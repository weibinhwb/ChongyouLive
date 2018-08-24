package com.example.weibin.chongyoulive.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "LoginActivity";
    private Button mCommitLoginButton;
    private EditText mMobileEdit, mPasswordEdit;

    private String mIdentify;
    private String mPassword;

    private TLSLoginHelper mTLSLoginHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar loginToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        mCommitLoginButton = findViewById(R.id.login_commit);
        mMobileEdit = findViewById(R.id.login_account);
        mPasswordEdit = findViewById(R.id.login_password);
        mCommitLoginButton.setOnClickListener(this);

    }

    private void login() {
        mTLSLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Base.SDK_APPID, Base.ACCOUNT_TYPE, "1.0");
        mTLSLoginHelper.setTimeOut(5000);
        mTLSLoginHelper.TLSPwdLogin(mIdentify, mPassword.getBytes(), new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                String userSig = mTLSLoginHelper.getUserSig(tlsUserInfo.identifier);
                TIMManager.getInstance().login(mIdentify, userSig, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: " + "登录失败");
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: " + "登录成功");
                        getUser();
                    }
                });
            }

            @Override
            public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {

            }

            @Override
            public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {

            }

            @Override
            public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                Toast.makeText(LoginActivity.this, "网络超时...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_commit:
                mIdentify = "86-" + mMobileEdit.getText().toString();
                mPassword = mPasswordEdit.getText().toString();
                login();
                break;
        }
    }

    private void getUser () {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>(){
            @Override
            public void onError(int code, String desc){
                Log.e(TAG, "getSelfProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess(TIMUserProfile result){
                Log.e(TAG, "getSelfProfile succ");
                Log.e(TAG, "identifier: " + result.getIdentifier() + " nickName: " + result.getNickName()
                        + " remark: " + result.getRemark() + " allow: " + result.getAllowType());
                new Thread(() -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserId(mIdentify);
                    userEntity.setUserPassword(mPassword);
                    userEntity.setFaceUrl(result.getFaceUrl());
                    userEntity.setNickName(result.getNickName());
                    userEntity.setSlogan(result.getSelfSignature());
                    UserDatabase.getINSTANCE(LoginActivity.this).getUserEntityDao().insertUser(userEntity);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }).start();
            }
        });
    }
}
