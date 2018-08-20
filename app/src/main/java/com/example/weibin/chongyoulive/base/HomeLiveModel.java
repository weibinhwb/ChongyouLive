package com.example.weibin.chongyoulive.base;

import android.util.Log;

import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;
import static tencent.tls.report.QLog.TAG;

public class HomeLiveModel implements IAllLiveModel{

    private static HttpService mHttpService;
    private IHomePresenter mIHomePresenter;
    private Disposable mDisposable;
    private List<String> mList;

    public HomeLiveModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base.ALL_LIVES)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mHttpService = retrofit.create(HttpService.class);
    }

    @Override
    public void getAllLive() {
        mDisposable = mHttpService.getAllLive(Base.USER_SIG, Base.IDENTIFIER, Base.SDK_APPID, "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LiveBean>() {
                    @Override
                    public void accept(LiveBean liveBean) throws Exception {
                        getLiveId(liveBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mIHomePresenter.failed();
                    }
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
    public void setPresenter(IHomePresenter iHomePresenter) {
        this.mIHomePresenter = iHomePresenter;
    }

    private void getLiveId(LiveBean liveBean) {
        mList = new ArrayList<>();
        for (int i = 0; i < liveBean.getTotalCount(); i ++) {
            mList.add(liveBean.getGroupIdList().get(i).getGroupId());
        }
        getLiveDetail();
    }

    private void getLiveDetail(){
        TIMGroupManagerExt.getInstance().getGroupDetailInfo(mList, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                mIHomePresenter.getData(timGroupDetailInfos);
            }
        });
    }
}
