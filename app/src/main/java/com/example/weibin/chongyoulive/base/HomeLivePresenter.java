package com.example.weibin.chongyoulive.base;

import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeLivePresenter implements IHomePresenter{

    private IAllLiveModel mLiveModel;
    private IHomeView mIHomeView;

    public HomeLivePresenter(IHomeView view) {
        this.mIHomeView = view;
        mLiveModel = new HomeLiveModel();
        mLiveModel.setPresenter(this);
        mLiveModel.getAllLive();
    }

    @Override
    public void getData(List<TIMGroupDetailInfo> timGroupDetailInfos) {
        mIHomeView.showView(timGroupDetailInfos);
    }

    @Override
    public void failed() {
        mLiveModel.failed();
    }
}
