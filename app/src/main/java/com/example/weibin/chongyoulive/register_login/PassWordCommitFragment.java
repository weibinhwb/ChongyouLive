package com.example.weibin.chongyoulive.register_login;

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
        return view;
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
