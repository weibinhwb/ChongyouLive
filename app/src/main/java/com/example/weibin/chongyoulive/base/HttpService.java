package com.example.weibin.chongyoulive.base;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpService {

    @GET("get_appid_group_list")
    Observable<LiveBean> getAllLive(@Query("usersig")String usersig, @Query("identifier")String identifier,
                                    @Query("sdkappid")int sdkappid, @Query("contenttype") String contenttype);
}
