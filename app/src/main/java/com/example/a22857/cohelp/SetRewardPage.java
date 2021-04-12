package com.example.a22857.cohelp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ImagePickerAdapter;
import com.example.a22857.cohelp.adapter.ResultListviewAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reward=editText.getText().toString();
                if(!reward.equals(""))
                {
                    Integer rewardnum=Integer.parseInt(reward);
                    String url = "http://10.0.2.2:8080/result/updatereward";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("resultid",resultidnum+"")
                            .add("reward",reward)
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
            if(result.getResult().getPicture()==0)
            {
                pictureview.setText("该用户未发布图片");
            }
            else
            {
                pictureview.setText(result.getPictures()+"");
            }
//            byte[][] pictures=new byte[result.getPictures().length][];
//            pictures=result.getPictures();
//            List<Bitmap> bitmaps=new ArrayList<>();
//            for(byte[] a:pictures)
//            {
//                Bitmap b=BitmapFactory.decodeByteArray(a, 0, a.length);
//
//            }

        }
    }

}
