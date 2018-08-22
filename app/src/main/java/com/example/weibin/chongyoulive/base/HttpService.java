package com.example.weibin.chongyoulive.base;



import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.example.weibin.chongyoulive.base.Base.DETAIL_LIVE;

public interface HttpService {

    @GET("get_appid_group_list")
    Observable<LiveBean> getAllLive(@QueryMap Map<String, String> map);

    @POST("get_group_info")
    Observable<DetailLiveBean> getDetailLives(@QueryMap Map<String, String> map, @Body RequestBody requestBody);
}

