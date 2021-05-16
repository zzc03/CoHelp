package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;

public class MyPublishNeedPage extends AppCompatActivity {
    private ListView listView;
    private List<ItemNeed> needs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mypublishneedpage);
        listView=(ListView)findViewById(R.id.mypublishneedpagelistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemNeed itemNeed=needs.get(position);
                Integer needid=itemNeed.getNeed().getNeedid();
                Intent intent=new Intent(MyPublishNeedPage.this,MyPublicNeedDoingInfoPage.class);
                intent.putExtra("needid",needid);
                intent.putExtra("type",0);
                startActivityForResult(intent,0);
            }
        });
        initView();

    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
        QueryNeedByUserId queryNeedByUserId=new QueryNeedByUserId();
        queryNeedByUserId.execute(userid);
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
            NeedListViewAdapter needListViewAdapter=new NeedListViewAdapter(MyPublishNeedPage.this,needs);
            listView.setAdapter(needListViewAdapter);
        }
    }

}
