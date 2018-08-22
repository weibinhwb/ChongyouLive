package com.example.weibin.chongyoulive.base;

/**
 * Created by weibin on 2018/8/23
 */


public class LiveMessageModel {

    private String mText;
    private boolean isSelf;
    private String mDate;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
