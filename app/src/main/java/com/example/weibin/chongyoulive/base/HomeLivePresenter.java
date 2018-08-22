package com.example.weibin.chongyoulive.base;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeLivePresenter implements IHomePresenter<DetailLiveBean>{

    private IAllLiveModel mLiveModel;
    private IHomeView<DetailLiveBean> mIHomeView;

    public HomeLivePresenter(IHomeView<DetailLiveBean> view) {
        this.mIHomeView = view;
        mLiveModel = new HomeLiveModel();
        mLiveModel.setPresenter(this);
        mLiveModel.getAllLive();
    }

    @Override
    public void getData(DetailLiveBean detailLiveBean) {
        mIHomeView.showView(detailLiveBean);
    }

    @Override
    public void failed() {
        mLiveModel.failed();
    }
}
