package com.example.weibin.chongyoulive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.activity.RegisterActivity;

public class QueryCodeFragment extends Fragment implements View.OnClickListener{

    private EditText mMobile;
    private Button mQueryCode;
    private RegisterActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RegisterActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_query_code, container, false);
        mMobile = view.findViewById(R.id.register_mobie);
        mQueryCode = view.findViewById(R.id.register_query_code);
        mQueryCode.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_query_code:
                String mobile = mMobile.getText().toString();
                mActivity.getTLSHelper().TLSPwdRegAskCode("86-" + mobile, mActivity.getTLSPwdRegListener());
                break;
        }
    }
}
