package com.example.weibin.chongyoulive.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by weibin on 2018/8/21
 */

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String userPassword;
    private String mNickName;
    private String mFaceUrl;
    private String mSlogan;

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getFaceUrl() {
        return mFaceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        mFaceUrl = faceUrl;
    }

    public String getSlogan() {
        return mSlogan;
    }

    public void setSlogan(String slogan) {
        mSlogan = slogan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
