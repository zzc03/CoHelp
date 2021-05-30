package com.example.a22857.cohelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a22857.cohelp.R;

import java.util.List;

import Entity.ItemMessage;
import Entity.ItemNeed;
import Entity.ItemNeedApply;

public class MessageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemMessage> items;


    public MessageAdapter(Activity contextList, List<ItemMessage> items ) {
        super();
        this.items = items;

        this.inflater = (LayoutInflater) contextList.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder
    {
        TextView name;
        TextView time;
        TextView text;
        TextView state;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageAdapter adapter=null;
        ViewHolder viewHolder=null;
        ItemMessage item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            viewHolder=new ViewHolder();
//            convertView=inflater.inflate(R.layout.item_need,null);
            v = inflater.inflate(R.layout.item_message, null);
            viewHolder.name = (TextView) v.findViewById(R.id.item_message_name);
            viewHolder.text = (TextView) v.findViewById(R.id.item_message_text);
            viewHolder.time = (TextView) v.findViewById(R.id.item_message_time);
            viewHolder.state=(TextView) v.findViewById(R.id.item_message_state);
            v.setTag(viewHolder);

        }
        else
        {
            v=convertView;
            viewHolder=(ViewHolder) v.getTag();
        }
        viewHolder.name.setText("管理员 "+item.getSendername());
        viewHolder.text.setText(item.getMessage().getText());
        viewHolder.time.setText(item.getMessage().getTime().substring(5));
        if(item.getMessage().getIsread()==0)
        {
            viewHolder.state.setText("未读");
        }
        else {
            viewHolder.state.setText("已读");
        }
        return v;
    }
}
