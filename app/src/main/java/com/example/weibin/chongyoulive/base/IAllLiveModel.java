package com.example.weibin.chongyoulive.base;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public interface IAllLiveModel {
    void getAllLive();
    void failed();
    void setPresenter(IHomePresenter<DetailLiveBean> iHomePresenter);
}
