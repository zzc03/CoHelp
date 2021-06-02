package com.example.a22857.cohelp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;
import com.example.a22857.cohelp.adapter.ResultInfoAdapter;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;

public class MydoingNeedPage extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydoingneedpage);
        listView=(ListView)findViewById(R.id.mydoingneedpagelistview);
        initView();
    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
        QueryNeedByAcceptUserId queryNeedByAcceptUserId=new QueryNeedByAcceptUserId();
        queryNeedByAcceptUserId.execute(userid);
    }
    class QueryNeedByAcceptUserId extends AsyncTask<Integer, Integer, String> {
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
            String result=inter.doGet("http://10.0.2.2:8080/result/querybyacceptuserid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            List<ItemResult> lists=JSON.parseArray(text,ItemResult.class);
            ResultInfoAdapter adapter=new ResultInfoAdapter(MydoingNeedPage.this,lists);
            listView.setAdapter(adapter);
        }
    }

}
