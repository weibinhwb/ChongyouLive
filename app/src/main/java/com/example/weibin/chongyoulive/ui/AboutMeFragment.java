package com.example.weibin.chongyoulive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.BaseFragment;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;

import java.util.List;

public class AboutMeFragment extends BaseFragment {

    private RecyclerView mAboutMeLiveRecycler;
    private AboutMyLiveAdapter mMyLiveAdapter;
    private static final String TAG = "AboutMeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        mAboutMeLiveRecycler = view.findViewById(R.id.about_me_recycler);
        mMyLiveAdapter = new AboutMyLiveAdapter(getContext());
        mAboutMeLiveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAboutMeLiveRecycler.setAdapter(mMyLiveAdapter);
        return view;
    }

    @Override
    protected void loadData() {
        super.loadData();
        getMyDetailInfo();
        getMyLive();
    }

    private void getMyDetailInfo() {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "获取个人资料出错");
            }

            @Override
            public void onSuccess(TIMUserProfile timUserProfile) {
                Log.d(TAG, "onSuccess: " + "获取个人资料成功");
                mMyLiveAdapter.setTIMUserProfile(timUserProfile);
                mMyLiveAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getMyLive() {
        TIMValueCallBack<List<TIMGroupBaseInfo>> cb = new TIMValueCallBack<List<TIMGroupBaseInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "获取我的Live失败");
            }

            @Override
            public void onSuccess(List<TIMGroupBaseInfo> timGroupBaseInfos) {
                mMyLiveAdapter.setGroupBaseInfos(timGroupBaseInfos);
                mMyLiveAdapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: " + "展示我的Live");
            }
        };
        TIMGroupManagerExt.getInstance().getGroupList(cb);
    }
}
