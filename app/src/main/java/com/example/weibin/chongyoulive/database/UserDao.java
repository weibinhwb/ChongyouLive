package com.example.weibin.chongyoulive.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by weibin on 2018/8/21
 */

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserEntity userEntity);

    @Delete
    void deleteUser(UserEntity userEntity);

    @Query("SELECT * FROM  user")
    List<UserEntity> queryUser();

    @Query("UPDATE user SET mNickName =:nickName, mFaceUrl =:faceUrl WHERE id =:id")
    void updateUser(String nickName, String faceUrl, int id);

    @Query("SELECT * FROM user WHERE userId = :userId")
    UserEntity findUser(String userId);
}
