package com.example.weibin.chongyoulive.ui.activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.fragment.AboutMeFragment;
import com.example.weibin.chongyoulive.ui.fragment.HomeFragment;
import com.example.weibin.chongyoulive.ui.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Fragment> mFragmentList;
    private List<String> mTabName;
    private MainPagerAdapter mPagerAdapter;
    private ViewPager mMainPager;
    private TabLayout mMainTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setTitle("Live");
        setSupportActionBar(mainToolbar);
        mMainPager = findViewById(R.id.main_pager);
        mMainTab = findViewById(R.id.main_tablayout);
        mFragmentList = new ArrayList<>();
        mTabName = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new AboutMeFragment());
        mTabName.add("Live小讲");
        mTabName.add("我的");
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentList, mTabName);
        mMainPager.setAdapter(mPagerAdapter);
        mMainTab.setupWithViewPager(mMainPager);
    }
}
