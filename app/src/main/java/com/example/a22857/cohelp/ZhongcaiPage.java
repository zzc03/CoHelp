package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import Entity.ItemNeed;
import Entity.ItemNeedApply;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZhongcaiPage extends AppCompatActivity {
    private ImageButton headview;
    private TextView nameview;
    private TextView timeview;
    private TextView titleview;
    private TextView textview;
    private TextView rewardview;
    private TextView stateview;
    private EditText reasonview;
    private Button button;
    int id=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhongcaipage);
        id=getIntent().getIntExtra("needapplyid",0);
        headview=(ImageButton)findViewById(R.id.zhongcai_head);
        nameview=(TextView)findViewById(R.id.zhongcai_name);
        timeview=(TextView)findViewById(R.id.zhongcai_time);
        titleview=(TextView)findViewById(R.id.zhongcai_title);
        textview=(TextView)findViewById(R.id.zhongcai_text);
        rewardview=(TextView)findViewById(R.id.zhongcai_money);
        stateview=(TextView)findViewById(R.id.zhongcai_state);
        reasonview=(EditText)findViewById(R.id.zhongcai_reason);
        button=(Button)findViewById(R.id.zhongcai_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reasonview.getText().equals(""))
                {
                    Toast.makeText(ZhongcaiPage.this,"请输入仲裁原因",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Date date=new Date();
                    SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
                    String  userid=sharedPreferences.getString("user_id","");
                    Log.d("AddNeedPagePost","发布需求的用户id为"+userid);
//                    Need need=new Need(title,text,date,"未完成",money,userid);
                    String url = "http://10.0.2.2:8080/need/zhongcai/add";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("applyid",id+"")
                            .add("applyuserid",userid+"")
                            .add("zcreason",reasonview.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("AddNeedPagePost","连接失败"+e.getLocalizedMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.body()!=null){
                                response.body().close();
                                Looper.prepare();
                                Toast.makeText(ZhongcaiPage.this,"已发布仲裁，请等待管理员审核",Toast.LENGTH_SHORT).show();
                                Intent inter = new Intent(ZhongcaiPage.this,MainPage.class);
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
        int id=intent.getIntExtra("needapplyid",0);
        QueryNeedApplyById queryNeedApplyById=new QueryNeedApplyById();
        queryNeedApplyById.execute(id);
    }
    class QueryNeedApplyById extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            int needapplyid=params[0];
            HashMap map=new HashMap();
            map.put("applyid",needapplyid+"");
            String result=inter.doGet("http://10.0.2.2:8080/needapply/querybyneedapplyid",map);
            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            ItemNeedApply a= JSON.parseObject(text, ItemNeedApply.class);
            Log.d("----result","接受到的need为"+a);
            nameview.setText(a.getName());
            timeview.setText(a.getNeedApply().getNeedtime());
            titleview.setText(a.getNeedApply().getNeedtitle());
            textview.setText(a.getNeedApply().getNeedtext());
            byte[] bytes= Base64.decode(a.getIcon(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            headview.setImageBitmap(bitmap);
            SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
            Integer userid=Integer.getInteger(sharedPreferences.getString("user_id",""));
            rewardview.setText("悬赏积分："+a.getNeedApply().getNeedreward()+"积分");
            stateview.setText(a.getNeedApply().getApplystate());
        }
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
