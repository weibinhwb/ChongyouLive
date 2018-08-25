package com.example.weibin.chongyoulive.base;

import android.util.Log;

import com.example.weibin.chongyoulive.bean.DetailLiveBean;
import com.example.weibin.chongyoulive.bean.LiveBean;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;
import static tencent.tls.report.QLog.TAG;
import static tencent.tls.report.QLog.i;

public class HomeLiveModel implements IAllLiveModel {

    private IHomePresenter<List<LiveData>> mIHomePresenter;
    private static HttpService sHttpService = null;
    private Disposable mDisposable;
    private List<String> mList;
    private List<LiveData> mLiveData;


    public HomeLiveModel() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("RetrofitLog", "retrofitBack = " + message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base.ALL_LIVES).client(client).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        sHttpService = retrofit.create(HttpService.class);
    }

    private void getLiveId(LiveBean liveBean) {
        mList = new ArrayList<>();
        for (int i = 0; i < liveBean.getTotalCount(); i++) {
            mList.add(liveBean.getGroupIdList().get(i).getGroupId());
        }
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllLive() {
        Map<String, String> map = new HashMap<>();
        map.put("usersig", Base.USER_SIG);
        map.put("identifier", Base.IDENTIFIER);
        map.put("sdkappid", Base.SDK_APPID + "");
        map.put("contenttype", "json");
        mDisposable = sHttpService.getAllLive(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::getLiveId, throwable -> mIHomePresenter.failed());
    }

    private void loadData() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            jsonArray.put(mList.get(i));
        }
        jsonObject.put("GroupIdList", jsonArray);
        HashMap<String, String> map = new HashMap<>();
        map.put("usersig", Base.USER_SIG);
        map.put("identifier", Base.IDENTIFIER);
        map.put("sdkappid", Base.SDK_APPID + "");
        map.put("contenttype", "json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), jsonObject.toString());
        mDisposable = sHttpService.getDetailLives(map, requestBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(detailLiveBean -> {
            mLiveData = new ArrayList<>();
            for (int i = 0; i < detailLiveBean.getGroupInfo().size(); i++) {
                LiveData liveData = new LiveData();
                liveData.setLiveFace(detailLiveBean.getGroupInfo().get(i).getFaceUrl());
                liveData.setOwnerName(detailLiveBean.getGroupInfo().get(i).getNotification());
                liveData.setLiveJoinPerson(detailLiveBean.getGroupInfo().get(i).getMemberNum() + "");
                liveData.setLiveName(detailLiveBean.getGroupInfo().get(i).getName());
                liveData.setLiveDate(detailLiveBean.getGroupInfo().get(i).getCreateTime());
                mLiveData.add(liveData);
            }
            Log.d(TAG, "onSuccess: " + "一共有" + detailLiveBean.getGroupInfo().size() + "Ge");
            Collections.sort(mLiveData, (o1, o2) -> {
                if (o1.getLiveDate() > o2.getLiveDate()) {
                    return -1;
                } else if (o1.getLiveDate() < o2.getLiveDate()) {
                    return 1;
                }
                return 0;
            });
            mIHomePresenter.getData(mLiveData);
        }, throwable -> {

        });
    }

    @Override
    public void failed() {
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void setPresenter(IHomePresenter<List<LiveData>> iHomePresenter) {
        this.mIHomePresenter = iHomePresenter;
    }
}
