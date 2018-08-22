package com.example.weibin.chongyoulive.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSRefreshUserSigListener;
import tencent.tls.platform.TLSUserInfo;

public class StartAppActivity extends AppCompatActivity {

    private TLSLoginHelper mTLSLoginHelper;
    private static final String TAG = "START_APP_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        Observable.create((ObservableOnSubscribe<List<UserEntity>>) emitter -> {
            List<UserEntity> userEntities = UserDatabase.getINSTANCE(StartAppActivity.this).getUserEntityDao().queryUser();
            emitter.onNext(userEntities);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userEntity -> {
                    if (userEntity == null || userEntity.size() == 0){
                        Log.d(TAG, "onCreate: " + "start登录界面");
                        exchangeActivity(LoginActivity.class);
                    } else {
                        mTLSLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Base.SDK_APPID, Base.ACCOUNT_TYPE, "1.0");
                        localLogin();
                    }
                }).isDisposed();
    }

    private void localLogin() {
        TLSRefreshUserSigListener refreshUserSigListener = new TLSRefreshUserSigListener() {
            @Override
            public void OnRefreshUserSigSuccess(TLSUserInfo tlsUserInfo) {
                String userSig = mTLSLoginHelper.getUserSig(tlsUserInfo.identifier);
                TIMManager.getInstance().login(tlsUserInfo.identifier, userSig, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: " + "本地登录失败，更新票据");
                        exchangeActivity(LoginActivity.class);
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: " + "本地登录成功，更新票据");
                        exchangeActivity(MainActivity.class);
                    }
                });
            }

            @Override
            public void OnRefreshUserSigFail(TLSErrInfo tlsErrInfo) {
                //需要重新登录
                Observable.create((ObservableEmitter<UserEntity> emitter) -> {
                    List<UserEntity> userEntities = UserDatabase.getINSTANCE(StartAppActivity.this).getUserEntityDao().queryUser();
                    if (userEntities == null || userEntities.size() == 0) {
                        emitter.onComplete();
                    } else {
                        emitter.onNext(userEntities.get(0));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userEntity -> {
                            if (userEntity == null) {
                                return;
                            }
                            String userId = userEntity.getUserId();
                            String userPassword = userEntity.getUserPassword();
                            if (userId != null && userPassword != null) {
                                Log.d(TAG, "onCreate: " + userId + userPassword);
                                login(userId, userPassword);
                            }
                        }, throwable -> {
                            Log.d(TAG, "OnRefreshUserSigFail: " + "登录失败，重新登录");
                            exchangeActivity(LoginActivity.class);
                        }).isDisposed();
            }

            @Override
            public void OnRefreshUserSigTimeout(TLSErrInfo tlsErrInfo) {

            }
        };
        TLSUserInfo tlsUserInfo = mTLSLoginHelper.getLastUserInfo();
        boolean hasLogin = tlsUserInfo != null && !mTLSLoginHelper.needLogin(tlsUserInfo.identifier);
        if (hasLogin) {
            mTLSLoginHelper.TLSRefreshUserSig(tlsUserInfo.identifier, refreshUserSigListener);
        }

    }

    private void login(final String userId, final String password) {
        mTLSLoginHelper.TLSPwdLogin(userId, password.getBytes(), new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                String userSig = mTLSLoginHelper.getUserSig(tlsUserInfo.identifier);
                TIMManager.getInstance().login(userId, userSig, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: " + s);
                        exchangeActivity(LoginActivity.class);
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: " + "密码从数据库获取------登录成功");
                        exchangeActivity(MainActivity.class);
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
                Log.d(TAG, "OnPwdLoginFail: " + "网络请求登录，密码从数据库获取-----失败");
            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                Log.d(TAG, "OnPwdLoginTimeout: " + "网络请求登录，密码从数据库获取----超时");
            }
        });
    }

    private void exchangeActivity(Class className) {
        Intent intent = new Intent(StartAppActivity.this, className);
        startActivity(intent);
        finish();
    }
}
