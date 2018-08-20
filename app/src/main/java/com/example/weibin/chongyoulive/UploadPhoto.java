package com.example.weibin.chongyoulive;


import android.util.Log;

import com.example.weibin.chongyoulive.qinniuUtils.Auth;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;

import static com.example.weibin.chongyoulive.base.Base.AccessKey;
import static com.example.weibin.chongyoulive.base.Base.SecreKey;


public class UploadPhoto {

    private static final String TAG = "UploadPhoto";

    private UploadPhoto() {
    }

    public static class getUploadInstance{
        public static UploadPhoto instance(){
            return new UploadPhoto();
        }
    }

    public void uploadImage2QiNiu(String path ,String key) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, key, Auth.create(AccessKey, SecreKey).uploadToken("chongyou-live"), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    Log.d(TAG, "complete: " + Auth.create(AccessKey, SecreKey));
                    Log.d(TAG, "complete: " + key);
                    Log.d(TAG, "complete: " + info.path);
                    Log.d(TAG, "complete: " + response.toString());
                }
            }
        }, null);
    }
}
