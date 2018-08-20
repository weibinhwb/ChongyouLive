package com.example.weibin.chongyoulive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weibin.chongyoulive.register_login.LoginActivity;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;

import java.util.List;

public class AboutMeFragment extends Fragment implements View.OnClickListener{

    private ImageView mAboutMeImage;
    private TextView mAboutMeName, mAboutAddLive;
    private static final String TAG = "AboutMeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        mAboutMeImage = view.findViewById(R.id.about_me_image);
        mAboutMeName = view.findViewById(R.id.about_me_name);
        mAboutAddLive = view.findViewById(R.id.about_me_add_live);

        mAboutAddLive.setOnClickListener(this);
        mAboutMeImage.setOnClickListener(this);
        return view;
    }

    private void getMyLive() {
        TIMValueCallBack<List<TIMGroupBaseInfo>> cb = new TIMValueCallBack<List<TIMGroupBaseInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(List<TIMGroupBaseInfo> timGroupBaseInfos) {
                //获取我加入的live的资料
            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_me_add_live:
                startActivity(new Intent(getContext(), AddLiveActivity.class));
                break;
            case R.id.about_me_image:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }
}
