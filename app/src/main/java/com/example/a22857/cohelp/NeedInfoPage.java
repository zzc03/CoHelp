package com.example.a22857.cohelp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

import Entity.ItemNeed;
import Entity.User;

public class NeedInfoPage extends AppCompatActivity {
    private TextView nameview;
    private TextView timeview;
    private TextView titleview;
    private TextView textview;
    private TextView rewardview;
    private Button buttonview;
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
        initView();
        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
    public void initView()
    {
        Intent intent=getIntent();
        final Integer needid=intent.getIntExtra("needid",0);
        if(needid!=0)
        {
            final Interface inter = new Interface();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap map=new HashMap();
                    map.put("needid",needid);
                    String result=inter.doGet("http://10.0.2.2:8080/need/querybyneedid",map);
                    ItemNeed a= JSON.parseObject(result, ItemNeed.class);
                    Looper.prepare();
                    nameview.setText(a.getUserName());
                    timeview.setText(a.getNeed().getTime());
                    titleview.setText(a.getNeed().getTitle());
                    textview.setText(a.getNeed().getText());
                    rewardview.setText(a.getNeed().getReward());
                    if(!a.getState().equals("none"))
                    {
                        buttonview.setText("已被接受");
                        buttonview.setEnabled(false);
                    }
                    Looper.loop();
                }
            }).start();
        }
    }
}
