package com.example.weibin.chongyoulive.base;

import retrofit2.http.PUT;

/**
 * Created by weibin on 2018/8/23
 */


public class Message {

    private String mText;
    private boolean isSelf;
    private String mDate;
    private int mType;
    public static final int TEXT = 0;
    public static final int SOUND = 1;
    public static final int DOCUMENT = 2;
    private long mDuration;
    private String mSoundUUid;
    private String mSpeakerFace;
    private String mLocalSoundFile;
    private String mSenderName;
    private boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getSenderName() {
        return mSenderName;
    }

    public void setSenderName(String senderName) {
        mSenderName = senderName;
    }

    public String getLocalSoundFile() {
        return mLocalSoundFile;
    }

    public void setLocalSoundFile(String localSoundFile) {
        mLocalSoundFile = localSoundFile;
    }

    public String getSpeakerFace() {
        return mSpeakerFace;
    }

    public void setSpeakerFace(String speakerFace) {
        mSpeakerFace = speakerFace;
    }

    public String getSoundUUid() {
        return mSoundUUid;
    }

    public void setSoundUUid(String soundUUid) {
        mSoundUUid = soundUUid;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

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
