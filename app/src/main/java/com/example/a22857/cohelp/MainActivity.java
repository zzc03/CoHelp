package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText account;
    EditText password;
    Button signin;
    Button register;
//    TextView change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account=(EditText)findViewById(R.id.mainAccount);
        password=(EditText)findViewById(R.id.mainPassword);
        signin=(Button)findViewById(R.id.mainSignin);
        register=(Button)findViewById(R.id.mainRegister);
        //change=(TextView)findViewById(R.id.mainChange);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accounttext=account.getText().toString();
                String passwordtext=password.getText().toString();
                if(accounttext.equals("") || passwordtext.equals(""))
                {
                    AlertDialog alertDialog1=new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("账号或者密码不能为空")
                            .create();
                    alertDialog1.show();
                }
                else
                {
//                    QueryAllBack queryAllBack = new QueryAllBack();
//                    queryAllBack.execute();
                    final Interface inter = new Interface();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap map=new HashMap();
                            map.put("account",account.getText().toString());
                            String result=inter.doGet("http://10.0.2.2:8080/user/querybyaccount",map);
                            User a= JSON.parseObject(result, User.class);
                            if(a==null)
                            {
                                Looper.prepare();
                                Toast.makeText(MainActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                            else
                            {
                                if(a.getIsvalid()==1)
                                {
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this,"该账号已被管理员封禁",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else
                                {
                                    if(!a.getPassword().equals(password.getText().toString()))
                                    {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    else
                                    {
                                        Looper.prepare();
                                        Log.d("MainActivity","查询到的user为"+a.toString());
                                        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putBoolean("is_login",true);
                                        editor.putString("user_id",a.getUserId()+"");
                                        Log.d("MainActivity","提交前的userID"+a.getUserId());
                                        editor.commit();
                                        Log.d("MainActivity","提交后的userID"+sharedPreferences.getString("user_id",""));
                                        Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(MainActivity.this,MainPage.class);
                                        startActivity(intent);
                                        Looper.loop();
                                    }
                                }
                            }
                        }
                    }).start();

                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterPage.class);
                startActivity(intent);
            }
        });

    }
    public void loginWithOkHttp(String address, final String account, String password){
        HttpUtil.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.d("----------",responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("true")){
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,Mainpage.class);
//                            SharedPreferences sharedPreferences=getSharedPreferences("state",MODE_PRIVATE);
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putBoolean("is_login",true);
//                            editor.putString("userId",account);
//                            editor.commit();
//                            startActivityForResult(intent,0);
                        }else{
                            Toast.makeText(MainActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        account.setText("");
        password.setText("");
    }
    class QueryBlogByText extends AsyncTask<String, Integer, String> {
        Interface inter = new Interface();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            return "done";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String text) {

        }
    }
}
