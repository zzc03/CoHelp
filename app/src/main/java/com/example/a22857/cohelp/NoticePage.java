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
import com.example.a22857.cohelp.adapter.MessageAdapter;
import com.example.a22857.cohelp.adapter.ResultInfoAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemMessage;
import Entity.ItemResult;

public class NoticePage extends AppCompatActivity {
    private ListView listView;
    List<ItemMessage> lists;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticepage);
        listView=(ListView)findViewById(R.id.noticepagelistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemMessage itemMessage=lists.get(position);
                Intent intent=new Intent(NoticePage.this,CallAdminPage.class);
                intent.putExtra("type","1");
                intent.putExtra("messageid",itemMessage.getMessage().getId()+"");
                intent.putExtra("adminname",itemMessage.getSendername());
                intent.putExtra("adminid",itemMessage.getMessage().getSendid()+"");
                intent.putExtra("text",itemMessage.getMessage().getText());
                startActivity(intent);
            }
        });
        initView();
    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
        QueryMessageByReceiverid queryMessageByReceiverid=new QueryMessageByReceiverid();
        queryMessageByReceiverid.execute(userid);
    }
    class QueryMessageByReceiverid extends AsyncTask<Integer, Integer, String> {
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
            lists= JSON.parseArray(text,ItemMessage.class);
            MessageAdapter adapter=new MessageAdapter(NoticePage.this,lists);
            listView.setAdapter(adapter);

        }
    }

}
