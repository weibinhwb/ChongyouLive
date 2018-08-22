package com.example.weibin.chongyoulive.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by weibin on 2018/8/21
 */

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase INSTANCE;

    public static UserDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user.db").build();
        }
        return INSTANCE;
    }

    public static void onDestroy() {
        INSTANCE = null;
    }

    public abstract UserDao getUserEntityDao();
}
