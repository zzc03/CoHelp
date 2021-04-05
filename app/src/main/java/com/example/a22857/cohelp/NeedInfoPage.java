package com.example.a22857.cohelp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.User;

public class NeedInfoPage extends AppCompatActivity {
    private TextView nameview;
    private TextView timeview;
    private TextView titleview;
    private TextView textview;
    private TextView rewardview;
    private Button buttonview;
    private Handler handler;
    private String result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needinfopage);
        handler=new Handler();
        nameview=(TextView)findViewById(R.id.personinfopagename);
        timeview=(TextView)findViewById(R.id.personinfopagetime);
        titleview=(TextView)findViewById(R.id.personinfopagetitle);
        textview=(TextView)findViewById(R.id.personinfopagetext);
        rewardview=(TextView)findViewById(R.id.personinfopagereward);
        buttonview=(Button)findViewById(R.id.personinfopagebutton);
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
//                                Intent intent=getIntent();
//                                Integer needid=intent.getIntExtra("needid",0);
//                                SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
//                                String userid=sharedPreferences.getString("user_id","");
//                                Intent intent1=new Intent();
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
//            final Interface inter = new Interface();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    HashMap map=new HashMap();
//                    map.put("needid",needid+"");
//                    result=inter.doGet("http://10.0.2.2:8080/need/querybyneedid",map);
//                    ItemNeed a= JSON.parseObject(result, ItemNeed.class);
//                    Looper.prepare();
//                    nameview.setText(a.getUserName());
//                    timeview.setText(a.getNeed().getTime());
//                    titleview.setText(a.getNeed().getTitle());
//                    textview.setText(a.getNeed().getText());
//                    rewardview.setText(a.getNeed().getReward());
//                    if(!a.getState().equals("none"))
//                    {
//                        buttonview.setText("已被接受");
//                        buttonview.setEnabled(false);
//                    }
//                    Looper.loop();
//                }
//            }).start();
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
            result=inter.doGet("http://10.0.2.2:8080/need/querybyneedid",map);

            return "done";
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            ItemNeed a= JSON.parseObject(result, ItemNeed.class);
            Log.d("----result","接受到的need为"+a);
            nameview.setText(a.getUserName());
            timeview.setText(a.getNeed().getTime());
            titleview.setText(a.getNeed().getTitle());
            textview.setText(a.getNeed().getText());
            rewardview.setText("悬赏积分："+a.getNeed().getReward()+"积分");
            Log.d("----result","接受到的needstate为"+a.getState());
            if(!a.getNeed().getState().equals("none"))
            {
                buttonview.setText("已被接受");
                buttonview.setEnabled(false);
            }
        }
    }
}
