package com.example.weibin.chongyoulive.base;

import com.example.weibin.chongyoulive.bean.DetailLiveBean;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public interface IAllLiveModel {
    void getAllLive();
    void failed();
    void setPresenter(IHomePresenter<List<LiveData>> iHomePresenter);
}
