package com.example.weibin.chongyoulive.base;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;
import com.example.weibin.chongyoulive.register_login.LoginActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;

import java.util.List;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

import static android.support.constraint.Constraints.TAG;

public class LiveApplication extends Application {

    private static LiveApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(Base.SDK_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化 SDK
        TIMManager.getInstance().init(getApplicationContext(), config);

        //检测是否初始化并集成成功
        Log.d("LiveApplication", TIMManager.getInstance().getVersion());

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity = UserDatabase.getINSTANCE(getInstance()).getUserEntityDao().queryUser();
                if (userEntity == null) {
                    return;
                }
                String userId = userEntity.getUserId();
                String userPassword = userEntity.getUserPassword();
                if (userId != null && userPassword != null) {
                    Log.d(TAG, "onCreate: " + userId + userPassword);
                    login(userId, userPassword);
                }
            }
        }).start();
    }

    private void login(final String userId, final String password) {
        final TLSLoginHelper tlsLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Base.SDK_APPID, Base.ACCOUNT_TYPE, "1.0");
        tlsLoginHelper.TLSPwdLogin(userId, password.getBytes(), new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                Log.d(TAG, "OnPwdLoginSuccess: " + tlsUserInfo.describeContents());
                String userSig = tlsLoginHelper.getUserSig(tlsUserInfo.identifier);
                Log.d(TAG, "OnPwdLoginSuccess: " + userSig);
                TIMManager.getInstance().login(userId, userSig, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: " + s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: ");
                        Toast.makeText(LiveApplication.this, "欢迎回来", Toast.LENGTH_SHORT).show();
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

    public static LiveApplication getInstance(){
        return instance;
    }

}
