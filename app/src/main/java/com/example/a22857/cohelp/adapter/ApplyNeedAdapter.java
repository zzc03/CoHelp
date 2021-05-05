package com.example.a22857.cohelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a22857.cohelp.R;
import com.example.a22857.cohelp.ZhongcaiPage;

import java.util.List;

import Entity.ItemNeed;
import Entity.ItemNeedApply;
import Entity.NeedApply;

public class ApplyNeedAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ItemNeedApply> items;
    Activity context;

    public ApplyNeedAdapter(Activity context, List<ItemNeedApply> items) {
        super();
        this.items = items;
        this.context=context;
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
        TextView view ;
        TextView viewtext;
        ImageButton head;
        Button button;
    }
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ApplyNeedAdapter needListViewAdapter=null;
        ApplyNeedAdapter.ViewHolder viewHolder=null;

        final ItemNeedApply item = items.get(position);
        View v = convertView;
        if (convertView == null) {
            viewHolder=new ApplyNeedAdapter.ViewHolder();
//            convertView=inflater.inflate(R.layout.item_need,null);
            v = inflater.inflate(R.layout.item_applyneed, null);
            viewHolder.name = (TextView) v.findViewById(R.id.item_needapply_name);
            viewHolder.title = (TextView) v.findViewById(R.id.item_needapply_title);
            viewHolder.text = (TextView) v.findViewById(R.id.item_needapply_text);
            viewHolder.money = (TextView) v.findViewById(R.id.item_needapply_money);
            viewHolder.time = (TextView) v.findViewById(R.id.item_needapply_time);
            viewHolder.view = (TextView) v.findViewById(R.id.item_needapply_view);
            viewHolder.viewtext=(TextView) v.findViewById(R.id.item_needapply_viewtext);
            viewHolder.head=(ImageButton) v.findViewById(R.id.item_needapply_head) ;
            viewHolder.button=(Button) v.findViewById(R.id.item_needapply_zhongcai);
            //convertView.setTag(viewHolder);
            v.setTag(viewHolder);

        }
        else
        {
            v=convertView;
            viewHolder=(ViewHolder) v.getTag();
        }
        viewHolder.name.setText(item.getName());
        // viewHolder.name.setTextSize(15);
        viewHolder.title.setText(item.getNeedApply().getNeedtitle());
        //viewHolder.title.setTextSize(20);
        viewHolder.text.setText(item.getNeedApply().getNeedtext());
        //viewHolder.text.setTextSize(15);
        viewHolder.money.setText("悬赏"+item.getNeedApply().getNeedreward()+"积分");
        //viewHolder.money.setTextSize(15);
        viewHolder.time.setText(item.getNeedApply().getNeedtime().substring(5));
        //viewHolder.time.setTextSize(15);
        viewHolder.view.setText(item.getNeedApply().getApplystate());
        viewHolder.viewtext.setText(item.getNeedApply().getSolveview());
        //viewHolder.state.setTextSize(15);
        byte[] bytes= Base64.decode(item.getIcon(),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
        viewHolder.head.setImageBitmap(setBitmap(bitmap,100,100));
        if(!item.getNeedApply().getApplystate().equals("未通过"))
        {
            Log.d("----点击函数","点击了applyid为"+item.getNeedApply().getApplyid()+"的仲裁按钮不可用");
            viewHolder.button.setEnabled(false);

        }
        else
        {
            Log.d("----点击函数","点击了applyid为"+item.getNeedApply().getApplyid()+"的仲裁按钮可用");
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("----点击函数","点击了applyid为"+item.getNeedApply().getApplyid()+"的仲裁按钮");
                    Intent intent=new Intent(context, ZhongcaiPage.class);
                    intent.putExtra("needapplyid",item.getNeedApply().getApplyid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
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
