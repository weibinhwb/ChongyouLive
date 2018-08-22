package com.example.weibin.chongyoulive.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.weibin.chongyoulive.R;

public class AboutMeDetailActivity extends AppCompatActivity {

    private Toolbar mDetailToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me_detail);
        mDetailToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mDetailToolbar);

        addFragment(new AboutMeDetailFragment());
    }


    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.beginTransaction();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.detail_container,fragment);
        transaction.commit();
    }

    public void replaceFragmentUseStack(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.detail_container , fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.add(R.id.detail_container , fragment);
        transaction.commit();
    }

    public void showFragment(Fragment show, Fragment hide) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.add(R.id.detail_container, show).addToBackStack(null).show(show).hide(hide).commit();
    }

    public void popBackStack(String name) {
        getSupportFragmentManager().popBackStack(name, 0);
    }

    public void popBackStack() {
        getSupportFragmentManager().popBackStack();

    }
}
