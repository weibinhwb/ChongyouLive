package com.example.weibin.chongyoulive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.activity.RegisterActivity;

public class PassWordCommitFragment extends Fragment implements View.OnClickListener{

    private EditText mPassWordEdit;
    private Button mPassWordButton;
    private RegisterActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RegisterActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_password, container, false);
        mPassWordEdit = view.findViewById(R.id.register_password);
        mPassWordButton = view.findViewById(R.id.register_commit_password);
        mPassWordButton.setOnClickListener(this);
        initToolbar();
        return view;
    }

    private void initToolbar(){
        Toolbar toolbar = mActivity.findViewById(R.id.register_toolbar);
        mActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> mActivity.popBackStack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_commit_password:
                String password = mPassWordEdit.getText().toString();
                mActivity.getTLSHelper().TLSPwdRegCommit(password, mActivity.getTLSPwdRegListener());
                break;
        }
    }
}
