package com.example.weibin.chongyoulive.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
}
