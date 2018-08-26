package com.example.weibin.chongyoulive.base;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupSettings;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.protocol.msg;

import java.util.List;

import okhttp3.OkHttpClient;

public class LiveApplication extends Application {

    private static LiveApplication instance;
    private static final String TAG = "LiveApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        init();
        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(Base.SDK_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        TIMGroupSettings settings = new TIMGroupSettings();
        TIMGroupSettings.Options options = new TIMGroupSettings.Options();
        options.addCustomTag(Base.LIVE_INTRO);
        options.addCustomTag(Base.LIVE_OUTLINE);
        options.addCustomTag(Base.LIVE_OWNER);
        options.addCustomTag(Base.LIVE_TIME);
        options.addCustomTag(Base.LIVE_OWNER_INTRODUCE);
        settings.setGroupInfoOptions(options);
        TIMUserConfig config1 = new TIMUserConfig().setGroupSettings(settings);
        //初始化 SDK
        TIMManager.getInstance().init(getApplicationContext(), config);
        TIMManager.getInstance().setUserConfig(config1);

        //检测是否初始化并集成成功
        Log.d("LiveApplication", TIMManager.getInstance().getVersion());
    }

    private void init(){
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    public static LiveApplication getInstance(){
        return instance;
    }
}
