package com.example.a22857.cohelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a22857.cohelp.R;

import java.util.List;

import Entity.ItemResult;

public class ResultInfoAdapter  extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemResult> items;

    public ResultInfoAdapter(Activity context, List<ItemResult> items) {
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
        TextView comment;
        TextView reward;
        ImageView head;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ResultInfoAdapter resultListviewAdapter=null;
        ResultInfoAdapter.ViewHolder viewHolder=null;
        ItemResult item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            viewHolder=new ResultInfoAdapter.ViewHolder();
            v = inflater.inflate(R.layout.item_resultinfo, null);
            viewHolder.name = (TextView) v.findViewById(R.id.resultinfoname);
            viewHolder.time = (TextView) v.findViewById(R.id.resultinfotime);
            viewHolder.state = (TextView) v.findViewById(R.id.resultinfostate);
            viewHolder.comment=(TextView) v.findViewById(R.id.resultinfocomment);
            viewHolder.reward=(TextView) v.findViewById(R.id.resultinforeward);
            viewHolder.head=(ImageView) v.findViewById(R.id.resultinfohead);
            v.setTag(viewHolder);

        }
        else
        {
            v=convertView;
            viewHolder=(ResultInfoAdapter.ViewHolder) v.getTag();
        }
        viewHolder.name.setText(item.getName());
        viewHolder.time.setText(item.getResult().getAccepttime().substring(5));
        viewHolder.state.setText(item.getResult().getState());
        byte[] bytes= Base64.decode(item.getIcon(),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
        viewHolder.head.setImageBitmap(setBitmap(bitmap,100,100));
        if(item.getResult().getComment()==null)
        {
            viewHolder.comment.setText("未发布评论");
        }
        else
        {
            viewHolder.comment.setText(item.getResult().getComment());
        }
        viewHolder.reward.setText(item.getResult().getReward()+"积分");

        return v;
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

}
