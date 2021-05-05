package com.example.a22857.cohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

import Entity.User;

public class PersonPage extends AppCompatActivity{
    private TextView nameview;
    private TextView moneyview;
    private TextView descriptionview;
    private TextView myneedview;
    private TextView myapplyview;
    private ImageView headview;
    private TextView mydoingneedview;
    private Button mainpagebutton;
    private Button addbutton;
    private Button personbutton;
    private Button exitbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personpage);
        nameview=(TextView)findViewById(R.id.personpagename);
        moneyview=(TextView)findViewById(R.id.personpagemoney);
        descriptionview=(TextView)findViewById(R.id.personpagedescription);
        myneedview=(TextView)findViewById(R.id.personpagemyneed);
        mydoingneedview=(TextView)findViewById(R.id.personpagemydoingneed);
        mainpagebutton=(Button)findViewById(R.id.personpagemain);
        addbutton=(Button)findViewById(R.id.personpageadd);
        personbutton=(Button)findViewById(R.id.personpageperson);
        headview=(ImageView)findViewById(R.id.personpagehead);
        myapplyview=(TextView)findViewById(R.id.personpagemyapplyneed);
        exitbtn=(Button)findViewById(R.id.personpagequitbutton);
        mainpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainPage.class);
                startActivityForResult(intent,0);
            }
        });
        initView();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainPage.class);
                startActivity(intent);
            }
        });
        personbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
        myneedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MyPublishNeedPage.class);
                startActivityForResult(intent,0);
            }
        });
        mydoingneedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MydoingNeedPage.class);
                startActivityForResult(intent,0);
            }
        });
        myapplyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,ApplyNeedPage.class);
                startActivityForResult(intent,0);
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonPage.this,MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }
    public void initView()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        final String userid=sharedPreferences.getString("user_id","");
        final Interface inter = new Interface();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map=new HashMap();
                map.put("userid",userid);
                String result=inter.doGet("http://10.0.2.2:8080/user/querybyid",map);
                User a= JSON.parseObject(result, User.class);
                Looper.prepare();
                nameview.setText(a.getName());
                moneyview.setText(a.getMoney()+"");
                descriptionview.setText(a.getDescription());
                byte[] bytes= Base64.decode(a.getIcon(),Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                //Log.d("----bitmap","图片的大小为w:"+bitmap.getWidth()+"h："+bitmap.getHeight());
                headview.setImageBitmap(setBitmap(bitmap,100,100));
                Looper.loop();


//                        Looper.prepare();
//                        Log.d("MainActivity","查询到的user为"+a.toString());
//                        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
//                        SharedPreferences.Editor editor=sharedPreferences.edit();
//                        editor.putBoolean("is_login",true);
//                        editor.putString("user_id",a.getUserId()+"");
//                        Log.d("MainActivity","提交前的userID"+a.getUserId());
//                        editor.commit();
//                        Log.d("MainActivity","提交后的userID"+sharedPreferences.getString("user_id",""));
//                        Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(MainActivity.this,MainPage.class);
//                        startActivity(intent);
//                        Looper.loop();





            }
        }).start();
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
