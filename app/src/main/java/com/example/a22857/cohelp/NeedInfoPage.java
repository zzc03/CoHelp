package com.example.a22857.cohelp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;
import Entity.User;

public class NeedInfoPage extends AppCompatActivity {
    private TextView nameview;
    private TextView timeview;
    private TextView titleview;
    private TextView textview;
    private TextView rewardview;
    private Button buttonview;
    private ListView resultview;
    private ImageView headview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needinfopage);
        nameview=(TextView)findViewById(R.id.personinfopagename);
        timeview=(TextView)findViewById(R.id.personinfopagetime);
        titleview=(TextView)findViewById(R.id.personinfopagetitle);
        textview=(TextView)findViewById(R.id.personinfopagetext);
        rewardview=(TextView)findViewById(R.id.personinfopagereward);
        buttonview=(Button)findViewById(R.id.personinfopagebutton);
        resultview=(ListView)findViewById(R.id.needinfopagelistview);
        headview=(ImageView)findViewById(R.id.personinfopagehead);
        initView();
        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NeedInfoPage.this).setTitle("提示")
                        .setMessage("是否接受该需求")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NeedInfoPage.this,"接受成功",Toast.LENGTH_SHORT).show();
                                Intent intent=getIntent();
                                Integer needid=intent.getIntExtra("needid",0);
                                SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
                                String userid=sharedPreferences.getString("user_id","");
                                Intent intent1=new Intent(NeedInfoPage.this,NeedDoingPage.class);
                                intent1.putExtra("needid",needid);
                                startActivityForResult(intent1,0);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();



            }
        });
    }
    public void initView()
    {
        Intent intent=getIntent();
        final Integer needid=intent.getIntExtra("needid",0);
        Log.d("----result","接受到的needID为"+needid);
        if(needid!=0)
        {
            QueryNeedById queryNeedById=new QueryNeedById();
            queryNeedById.execute(needid);
            QueryResultByNeedId queryResultByNeedId=new QueryResultByNeedId();
            queryResultByNeedId.execute(needid);
        }
    }
    class QueryNeedById extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            int needid=params[0];
            HashMap map=new HashMap();
            map.put("needid",needid+"");
            String result=inter.doGet("http://10.0.2.2:8080/need/querybyneedid",map);

            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            ItemNeed a= JSON.parseObject(text, ItemNeed.class);
            Log.d("----result","接受到的need为"+a);
            nameview.setText(a.getUserName());
            timeview.setText(a.getNeed().getTime());
            titleview.setText(a.getNeed().getTitle());
            textview.setText(a.getNeed().getText());
            final Intent intent=new Intent(NeedInfoPage.this,UserinfoPage.class);
            intent.putExtra("userid",a.getNeed().getUserid());
            headview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(intent,0);
                }
            });
            byte[] bytes= Base64.decode(a.getHead(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
            headview.setImageBitmap(setBitmap(bitmap,100,100));
            SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
            Integer userid=Integer.getInteger(sharedPreferences.getString("user_id",""));
            rewardview.setText("悬赏积分："+a.getNeed().getReward()+"积分");
            Log.d("----result","接受到的needstate为"+a.getState());
            IsAccepted isAccepted=new IsAccepted();
            isAccepted.execute(a.getNeed().getNeedid(),userid);
        }
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
    class QueryResultByNeedId extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            int needid=params[0];
            HashMap map=new HashMap();
            map.put("needid",needid+"");
            String result=inter.doGet("http://10.0.2.2:8080/result/querybyneedid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}
        @Override
        protected void onPostExecute(String text) {
            List<ItemResult> a= JSON.parseArray(text, ItemResult.class);
            Log.d("----result","接受到的need为"+a);
            ResultListviewAdapter adapter=new ResultListviewAdapter(NeedInfoPage.this,a);
            resultview.setAdapter(adapter);
        }
    }
    class IsAccepted extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            Integer needid=params[0];
            Integer userid=params[1];
            HashMap map=new HashMap();
            map.put("needid",needid+"");
            map.put("userid",userid+"");
            String result=inter.doGet("http://10.0.2.2:8080/result/querybyneedidanduserid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}
        @Override
        protected void onPostExecute(String text) {
           if(text.equals("true"))
           {
               buttonview.setText("你已接受");
               buttonview.setEnabled(false);
           }
        }
    }

}
