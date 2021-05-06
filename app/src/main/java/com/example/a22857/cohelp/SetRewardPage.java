package com.example.a22857.cohelp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ImagePickerAdapter;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import Entity.ItemNeed;
import Entity.ItemResult;
import Entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetRewardPage extends AppCompatActivity {
    private TextView nameview;
    private TextView timeview;
    private TextView textview;
    //private RecyclerView pictureview;
    private TextView pictureview;
    private Button buttonview;
    private EditText editText;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;//允许选择图片最大数
    private HttpUtil httpUtil;
    private Integer resultidnum=0;
    private RecyclerView picturdisplayeview;
    private EditText commentview;
    private ImageView headview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setrewardpage);
        nameview=(TextView)findViewById(R.id.setrewardpagename);
        timeview=(TextView)findViewById(R.id.setrewardpagetime);
        textview=(TextView)findViewById(R.id.setrewardpagetext);
        //pictureview=(RecyclerView)findViewById(R.id.setrewardpagerecylerview);
        pictureview=(TextView)findViewById(R.id.setrewardpagepicture);
        buttonview=(Button)findViewById(R.id.setrewardpagebutton);
        editText=(EditText)findViewById(R.id.setrewardpagereward);
        picturdisplayeview=(RecyclerView)findViewById(R.id.setrewardpagepictureview);
        commentview=(EditText)findViewById(R.id.setrewardpagecomment);
        headview=(ImageView)findViewById(R.id.setrewardpagehead);
        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reward=editText.getText().toString();
                String comment=commentview.getText().toString();
                if(!reward.equals(""))
                {
                    Integer rewardnum=Integer.parseInt(reward);
                    String url = "http://10.0.2.2:8080/result/updatereward";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("resultid",resultidnum+"")
                            .add("reward",reward)
                            .add("comment",comment)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("SetRewardpage","连接失败"+e.getLocalizedMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.body()!=null){
                                response.body().close();
                                Looper.prepare();
                                Toast.makeText(SetRewardPage.this,"成功",Toast.LENGTH_SHORT).show();
                                Intent inter = new Intent(SetRewardPage.this,MainPage.class);
                                startActivityForResult(inter,0);
                                Looper.loop();
                            }
                        }
                    });

                }
            }
        });
        initView();
    }

    public void initView()
    {
        Intent intent=getIntent();
        Integer resultid=intent.getIntExtra("resultid",0);
        Log.d("SetRewardpage","传过来的needID"+resultid);
        if(resultid!=0)
        {
            QueryResultById queryResultById=new QueryResultById();
            queryResultById.execute(resultid);
        }
    }
    public List<Bitmap> getbitmap(List<String> image)
    {
        List<Bitmap> results=new ArrayList<>();
        for(String a:image)
        {
            byte[] bytes=Base64.decode(a,Base64.DEFAULT);
            results.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
        }
        return results;
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
    class QueryResultById extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            int resultid=params[0];
            HashMap map=new HashMap();
            map.put("resultid",resultid+"");
            String result=inter.doGet("http://10.0.2.2:8080/result/querybyresultid",map);
            Log.d("SetRewardpage","请求的结果为"+result);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}
        @Override
        protected void onPostExecute(String text) {
            ItemResult result= JSON.parseObject(text, ItemResult.class);
            nameview.setText(result.getName());
            timeview.setText(result.getResult().getAccepttime().substring(5));
            textview.setText(result.getResult().getAccepttext());
            resultidnum=result.getResult().getResultId();
            byte[] bytes= Base64.decode(result.getIcon(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
            headview.setImageBitmap(setBitmap(bitmap,100,100));
            if(result.getResult().getPicture()==0)
            {
                pictureview.setText("该用户未发布图片");
            }
            else
            {
                Log.d("SetRewardpage","要展示的图片为"+result.getPictures());
                pictureview.setText("图片如下");
                List<Bitmap> bitmaps=getbitmap(result.getPictures());
                Log.d("SetRewardpage","要展示的bitmap图片为"+bitmaps);
                if(result.getPictures().size()!=0)
                {
                    GridLayoutManager layoutManager=new GridLayoutManager(SetRewardPage.this,4);
                    picturdisplayeview.setLayoutManager(layoutManager);
                    imgAdapter adapter=new imgAdapter(bitmaps);
                    picturdisplayeview.setAdapter(adapter);
                }
                else
                {
                    pictureview.setText("图片加载失败");
                }

            }
        }
    }
    class imgAdapter extends RecyclerView.Adapter<imgAdapter.ViewHolder>{
        private List<Bitmap> list;
        public imgAdapter(List<Bitmap> list){ this.list = list; }

        class ViewHolder extends RecyclerView.ViewHolder{
            View myView;
            ImageView largeview;
            public ViewHolder(View itemView) {
                super(itemView);
                myView = itemView;
                largeview=(ImageView)myView.findViewById(R.id.iv_fangda_img);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(SetRewardPage.this).inflate(R.layout.otheractivity, parent, false);
            return new ViewHolder(v);
        }

        @SuppressLint("NewApi")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("SetRewardpage","进入适配器后要展示的bitmaps图片为"+list);
            final Bitmap img = list.get(position);

            Log.d("SetRewardpage","进入适配器后要展示的下标为"+position);
            Log.d("SetRewardpage","进入适配器后要展示的bitmap图片为"+img);
            if(holder.largeview==null)
            {
                Log.d("SetRewardpage","largeview为空");
            }
            holder.largeview.setImageBitmap(img);
            holder.myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View ve) {
                    final Dialog dialog=new Dialog(SetRewardPage.this,R.style.Widget_AppCompat_Light_ActionBar);
                    ImageView imageView=new ImageView(SetRewardPage.this);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    imageView.setImageBitmap(img);
                    dialog.setContentView(imageView);
                    dialog.show();
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
//                    ve.getContext().startActivity(
//                            new Intent(ve.getContext(),OtherActivity.class),
//                            // 注意这里的sharedView
//                            // Content，View（动画作用view），String（和XML一样）
//                            ActivityOptions.makeSceneTransitionAnimation((Activity) ve.getContext(),
//                                    ve,"sharedView").toBundle()
//                    );
//                    LayoutInflater inflater=LayoutInflater.from(SetRewardPage.this);
//                    View imgEntryView=inflater.inflate(R.layout.otheractivity,null);
//                    final AlertDialog alertDialog=new AlertDialog.Builder(SetRewardPage.this).create();
//                    alertDialog.setView(largeview);
//                    alertDialog.show();
//                    imgEntryView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            alertDialog.cancel();
//                        }
//                    });

//                    final AlertDialog alertDialog=new AlertDialog.Builder(SetRewardPage.this).create();
//                    alertDialog.setContentView(R.layout.otheractivity);
//                    largeview.setImageBitmap(img);
//                    alertDialog.setCanceledOnTouchOutside(true);
//                    Window w=alertDialog.getWindow();
//                    WindowManager.LayoutParams lp=w.getAttributes();
//                    lp.x=0;
//                    lp.y=40;
//                    alertDialog.onWindowAttributesChanged(lp);
//                    largeview.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            alertDialog.dismiss();
//                        }
//                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }

}
