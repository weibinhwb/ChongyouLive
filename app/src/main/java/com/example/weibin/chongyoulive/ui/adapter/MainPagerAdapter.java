package com.example.weibin.chongyoulive.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTabName;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tabName) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTabName = tabName;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabName.get(position);
    }
}
