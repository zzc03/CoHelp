package com.example.a22857.cohelp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Date;

import Entity.Need;
import Entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddNeedPage extends AppCompatActivity{
    private EditText titleview;
    private EditText textview;
    private EditText moneyview;
    private Button submitview;
    private Button mainpageview;
    private Button personview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);
        titleview=(EditText)findViewById(R.id.addpagetitle);
        textview=(EditText)findViewById(R.id.addpagetext);
        moneyview=(EditText)findViewById(R.id.addpagemoney);
        submitview=(Button)findViewById(R.id.addpagesubmit);
        mainpageview=(Button)findViewById(R.id.addpagemain);
        personview=(Button)findViewById(R.id.addpageperson);
        submitview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleview.getText().toString();
                String text=textview.getText().toString();
                String moneystring=moneyview.getText().toString();
                if(title.equals("") ||text.equals("")||moneystring.equals(""))
                {
                    AlertDialog alertDialog1=new AlertDialog.Builder(AddNeedPage.this)
                            .setTitle("提示")
                            .setMessage("有未填的空")
                            .create();
                    alertDialog1.show();
                }
                else
                {

                    Date date=new Date();

                    SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
                    String  userid=sharedPreferences.getString("user_id","");
                    Log.d("AddNeedPagePost","发布需求的用户id为"+userid);
//                    Need need=new Need(title,text,date,"未完成",money,userid);
                    String url = "http://10.0.2.2:8080/need/add";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("userid",userid)
                            .add("title",title)
                            .add("text",text)
                            .add("reward",moneystring)
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
                            String  result = response.body().string();
                            Log.d("MainActivity",result);
                            User user = JSON.parseObject(result, User.class);
                            int userid = user.getId();
                            if (response.body()!=null){
                                response.body().close();
                                Looper.prepare();
                                Toast.makeText(AddNeedPage.this,"你发布需求的用户id为："+ userid,Toast.LENGTH_SHORT).show();
                                Intent inter = new Intent(AddNeedPage.this,MainPage.class);
                                startActivityForResult(inter,0);
                                Looper.loop();
                            }
                        }
                    });
                }

            }
        });
    }

}
