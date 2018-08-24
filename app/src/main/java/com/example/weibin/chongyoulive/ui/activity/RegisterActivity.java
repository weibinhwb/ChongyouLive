package com.example.weibin.chongyoulive.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.fragment.PassWordCommitFragment;
import com.example.weibin.chongyoulive.ui.fragment.QueryCodeFragment;
import com.example.weibin.chongyoulive.ui.fragment.VerifyCodeFragment;
import com.tencent.qalsdk.QALSDKManager;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSHelper;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterActivity extends AppCompatActivity {

    private TLSHelper mTLSHelper;
    private TLSPwdRegListener mTLSPwdRegListener;
    private static final String TAG = "RegisterActivity";
    private Fragment verifyCodeFragment, queryCodeFragment, passwordCommitFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar registerToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(registerToolbar);

        QALSDKManager.getInstance().setEnv(0);
        QALSDKManager.getInstance().init(getApplicationContext(), Base.SDK_APPID);

        mTLSHelper = TLSHelper.getInstance().init(getApplicationContext(), Base.SDK_APPID);

        mTLSPwdRegListener = new TLSPwdRegListener() {
            @Override
            public void OnPwdRegAskCodeSuccess(int i, int i1) {
                //发送短信成功，跳转到验证码输入界面，调用OnPwdRegVerifyCode进行验证
                Log.d(TAG, "OnPwdRegAskCodeSuccess: " + "发送短信成功");
                verifyCodeFragment = new VerifyCodeFragment();
                showFragment(verifyCodeFragment, queryCodeFragment);
            }

            @Override
            public void OnPwdRegReaskCodeSuccess(int i, int i1) {
                //重新请求短信成功
            }

            @Override
            public void OnPwdRegVerifyCodeSuccess() {
                //短信验证成功，调用PwdRegCommit进行注册的最后一步
                Log.d(TAG, "OnPwdRegVerifyCodeSuccess: " + "验证短信成功");
                passwordCommitFragment = new PassWordCommitFragment();
                showFragment(passwordCommitFragment, verifyCodeFragment);
            }

            @Override
            public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
                //最终注册成功，引导用户登录
                Log.d(TAG, "OnPwdRegCommitSuccess: " + "设置密码成功");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
                /* 密码注册过程中任意一步都可以到达这里，可以根据tlsErrInfo 中ErrCode, Title, Msg 给用户弹提示语，引导相关操作*/
                Log.d(TAG, "OnPwdRegFail: " + "出错");
                Log.d(TAG, "OnPwdRegFail: " + tlsErrInfo.ErrCode);
                Log.d(TAG, "OnPwdRegFail: " + tlsErrInfo.ExtraMsg);
                switch (tlsErrInfo.ErrCode) {
                    case 2:
                        Toast.makeText(RegisterActivity.this, "该账号已经注册...", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }

            @Override
            public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
                /* 密码注册过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定，一般让用户重试即可*/
                Log.d(TAG, "OnPwdRegTimeout: " + "网络出现问题");
            }
        };

        mTLSHelper.TLSPwdRegReaskCode(mTLSPwdRegListener);
        queryCodeFragment = new QueryCodeFragment();
        addFragment(queryCodeFragment);
    }

    public void verifyCode(String code) {
        mTLSHelper.TLSPwdRegVerifyCode(code, mTLSPwdRegListener);
    }

    public void commitPassword(String password) {
        mTLSHelper.TLSPwdRegCommit(password, mTLSPwdRegListener);
    }

    public TLSHelper getTLSHelper() {
        return mTLSHelper;
    }

    public void setTLSHelper(TLSHelper TLSHelper) {
        mTLSHelper = TLSHelper;
    }

    public TLSPwdRegListener getTLSPwdRegListener() {
        return mTLSPwdRegListener;
    }

    public void setTLSPwdRegListener(TLSPwdRegListener TLSPwdRegListener) {
        mTLSPwdRegListener = TLSPwdRegListener;
    }

    private FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.beginTransaction();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.register_container, fragment);
        transaction.commit();
    }

    public void replaceFragmentUseStack(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.register_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.add(R.id.register_container, fragment);
        transaction.commit();
    }

    public void showFragment(Fragment show, Fragment hide) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.add(R.id.register_container, show).addToBackStack(null).show(show).hide(hide).commit();
    }

    public void popBackStack(String name) {
        getSupportFragmentManager().popBackStack(name, 0);
    }

    public void popBackStack() {
        getSupportFragmentManager().popBackStack();

    }
}
