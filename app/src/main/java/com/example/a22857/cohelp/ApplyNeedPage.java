package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ApplyNeedAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemNeedApply;

public class ApplyNeedPage extends AppCompatActivity {
    private ListView listView;
    private List<ItemNeedApply> itemNeedApplies;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applyneedpage);
        listView=(ListView)findViewById(R.id.applyneedpagelistview);
       initView();
    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
        QueryNeedApplyByuserId queryNeedApplyByuserId=new QueryNeedApplyByuserId();
        queryNeedApplyByuserId.execute(userid);
    }
    class QueryNeedApplyByuserId extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            int userid=params[0];
            HashMap map=new HashMap();
            map.put("userid",userid+"");
            String result=inter.doGet("http://10.0.2.2:8080/needapply/querybyuserid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
           itemNeedApplies=JSON.parseArray(text,ItemNeedApply.class);
            ApplyNeedAdapter applyNeedAdapter=new ApplyNeedAdapter(ApplyNeedPage.this,itemNeedApplies);
            listView.setAdapter(applyNeedAdapter);
        }
    }
}
