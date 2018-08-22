package com.example.weibin.chongyoulive.base;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.protocol.msg;

import java.util.List;

public class LiveApplication extends Application {

    private static LiveApplication instance;
    private static final String TAG = "LiveApplication";
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
    }

    public static LiveApplication getInstance(){
        return instance;
    }
}
