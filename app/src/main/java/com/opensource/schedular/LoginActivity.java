package com.opensource.schedular;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Adapters.UserLoginPageAdapter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViewPager();
    }


    @Override
    public void onClick(View v) {

    }


    private void setupViewPager(){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SIGN IN"));
        tabLayout.addTab(tabLayout.newTab().setText("SIGN UP"));
//        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
//        new
        tabLayout.setTabTextColors(Color.parseColor("#013244"), Color.parseColor("#ffffff"));
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //R.layout is the previous defined xml
            TextView tv=(TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);

        }
        //
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final UserLoginPageAdapter adapter = new UserLoginPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
}
