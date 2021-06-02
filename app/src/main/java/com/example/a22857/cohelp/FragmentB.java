package com.example.a22857.cohelp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.NeedListViewAdapter;
import com.example.a22857.cohelp.adapter.ResultInfoAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;

import static android.content.Context.MODE_PRIVATE;

public class FragmentB extends Fragment {
    private ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main01b, container, false);
        listview=(ListView) view.findViewById(R.id.fragment02listview);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
       QueryNeedByAcceptUserId queryNeedByAcceptUserId=new QueryNeedByAcceptUserId();
       queryNeedByAcceptUserId.execute(userid);

        return view;
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
            ResultInfoAdapter adapter=new ResultInfoAdapter(getActivity(),lists);
            listview.setAdapter(adapter);
        }
    }


}