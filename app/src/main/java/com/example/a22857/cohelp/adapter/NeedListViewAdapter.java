package com.example.a22857.cohelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
        ImageButton head;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NeedListViewAdapter needListViewAdapter=null;
        ViewHolder viewHolder=null;

        ItemNeed item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            viewHolder=new ViewHolder();
//            convertView=inflater.inflate(R.layout.item_need,null);
            v = inflater.inflate(R.layout.item_need, null);
            viewHolder.name = (TextView) v.findViewById(R.id.item_need_name);
            viewHolder.title = (TextView) v.findViewById(R.id.item_need_title);
            viewHolder.text = (TextView) v.findViewById(R.id.item_need_text);
            viewHolder.money = (TextView) v.findViewById(R.id.item_need_money);
            viewHolder.time = (TextView) v.findViewById(R.id.item_need_time);
            viewHolder.state = (TextView) v.findViewById(R.id.item_need_state);
            viewHolder.head=(ImageButton) v.findViewById(R.id.item_need_head) ;
            //convertView.setTag(viewHolder);
            v.setTag(viewHolder);

        }
        else
        {
            v=convertView;
            viewHolder=(ViewHolder) v.getTag();
        }
        viewHolder.name.setText(item.getUserName());
       // viewHolder.name.setTextSize(15);
        viewHolder.title.setText(item.getNeed().getTitle());
        //viewHolder.title.setTextSize(20);
        viewHolder.text.setText(item.getNeed().getText());
        //viewHolder.text.setTextSize(15);
        viewHolder.money.setText("悬赏"+item.getNeed().getReward()+"积分");
        //viewHolder.money.setTextSize(15);
        viewHolder.time.setText(item.getNeed().getTime().substring(5));
        //viewHolder.time.setTextSize(15);
        viewHolder.state.setText(item.getNeed().getState());
        //viewHolder.state.setTextSize(15);
        byte[] bytes= Base64.decode(item.getHead(),Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
        viewHolder.head.setImageBitmap(setBitmap(bitmap,100,100));
        return v;
    }
    public Bitmap setBitmap(Bitmap bitmap,int height,int width)
    {
        int w=bitmap.getWidth();
        int h=bitmap.getHeight();

        float scaleW=((float)width)/w;
        float scaleh=((float)height)/h;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleW,scaleh);
        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);

    }

}
