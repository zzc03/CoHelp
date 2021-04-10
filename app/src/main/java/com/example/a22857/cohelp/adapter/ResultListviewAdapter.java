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

public class ResultListviewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemNeed> items;

    public ResultListviewAdapter(Activity context, List<ItemNeed> items) {
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
//        NeedListViewAdapter needListViewAdapter=null;
//       // NeedListViewAdapter.ViewHolder viewHolder=null;
//
//        ItemNeed item = items.get(position);
//        View v = convertView;
//        if (convertView == null) {
//            viewHolder=new NeedListViewAdapter.ViewHolder();
////            convertView=inflater.inflate(R.layout.item_need,null);
//            v = inflater.inflate(R.layout.item_need, null);
//            viewHolder.name = (TextView) v.findViewById(R.id.item_need_name);
//            viewHolder.title = (TextView) v.findViewById(R.id.item_need_title);
//            viewHolder.text = (TextView) v.findViewById(R.id.item_need_text);
//            viewHolder.money = (TextView) v.findViewById(R.id.item_need_money);
//            viewHolder.time = (TextView) v.findViewById(R.id.item_need_time);
//            viewHolder.state = (TextView) v.findViewById(R.id.item_need_state);
//            //convertView.setTag(viewHolder);
//            v.setTag(viewHolder);
//
//        }
//        else
//        {
//            v=convertView;
//            viewHolder=(NeedListViewAdapter.ViewHolder) v.getTag();
//        }
//        viewHolder.name.setText(item.getUserName());
//        // viewHolder.name.setTextSize(15);
//        viewHolder.title.setText(item.getNeed().getTitle());
//        //viewHolder.title.setTextSize(20);
//        viewHolder.text.setText(item.getNeed().getText());
//        //viewHolder.text.setTextSize(15);
//        viewHolder.money.setText("悬赏"+item.getNeed().getReward()+"积分");
//        //viewHolder.money.setTextSize(15);
//        viewHolder.time.setText(item.getNeed().getTime().toString());
//        //viewHolder.time.setTextSize(15);
//        viewHolder.state.setText(item.getState());
        //viewHolder.state.setTextSize(15);
        //return v;
        return null;
    }

}

