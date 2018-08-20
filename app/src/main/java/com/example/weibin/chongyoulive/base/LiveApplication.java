package com.example.weibin.chongyoulive.base;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;

public class LiveApplication extends Application {

    private static LiveApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(Base.SDK_APPID).enableCrashReport(false).enableLogPrint(true).setLogLevel(TIMLogLevel.DEBUG).setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化 SDK
        TIMManager.getInstance().init(getApplicationContext(), config);

        //检测是否初始化并集成成功
        Log.e("LiveApplication", TIMManager.getInstance().getVersion());
    }

    public static LiveApplication getInstance(){
        return instance;
    }

}
