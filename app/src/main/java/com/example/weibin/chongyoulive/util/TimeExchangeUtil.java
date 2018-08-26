package com.example.weibin.chongyoulive.util;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by weibin on 2018/8/26
 */


public class TimeExchangeUtil {

    public static Integer String2Timestamp(String time) {
        return (int) ((Timestamp.valueOf(time).getTime()) / 1000);
    }

    public static String Timestamp2String(long time){
        SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return sdf.format(time * 1000);
    }
}
