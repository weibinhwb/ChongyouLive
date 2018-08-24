package com.example.weibin.chongyoulive.base;



import com.example.weibin.chongyoulive.bean.DetailLiveBean;
import com.example.weibin.chongyoulive.bean.LiveBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface HttpService {

    @GET("get_appid_group_list")
    Observable<LiveBean> getAllLive(@QueryMap Map<String, String> map);

    @POST("get_group_info")
    Observable<DetailLiveBean> getDetailLives(@QueryMap Map<String, String> map, @Body RequestBody requestBody);
}

