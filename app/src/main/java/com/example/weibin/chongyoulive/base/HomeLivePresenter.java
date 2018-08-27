package com.example.weibin.chongyoulive.base;

import com.example.weibin.chongyoulive.bean.LiveData;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeLivePresenter implements IHomePresenter<List<LiveData>> {

    private IAllLiveModel mLiveModel;
    private IHomeView<List<LiveData>> mIHomeView;

    public HomeLivePresenter(IHomeView<List<LiveData>> view) {
        this.mIHomeView = view;
        mLiveModel = new HomeLiveModel();
        mLiveModel.setPresenter(this);
        mLiveModel.getAllLive();
    }


    @Override
    public void getData(List<LiveData> liveData) {
        mIHomeView.showView(liveData);
    }

    @Override
    public void failed() {
        mLiveModel.failed();
        mIHomeView.failed();
    }

    @Override
    public void loadData() {
        mLiveModel.getAllLive();
    }
}
