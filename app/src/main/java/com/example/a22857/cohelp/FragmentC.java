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
import com.example.a22857.cohelp.adapter.ApplyNeedAdapter;
import com.example.a22857.cohelp.adapter.ResultInfoAdapter;

import java.util.HashMap;
import java.util.List;

import Entity.ItemNeedApply;
import Entity.ItemResult;

import static android.content.Context.MODE_PRIVATE;

public class FragmentC extends Fragment {
    private ListView listView;
    private List<ItemNeedApply> itemNeedApplies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main01c, container, false);
        listView=(ListView) view.findViewById(R.id.fragment03listview);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);

        QueryNeedApplyByuserId queryNeedApplyByuserId=new QueryNeedApplyByuserId();
        queryNeedApplyByuserId.execute(userid);
        return view;
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
            itemNeedApplies=JSON.parseArray(text, ItemNeedApply.class);
            ApplyNeedAdapter applyNeedAdapter=new ApplyNeedAdapter(getActivity(),itemNeedApplies);
            listView.setAdapter(applyNeedAdapter);
        }
    }
}
