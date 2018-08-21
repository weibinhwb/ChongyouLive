package com.example.weibin.chongyoulive.register_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;
import com.example.weibin.chongyoulive.ui.LiveActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import java.util.List;

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

        initTLS();
    }

    private void initTLS() {
        mTLSLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Base.SDK_APPID, Base.ACCOUNT_TYPE, "1.0");
        mTLSLoginHelper.setTimeOut(5000);
    }

    private void login() {
        mTLSLoginHelper.TLSPwdLogin(mIdentify, mPassword.getBytes(), new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                Log.d(TAG, "OnPwdLoginSuccess: " + tlsUserInfo.describeContents());
                String userSig = mTLSLoginHelper.getUserSig(tlsUserInfo.identifier);
                Log.d(TAG, "OnPwdLoginSuccess: " + userSig);
                TIMManager.getInstance().login(mIdentify, userSig, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: ");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserEntity userEntity = new UserEntity();
                                userEntity.setUserId(mIdentify);
                                userEntity.setUserPassword(mPassword);
                                UserDatabase.getINSTANCE(LoginActivity.this).getUserEntityDao().insertUser(userEntity);
                            }
                        }).start();
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
                Log.d(TAG, "OnPwdLoginFail: " + tlsErrInfo.Title);
                Log.d(TAG, "OnPwdLoginFail: " + tlsErrInfo.ErrCode);
            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                Log.d(TAG, "OnPwdLoginTimeout: ");
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
}
