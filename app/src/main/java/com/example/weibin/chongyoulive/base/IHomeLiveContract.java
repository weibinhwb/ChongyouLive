package com.example.weibin.chongyoulive.base;

import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;

import java.util.List;

public interface IHomeLiveContract {
    interface IHomePresenter {
        void getData(List<TIMGroupDetailInfo> timGroupDetailInfos);
        void failed();
    }
    interface IHomeView {
        void showView(List<TIMGroupDetailInfo> timGroupDetailInfos);
        void failed();
    }
}
