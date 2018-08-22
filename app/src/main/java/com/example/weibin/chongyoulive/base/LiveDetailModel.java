package com.example.weibin.chongyoulive.base;

import java.io.Serializable;

/**
 * Created by weibin on 2018/8/22
 */


public class LiveDetailModel implements Serializable{
    private String mLiveName;
    private String mLivePhotoUrl;
    private String mLiveOwener;
    private String mLiveIntroduce;
    private String mLiveDate;
    private String mLiveOwenerIntroduce;
    private String mLiveJoinPerson;
    private String mLiveId;

    public String getLiveId() {
        return mLiveId;
    }

    public void setLiveId(String liveId) {
        mLiveId = liveId;
    }

    public String getLiveJoinPerson() {
        return mLiveJoinPerson;
    }

    public void setLiveJoinPerson(String liveJoinPerson) {
        mLiveJoinPerson = liveJoinPerson;
    }

    public String getLiveName() {
        return mLiveName;
    }

    public void setLiveName(String liveName) {
        mLiveName = liveName;
    }

    public String getLivePhotoUrl() {
        return mLivePhotoUrl;
    }

    public void setLivePhotoUrl(String livePhotoUrl) {
        mLivePhotoUrl = livePhotoUrl;
    }

    public String getLiveOwener() {
        return mLiveOwener;
    }

    public void setLiveOwener(String liveOwener) {
        mLiveOwener = liveOwener;
    }

    public String getLiveIntroduce() {
        return mLiveIntroduce;
    }

    public void setLiveIntroduce(String liveIntroduce) {
        mLiveIntroduce = liveIntroduce;
    }

    public String getLiveDate() {
        return mLiveDate;
    }

    public void setLiveDate(String liveDate) {
        mLiveDate = liveDate;
    }

    public String getLiveOwenerIntroduce() {
        return mLiveOwenerIntroduce;
    }

    public void setLiveOwenerIntroduce(String liveOwenerIntroduce) {
        mLiveOwenerIntroduce = liveOwenerIntroduce;
    }
}
