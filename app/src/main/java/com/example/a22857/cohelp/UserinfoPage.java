package com.example.a22857.cohelp;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.User;

public class UserinfoPage extends AppCompatActivity {
    private ImageView headview;
    private TextView nameview;
    private TextView descview;
    private ListView listView;
    private TextView neednumview;
    private List<ItemNeed> needs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfopage);
        headview=(ImageView)findViewById(R.id.userinfohead);
        nameview=(TextView)findViewById(R.id.userinfoname);
        listView=(ListView)findViewById(R.id.userinfolistview);
        descview=(TextView)findViewById(R.id.userinfodesc);
        neednumview=(TextView)findViewById(R.id.userinfoneednum);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemNeed itemNeed=needs.get(position);
                Integer needid=itemNeed.getNeed().getNeedid();
                Intent intent=new Intent(UserinfoPage.this,MyPublicNeedDoingInfoPage.class);
                intent.putExtra("needid",needid);
                intent.putExtra("type",1);
                startActivityForResult(intent,0);
            }
        });
        initView();
    }
    public void initView()
    {
        Intent intent=getIntent();
        final Interface inter = new Interface();
        final int userid=intent.getIntExtra("userid",0);
        QueryNeedByUserId queryNeedByUserId=new QueryNeedByUserId();
        queryNeedByUserId.execute(userid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map=new HashMap();
                map.put("userid",userid+"");
                String result=inter.doGet("http://10.0.2.2:8080/user/querybyid",map);
                User a= JSON.parseObject(result, User.class);
                Looper.prepare();
                nameview.setText(a.getName());
                descview.setText(a.getDescription());
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
    class QueryNeedByUserId extends AsyncTask<Integer, Integer, String> {
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
            String result=inter.doGet("http://10.0.2.2:8080/need/querybyuserid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            needs= JSON.parseArray(text, ItemNeed.class);
            neednumview.setText(needs.size()+"");
            NeedListViewAdapter needListViewAdapter=new NeedListViewAdapter(UserinfoPage.this,needs);
            listView.setAdapter(needListViewAdapter);
        }
    }

}
