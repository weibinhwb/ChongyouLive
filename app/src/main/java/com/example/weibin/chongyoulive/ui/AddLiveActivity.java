package com.example.weibin.chongyoulive.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.util.UploadPhoto;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.util.MyImageResizer;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.Date;

public class AddLiveActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mLiveNameEdit, mLiveDescribeEdit;
    private ImageView mLivePhotoView;
    private Button mLiveCommit;
    private final static String TYPE = "Public";
    private final static int SELECTED_PHOTO = 1;
    private String mLiveName;
    private String mLiveDescribe;
    private String mLivePhotoPath;
    private boolean isSelectedPhoto = false;
    private final static String TAG = "AddFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_live);

        mLiveCommit = findViewById(R.id.add_live_commit);
        mLiveDescribeEdit = findViewById(R.id.add_live_content);
        mLiveNameEdit = findViewById(R.id.add_live_name);
        mLivePhotoView = findViewById(R.id.add_live_photo);
        mLiveCommit.setOnClickListener(this);
        mLivePhotoView.setOnClickListener(this);
    }

    private void addLive(String faceUrl) {
        TIMGroupManager.CreateGroupParam param = new TIMGroupManager.CreateGroupParam(TYPE, mLiveName);
        param.setIntroduction(mLiveDescribe);
        //上传群图片
        param.setFaceUrl(faceUrl);
        //可以自定义
        TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "onSuccess: " + s);
                Toast.makeText(AddLiveActivity.this, "创建Live成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_live_commit:
                if (isSelectedPhoto) {
                    mLiveName = mLiveNameEdit.getText().toString();
                    mLiveDescribe = mLiveDescribeEdit.getText().toString();
                    String key = new Date().getTime() + "";
                    addLive(Base.UPLOAD_PHOTO_BASE_URL + key);
                    UploadPhoto.getUploadInstance.instance().
                            uploadImage2QiNiu(MyImageResizer.
                                    decodeSampledBitmapFromFile(mLivePhotoPath, 400, 300), key);
                }
                break;
            case R.id.add_live_photo:
                requestPermission();
                break;
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECTED_PHOTO);
        isSelectedPhoto = true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PHOTO:
                if (resultCode == RESULT_OK){
                    Uri selectedPhoto = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor =getContentResolver().query(selectedPhoto,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mLivePhotoPath = cursor.getString(columnIndex);
                    cursor.close();
                }
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(AddLiveActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddLiveActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            pickPhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
