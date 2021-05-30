package com.example.a22857.cohelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a22857.cohelp.adapter.NotePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyNeedPage extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
   private  RadioButton button1,button2,button3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myneedpage);
        viewPager=(ViewPager) findViewById(R.id.myneedpageviewpager);
        radioGroup=(RadioGroup) findViewById(R.id.myneedpagegroup);
        button1=(RadioButton)findViewById(R.id.myneedpagebutton1);
        button2=(RadioButton)findViewById(R.id.myneedpagebutton2);
        button3=(RadioButton)findViewById(R.id.myneedpagebutton3);
        initView();
    }
    public void initView()
    {
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());
        NotePagerAdapter pagerAdapter=new NotePagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        button1.setChecked(true);
                        button1.setTextSize(30);
                        button2.setTextSize(25);
                        button3.setTextSize(25);
                        break;
                    case 1:
                        button2.setChecked(true);
                        button1.setTextSize(25);
                        button2.setTextSize(30);
                        button3.setTextSize(25);
                        break;
                    case 2:
                        button3.setChecked(true);
                        button1.setTextSize(25);
                        button2.setTextSize(25);
                        button3.setTextSize(30);
                        break;
                    default:break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==button1.getId())
                {
                    viewPager.setCurrentItem(1);
                }
            }
        });
        viewPager.setCurrentItem(0);
    }


}
