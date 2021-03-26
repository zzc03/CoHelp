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
import Entity.Need;


public class NeedListViewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemNeed> items;

    public NeedListViewAdapter(Activity context, List<ItemNeed> items) {
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
        TextView title;
        TextView text ;
        TextView money ;
        TextView time ;
        TextView state ;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NeedListViewAdapter needListViewAdapter=null;
        ItemNeed item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            ViewHolder viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_need,null);
            v = inflater.inflate(R.layout.item_need, null);
            TextView name = (TextView) v.findViewById(R.id.item_need_name);
            TextView title = (TextView) v.findViewById(R.id.item_need_title);
            TextView text = (TextView) v.findViewById(R.id.item_need_text);
            TextView money = (TextView) v.findViewById(R.id.item_need_money);
            TextView time = (TextView) v.findViewById(R.id.item_need_time);
            TextView state = (TextView) v.findViewById(R.id.item_need_state);
            convertView.setTag(viewHolder);
            name.setText(item.getUserName());
            name.setTextSize(15);
            title.setText(item.getNeed().getTitle());
            title.setTextSize(20);
            text.setText(item.getNeed().getText());
            text.setTextSize(15);
            money.setText("悬赏"+item.getNeed().getReward()+"积分");
            money.setTextSize(15);
            time.setText(item.getNeed().getTime().toString());
            time.setTextSize(15);
            state.setText(item.getState());
            state.setTextSize(15);

        }
        return v;
    }

}