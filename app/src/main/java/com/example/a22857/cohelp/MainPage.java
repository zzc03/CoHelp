package com.example.a22857.cohelp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import Entity.ItemNeed;
import Entity.Need;

/**
 * Created by 22857 on 2021/3/18.
 */

public class MainPage extends AppCompatActivity {
    private ListView listView;
    private List<ItemNeed> needs=new ArrayList<>();
    private boolean isLogin;
    private Button addButton;
    private Button personButton;
    private Button mainButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        listView=(ListView)findViewById(R.id.mainpageneedlistview);
        addButton=(Button)findViewById(R.id.mainpageaddbutton);
        personButton=(Button)findViewById(R.id.mainpagepersonbutton);
        mainButton=(Button)findViewById(R.id.mainpagemainbutton);
        initView();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPage.this,AddNeedPage.class);
                startActivity(intent);
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
        personButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog1=new AlertDialog.Builder(MainPage.this)
                        .setTitle("提示")
                        .setMessage("功能暂未实现")
                        .create();
                alertDialog1.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog1=new AlertDialog.Builder(MainPage.this)
                        .setTitle("提示")
                        .setMessage("功能暂未实现")
                        .create();
                alertDialog1.show();
            }
        });
    }
    public void initView()
    {
        QueryAllBack queryAllBack = new QueryAllBack();
        queryAllBack.execute(10);
    }
    class QueryAllBack extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            String result=inter.doGet("http://10.0.2.2:8080/need/query/all/map");
            Log.d("----result",result);
            List<ItemNeed> a= JSON.parseArray(result,ItemNeed.class);
            needs.clear();
            for(ItemNeed b:a)
            {
//                Log.d("----blogId+userName",b.getBlog().getBlogId()+b.getUserName());

                needs.add(b);
            }
            return "done";
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            NeedListViewAdapter adapter=new NeedListViewAdapter(MainPage.this,needs);
            listView.setAdapter(adapter);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        initView();
    }

}
