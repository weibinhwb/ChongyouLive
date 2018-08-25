package com.example.weibin.chongyoulive.base;

import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;

import java.util.List;

public interface IHomeLiveContract {

    interface IHomePresenter<T> {
        void getData(T t);
        void failed();
        void loadData();
    }
    interface IHomeView<T>{
        void showView(T t);
        void failed();
    }
}
