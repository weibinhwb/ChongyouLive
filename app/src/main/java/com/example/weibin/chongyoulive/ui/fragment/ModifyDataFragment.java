package com.example.weibin.chongyoulive.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.activity.AboutMeDetailActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;

/**
 * Created by weibin on 2018/8/21
 */


public class ModifyDataFragment extends Fragment implements View.OnClickListener{

    private String mItemName;
    private String mItemContent;

    private EditText mModifyContent;
    private Toolbar mModifyToolbar;
    private TextView mCommitView;
    private ImageView mDeleteView;
    private AboutMeDetailActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mItemName = bundle.getString("param1");
        mItemContent = bundle.getString("param2");
        mActivity = (AboutMeDetailActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_mydetail, container, false);
        mModifyToolbar = view.findViewById(R.id.modify_toolbar);
        mActivity.setSupportActionBar(mModifyToolbar);
        mModifyToolbar.setTitle(mItemName);
        mModifyToolbar.setNavigationIcon(R.drawable.back_icon);
        mModifyToolbar.setNavigationOnClickListener(v -> {
            mActivity.popBackStack();
        });
        mDeleteView = view.findViewById(R.id.modify_content_deleteAll);
        mDeleteView.setOnClickListener(this);
        mModifyContent = view.findViewById(R.id.modify_content);
        mModifyContent.setText(mItemContent);
        mCommitView = view.findViewById(R.id.modify_commit);
        mCommitView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_commit:
                TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
                if (mItemName.equals("用户名")){
                    param.setNickname(mModifyContent.getText().toString());
                } else if (mItemName.equals("一句话描述")) {
                    param.setSelfSignature(mModifyContent.getText().toString());
                }
                TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        mActivity.popBackStack();
                    }
                });
                break;
            case R.id.modify_content_deleteAll:
                mModifyContent.setText("");
                break;
        }
    }
}
