package com.example.a22857.cohelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a22857.cohelp.R;

import java.util.List;

import Entity.ItemNeed;
import Entity.ItemResult;

public class ResultListviewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemResult> items;

    public ResultListviewAdapter(Activity context, List<ItemResult> items) {
        super();
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;

    }
    private class ViewHolder{
        TextView name;
        TextView time ;
        TextView state ;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ResultListviewAdapter resultListviewAdapter=null;
        ResultListviewAdapter.ViewHolder viewHolder=null;
        ItemResult item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            viewHolder=new ResultListviewAdapter.ViewHolder();
            v = inflater.inflate(R.layout.item_result, null);
            viewHolder.name = (TextView) v.findViewById(R.id.itemresultname);
            viewHolder.time = (TextView) v.findViewById(R.id.itemresulttiem);
            viewHolder.state = (TextView) v.findViewById(R.id.itemresultstate);
            v.setTag(viewHolder);

        }
        else
        {
            v=convertView;
            viewHolder=(ResultListviewAdapter.ViewHolder) v.getTag();
        }
        viewHolder.name.setText(item.getName());
        viewHolder.time.setText(item.getResult().getAccepttime().substring(5));
        viewHolder.state.setText(item.getResult().getState());
        return v;
    }

}

