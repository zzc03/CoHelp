package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;

public class MyPublicNeedDoingInfoPage extends AppCompatActivity {
    private TextView nameview;
    private TextView timeview;
    private TextView titleview;
    private TextView textview;
    private TextView rewardview;
    private ListView resultview;
    private ImageView headview;
    private List<ItemResult> results=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypublicneeddoinginfopage);
        nameview=(TextView)findViewById(R.id.mypublicneeddoinginfopagename);
        timeview=(TextView)findViewById(R.id.mypublicneeddoinginfopagetime);
        titleview=(TextView)findViewById(R.id.mypublicneeddoinginfopagetitle);
        textview=(TextView)findViewById(R.id.mypublicneeddoinginfopagetext);
        rewardview=(TextView)findViewById(R.id.mypublicneeddoinginfopagereward);
        resultview=(ListView)findViewById(R.id.mypublicneeddoinginfolistview);
        headview=(ImageView)findViewById(R.id.mypublicneeddoinginfopagehead);
        initView();
    }
    public void initView()
    {
        Intent intent=getIntent();
        final Integer needid=intent.getIntExtra("needid",0);
        int type=intent.getIntExtra("type",0);
        if(type!=1)
        {
            resultview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItemResult itemNeed=results.get(position);
                    Integer resultid=itemNeed.getResult().getResultId();
                    Intent intent=new Intent(MyPublicNeedDoingInfoPage.this,SetRewardPage.class);
                    intent.putExtra("resultid",resultid);
                    startActivityForResult(intent,0);
                }
            });
        }
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
            byte[] bytes= Base64.decode(a.getHead(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
            headview.setImageBitmap(setBitmap(bitmap,100,100));
            SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
            Integer userid=Integer.getInteger(sharedPreferences.getString("user_id",""));
            rewardview.setText("悬赏积分："+a.getNeed().getReward()+"积分");
            Log.d("----result","接受到的needstate为"+a.getState());
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
            Log.d("----result","接受到的result为"+result);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}
        @Override
        protected void onPostExecute(String text) {
           // Gson gson=new Gson();
           // List<ItemResult> a=gson.fromJson(text,new TypeToken<List<ItemResult>>() {}.getType());
            List<ItemResult> a= JSON.parseArray(text, ItemResult.class);
            Log.d("----result","接受到的need为"+a);
//            Log.d("----result","接受到的need为"+results);
            results=a;
            ResultListviewAdapter adapter=new ResultListviewAdapter(MyPublicNeedDoingInfoPage.this,a);
            resultview.setAdapter(adapter);

        }
    }
}
