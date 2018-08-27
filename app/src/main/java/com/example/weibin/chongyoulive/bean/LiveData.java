package com.example.weibin.chongyoulive.bean;

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
    private String mLiveOwnerIntroduce;
    private String mLiveId;
    private String mLiveTime;
    private String mLiveOutLine;

    public String getLiveTime() {
        return mLiveTime;
    }

    public void setLiveTime(String liveTime) {
        mLiveTime = liveTime;
    }

    public String getLiveOutLine() {

        return mLiveOutLine;
    }

    public void setLiveOutLine(String liveOutLine) {
        mLiveOutLine = liveOutLine;
    }

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

    public String getLiveOwnerIntroduce() {
        return mLiveOwnerIntroduce;
    }

    public void setLiveOwnerIntroduce(String liveOwnerIntroduce) {
        mLiveOwnerIntroduce = liveOwnerIntroduce;
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
