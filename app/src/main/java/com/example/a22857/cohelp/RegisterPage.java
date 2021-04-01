package com.example.a22857.cohelp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import Entity.User;

/**
 * Created by 22857 on 2021/3/11.
 */

public class RegisterPage extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private EditText repassword;
    private EditText account;
    private Button sure;
    private Button cancal;
    private String nametext;
    private String passwordtext;
    private String repasswordtext;
    private String accounttext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        name=(EditText)findViewById(R.id.registerPageName);
        password=(EditText)findViewById(R.id.registerPagePassword);
        repassword=(EditText)findViewById(R.id.registerPageRepassword);
        sure=(Button)findViewById(R.id.registerPageSure);
        cancal=(Button)findViewById(R.id.registerPageCancal);
        account=(EditText)findViewById(R.id.registerPageAccount);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nametext=name.getText().toString();
                passwordtext=password.getText().toString();
                repasswordtext=repassword.getText().toString();
                accounttext=account.getText().toString();
                if(accounttext.equals("")||nametext.equals("")||passwordtext.equals("")||repasswordtext.equals(""))
                {
                    AlertDialog alertDialog1=new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("提示")
                            .setMessage("昵称或者密码不能为空")
                            .create();
                    alertDialog1.show();
                }
                else if(accounttext.length()>6 ||accounttext.length()<4)
                {
                    AlertDialog alertDialog3=new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("提示")
                            .setMessage("账号长度为4-6位")
                            .create();
                    alertDialog3.show();
                }
                else if(!passwordtext.equals(repasswordtext))
                {
                    AlertDialog alertDialog2=new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("提示")
                            .setMessage("密码与确认密码不同")
                            .create();
                    alertDialog2.show();
                }
                else
                {
                    String url = "http://10.0.2.2:8080/user/add";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("name",nametext)
                            .add("account",nametext+"001")
                            .add("password",passwordtext)
                            .add("description","this is "+nametext)
                            .add("money","1")
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("MainActivityPost","连接失败"+e.getLocalizedMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String  result = response.body().string();
                            Log.d("MainActivity",result);
                            User user = JSON.parseObject(result, User.class);
                            int userid = user.getUserId();
                            if (response.body()!=null){
                                response.body().close();
                                Looper.prepare();
                                Toast.makeText(RegisterPage.this,"你注册的userID为："+ userid,Toast.LENGTH_SHORT).show();
                                Intent inter = new Intent(RegisterPage.this,MainActivity.class);
                                startActivityForResult(inter,0);
                                Looper.loop();
                            }
                        }
                    });
                }

            }
        });
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
