package com.example.a22857.cohelp;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class OtherActivity extends AppCompatActivity {

    private ImageView iv_fangda_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otheractivity);
        iv_fangda_img = findViewById(R.id.iv_fangda_img);
        iv_fangda_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 注意这里不使用finish
                ActivityCompat.finishAfterTransition(OtherActivity.this);
            }
        });
    }
}