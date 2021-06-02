package com.example.a22857.cohelp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;

public class NeedDoingInfoPage extends AppCompatActivity {
    private ListView listView;
    private List<ItemResult> results;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needdoinginfopage);
        listView=(ListView)findViewById(R.id.needdoinginfopagelistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemResult itemResult=results.get(position);
                Integer resultid=itemResult.getResult().getResultId();
                Intent intent=new Intent(NeedDoingInfoPage.this,SetRewardPage.class);
                intent.putExtra("resultid",resultid);
                startActivityForResult(intent,0);
            }
        });
    }
    public void initView(){
        Intent intent=getIntent();
        final Integer needid=intent.getIntExtra("needid",0);
        QueryResultByNeedId queryResultByNeedId=new QueryResultByNeedId();
        queryResultByNeedId.execute(needid);
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
            String result=inter.doGet("http://10.0.2.2:8080/result/querybyid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}
        @Override
        protected void onPostExecute(String text) {
            List<ItemResult> results= JSON.parseArray(text, ItemResult.class);
            Log.d("----result","接受到的need为"+results);
            ResultListviewAdapter adapter=new ResultListviewAdapter(NeedDoingInfoPage.this,results);
            listView.setAdapter(adapter);
        }
    }
}
