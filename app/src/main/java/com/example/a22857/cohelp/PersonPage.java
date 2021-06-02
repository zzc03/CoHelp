package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.MessageAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemMessage;
import Entity.User;

public class PersonPage extends AppCompatActivity{
    private TextView nameview;
    private TextView moneyview;
    private TextView descriptionview;
    private TextView needview;
    private TextView noticeview;
    private ImageView headview;
    private TextView messnumview;
    private Button mainpagebutton;
    private Button addbutton;
    private Button personbutton;
    private Button exitbtn;
    private TextView calladminview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personpage);
        nameview=(TextView)findViewById(R.id.personpagename);
        moneyview=(TextView)findViewById(R.id.personpagemoney);
        descriptionview=(TextView)findViewById(R.id.personpagedescription);
        needview=(TextView)findViewById(R.id.personpageneed);
        noticeview=(TextView)findViewById(R.id.personpagenotice);
        mainpagebutton=(Button)findViewById(R.id.personpagemain);
        addbutton=(Button)findViewById(R.id.personpageadd);
        personbutton=(Button)findViewById(R.id.personpageperson);
        headview=(ImageView)findViewById(R.id.personpagehead);
        messnumview=(TextView)findViewById(R.id.personpagemessnum);
        exitbtn=(Button)findViewById(R.id.personpagequitbutton);
        calladminview=(TextView)findViewById(R.id.personpagecalladmin);
        mainpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainPage.class);
                startActivityForResult(intent,0);
            }
        });
        calladminview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,CallAdminPage.class);
                intent.putExtra("type","2");
                startActivityForResult(intent,0);
            }
        });
        initView();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainPage.class);
                startActivity(intent);
            }
        });
        personbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
        needview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MyNeedPage.class);
                startActivity(intent);

            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
        noticeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,NoticePage.class);
                startActivityForResult(intent,0);
            }
        });
    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        final String userid=sharedPreferences.getString("user_id","");
        final Interface inter = new Interface();
        QueryMessageNumByReceiverid queryMessageNumByReceiverid=new QueryMessageNumByReceiverid();
        queryMessageNumByReceiverid.execute(Integer.parseInt(userid));
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map=new HashMap();
                map.put("userid",userid);
                String result=inter.doGet("http://10.0.2.2:8080/user/querybyid",map);
                User a= JSON.parseObject(result, User.class);
                Looper.prepare();
                nameview.setText(a.getName());
                moneyview.setText(a.getMoney()+"");
                descriptionview.setText(a.getDescription());
                byte[] bytes= Base64.decode(a.getIcon(),Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
                headview.setImageBitmap(setBitmap(bitmap,100,100));
                Looper.loop();
            }
        }).start();
    }
    public Bitmap setBitmap(Bitmap bitmap, int height, int width)
    {
        int w=bitmap.getWidth();
        int h=bitmap.getHeight();
        float scaleW=((float)width)/w;
        float scaleh=((float)height)/h;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleW,scaleh);
        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);

    }
    class QueryMessageNumByReceiverid extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            Integer userid=params[0];
            HashMap map=new HashMap();
            map.put("userid",userid+"");
            Log.d("----result","查询的userID为为"+userid);
            String result=inter.doGet("http://10.0.2.2:8080/message/querybyuserid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {

            List<ItemMessage> lists= JSON.parseArray(text,ItemMessage.class);
            messnumview.setText(lists.size()+"");
        }
    }
}
