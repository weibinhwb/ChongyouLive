package com.example.weibin.chongyoulive.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.BaseFragment;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by weibin on 2018/8/21
 */


public class AboutMeDetailFragment extends BaseFragment implements View.OnClickListener{

    private FloatingActionButton mFB;
    private CircleImageView mDetailPhotoView;
    private TextView mDetailNameView, mDetailSloganView;
    private AboutMeDetailActivity mActivity;
    private final static String TAG = "AboutMeDetailFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AboutMeDetailActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me_detail, container, false);
        mDetailNameView = view.findViewById(R.id.detail_name);
        mDetailPhotoView = view.findViewById(R.id.detail_photo);
        mDetailSloganView = view.findViewById(R.id.detail_slogan);
        mFB = view.findViewById(R.id.about_me_fb);
        mFB.setOnClickListener(this);
        getMyDetailInfo();
        return view;
    }

    @Override
    protected void loadData() {
        super.loadData();
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
                mDetailNameView.setText(timUserProfile.getNickName());
                mDetailSloganView.setText(timUserProfile.getSelfSignature());
                Glide.with(mDetailPhotoView.getContext()).load(timUserProfile.getFaceUrl()).into(mDetailPhotoView);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_me_fb:
                mActivity.showFragment(new ModifyMyDetailFragment(), this);
                break;
        }
    }
}
