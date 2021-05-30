package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallAdminPage extends AppCompatActivity {
    private TextView nameview;
    private TextView textView;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calladminpage);
        nameview=(TextView)findViewById(R.id.calladminpagename);
        textView=(TextView)findViewById(R.id.calladminpagetext);
        editText=(EditText)findViewById(R.id.calladminpageedit);
        button=(Button)findViewById(R.id.calladminpagebtn);
        initView();
    }
    public void initView()
    {
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        String adminid="";
        if(type.equals("1"))
        {
            String name=intent.getStringExtra("adminname");
            String text=intent.getStringExtra("text");
            String messageid=intent.getStringExtra("messageid");
            if(!messageid.equals(""))
            {
                String url = "http://10.0.2.2:8080/message/setread";
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder requestBuild = new FormBody.Builder();
                RequestBody requestBody = requestBuild
                        .add("messageid",messageid)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Calladmin","连接失败"+e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
            nameview.setText(name);
            textView.setText(text);
            adminid=intent.getStringExtra("adminid");
        }
        else
        {
            nameview.setText("");
            textView.setText("");
        }
        final String admin=adminid;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
                String user=sharedPreferences.getString("user_id","");
                String url = "http://10.0.2.2:8080/message/usersend";
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder requestBuild = new FormBody.Builder();
                RequestBody requestBody = requestBuild
                        .add("userid",user)
                        .add("text",editText.getText().toString())
                        .add("adminid",admin)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Calladmin","连接失败"+e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.body()!=null){
                            response.body().close();
                            Looper.prepare();
                            Toast.makeText(CallAdminPage.this,"发送成功",Toast.LENGTH_SHORT).show();
                            Intent inter = new Intent(CallAdminPage.this,PersonPage.class);
                            startActivityForResult(inter,0);
                            Looper.loop();
                        }
                    }
                });
            }
        });
    }
}
