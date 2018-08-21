package com.example.weibin.chongyoulive.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by weibin on 2018/8/21
 */


public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    private boolean isViewCreated;
    private boolean isUIVisible;

    protected void loadData() {
        Log.d(TAG, getClass().getName() + "--------> loadData");
    }

    private void lazyLoad() {
        if ( isUIVisible && isViewCreated && isVisible()) {
            loadData();
            isUIVisible = false;
            isViewCreated = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUIVisible = false;
    }
}
