package com.example.weibin.chongyoulive.util.photo;


import android.util.Log;

import com.example.weibin.chongyoulive.util.qiniu.Auth;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

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

    public void uploadImage2QiNiu(byte[] data, String key) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(data, key, Auth.create(AccessKey, SecreKey).uploadToken("chongyou-live"), new UpCompletionHandler() {
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
