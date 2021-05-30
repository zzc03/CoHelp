package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.User;

import static android.content.Context.MODE_PRIVATE;

public class FragmentA extends Fragment {
    private ListView listview;
    private List<ItemNeed> needs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main01a, container, false);
        listview=(ListView) view.findViewById(R.id.fragment01listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemNeed itemNeed=needs.get(position);
                Integer needid=itemNeed.getNeed().getNeedid();
                Intent intent=new Intent(getActivity(),MyPublicNeedDoingInfoPage.class);
                intent.putExtra("needid",needid);
                intent.putExtra("type",0);
                startActivityForResult(intent,0);
            }
        });
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
//        final Interface inter = new Interface();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HashMap map=new HashMap();
//                map.put("userid",userid+"");
//                String result=inter.doGet("http://10.0.2.2:8080/need/querybyuserid",map);
//                Message message=new Message();
//                Bundle data=new Bundle();
//                data.putString("data",result);
//                message.setData(data);
//                handler.sendMessage(message);
//
//            }
//        }).start();
//        Log.d("----needs","needs为"+needs);
        QueryNeedByUserId queryNeedByUserId=new QueryNeedByUserId();
        queryNeedByUserId.execute(userid);

        return view;
    }
//    Handler handler=new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String val = data.getString(" data");
//            needs= JSON.parseArray(val, ItemNeed.class);
//            NeedListViewAdapter needListViewAdapter=new NeedListViewAdapter(getActivity(),needs);
//            listview.setAdapter(needListViewAdapter);
//        }
//    };
    public void initView()
    {


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
            NeedListViewAdapter needListViewAdapter=new NeedListViewAdapter(getActivity(),needs);
            listview.setAdapter(needListViewAdapter);
        }
    }


}