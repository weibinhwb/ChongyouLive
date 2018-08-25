package com.example.weibin.chongyoulive.base;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by weibin on 2018/8/25
 */


public class LiveData implements Serializable {

    private String mOwnerName;
    private String mLiveFace;
    private String mLiveName;
    private String mLiveJoinPerson;
    private String mLiveIntroduce;
    private int mLiveDate;
    private String mLiveOwenerIntroduce;
    private String mLiveId;

    public String getLiveIntroduce() {
        return mLiveIntroduce;
    }

    public void setLiveIntroduce(String liveIntroduce) {
        mLiveIntroduce = liveIntroduce;
    }

    public int getLiveDate() {
        return mLiveDate;
    }

    public void setLiveDate(int liveDate) {
        mLiveDate = liveDate;
    }

    public String getLiveOwenerIntroduce() {
        return mLiveOwenerIntroduce;
    }

    public void setLiveOwenerIntroduce(String liveOwenerIntroduce) {
        mLiveOwenerIntroduce = liveOwenerIntroduce;
    }

    public String getLiveId() {
        return mLiveId;
    }

    public void setLiveId(String liveId) {
        mLiveId = liveId;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getLiveFace() {
        return mLiveFace;
    }

    public void setLiveFace(String liveFace) {
        mLiveFace = liveFace;
    }

    public String getLiveName() {
        return mLiveName;
    }

    public void setLiveName(String liveName) {
        mLiveName = liveName;
    }

    public String getLiveJoinPerson() {
        return mLiveJoinPerson;
    }

    public void setLiveJoinPerson(String liveJoinPerson) {
        mLiveJoinPerson = liveJoinPerson;
    }
}
