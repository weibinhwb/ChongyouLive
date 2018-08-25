package com.example.weibin.chongyoulive.base;

/**
 * Created by weibin on 2018/8/25
 */


public class SelfInfoBean {

    private String mIdentify;
    private String mNickName;
    private String mSelfSignature;
    private String mGender;
    private long mBirthday;
    private String mLocation;
    private long mLanguage;

    public String getIdentify() {
        return mIdentify;
    }

    public void setIdentify(String identify) {
        mIdentify = identify;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getSelfSignature() {
        return mSelfSignature;
    }

    public void setSelfSignature(String selfSignature) {
        mSelfSignature = selfSignature;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public long getBirthday() {
        return mBirthday;
    }

    public void setBirthday(long birthday) {
        mBirthday = birthday;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public long getLanguage() {
        return mLanguage;
    }

    public void setLanguage(long language) {
        mLanguage = language;
    }
}
