package com.example.weibin.chongyoulive.ui.activity;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;
import com.example.weibin.chongyoulive.util.photo.MyImageResizer;
import com.example.weibin.chongyoulive.util.photo.UploadPhoto;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMGroupAddOpt;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.weibin.chongyoulive.base.Base.LIVE_INTRO;
import static com.example.weibin.chongyoulive.base.Base.LIVE_OUTLINE;
import static com.example.weibin.chongyoulive.base.Base.LIVE_OWNER;
import static com.example.weibin.chongyoulive.base.Base.LIVE_OWNER_INTRODUCE;
import static com.example.weibin.chongyoulive.base.Base.LIVE_TIME;

public class AddLiveActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mLiveTitleEdit, mLiveIntroduceEdit, mOwnerIntroductionEdit, mLiveOutLineEdit;
    private ImageView mLivePhotoView, mLiveDone;
    private TextView mLiveTimeView;
    private Toolbar mLiveToolbar;

    private String mLiveTitle, mLiveIntroduction, mLiveOwnerName, mLiveOutLine, mLivePhotoPath, mLiveOwnerIntroduce, mLiveTime;

    private final static String TAG = "AddFragment";
    private final static String TYPE = "Public";
    private final static int SELECTED_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_live);
        mLiveIntroduceEdit = findViewById(R.id.live_introduction);
        mLiveTitleEdit = findViewById(R.id.live_title);
        mLiveOutLineEdit = findViewById(R.id.live_out_line);
        mLiveTimeView = findViewById(R.id.live_time);
        mLivePhotoView = findViewById(R.id.live_photo);
        mOwnerIntroductionEdit = findViewById(R.id.live_owner_introduction);
        mLiveToolbar = findViewById(R.id.live_toolbar);
        mLivePhotoView.setOnClickListener(this);
        mLiveToolbar.setNavigationOnClickListener(v -> finish());
        mLiveTimeView.setOnClickListener(this);
        mLiveDone = findViewById(R.id.live_commit_done);
        mLiveDone.setOnClickListener(this);
    }

    private void addLive() {
        mLiveTitle = mLiveTitleEdit.getText().toString();
        mLiveIntroduction = mLiveIntroduceEdit.getText().toString();
        mLiveOwnerIntroduce = mOwnerIntroductionEdit.getText().toString();
        mLiveOutLine = mLiveOutLineEdit.getText().toString();
        if (mLivePhotoPath == null || mLivePhotoPath.equals("")){
            Toast.makeText(this, "Live照片添加失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mLiveTitle == null || mLiveTitle.equals("")) {
            Toast.makeText(this, "Live标题添加失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mLiveTime == null || mLiveTime.equals("")) {
            Toast.makeText(this, "Live时间选择失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mLiveIntroduction == null || mLiveIntroduction.equals("")) {
            Toast.makeText(this, "Live简介添加失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mLiveOwnerIntroduce == null || mLiveOwnerIntroduce.equals("")) {
            Toast.makeText(this, "Live主讲人简介添加失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mLiveOutLine == null || mLiveOutLine.equals("")){
            Toast.makeText(this, "Live大纲添加失败", Toast.LENGTH_SHORT).show();
            return;
        }
        String key = new Date().getTime() + "";
        UploadPhoto.getUploadInstance.instance().uploadImage2QiNiu(MyImageResizer.bitmap2Byte(mLivePhotoPath, 400, 300), key);
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            UserEntity userEntity = UserDatabase.getINSTANCE(AddLiveActivity.this).getUserEntityDao().findUser(TIMManager.getInstance().getLoginUser());
            emitter.onNext(userEntity.getNickName());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> addLive(Base.UPLOAD_PHOTO_BASE_URL + key, s)).isDisposed();
    }

    private void addLive(String faceUrl, String operatorName) {
        TIMGroupManager.CreateGroupParam param = new TIMGroupManager.CreateGroupParam(TYPE, mLiveTitle);
//        图片
        param.setFaceUrl(faceUrl);
//        live主讲人名字
        param.setCustomInfo(LIVE_OWNER, operatorName.getBytes());
//        live简介
        param.setCustomInfo(LIVE_INTRO, mLiveIntroduction.getBytes());
//        live大纲
        param.setCustomInfo(LIVE_OUTLINE, mLiveOutLine.getBytes());
//        live主讲人简介
        param.setCustomInfo(LIVE_OWNER_INTRODUCE, mLiveOwnerIntroduce.getBytes());
//        live时间
        param.setCustomInfo(LIVE_TIME, mLiveTime.getBytes());
        TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "onSuccess: " + s);
                modifyLivePermission(s);
                Toast.makeText(AddLiveActivity.this, "创建Live成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selecteTime() {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            mLiveTime = year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
            mLiveTimeView.setText(mLiveTime);
        };
        new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
    }

    private void modifyLivePermission(String liveId) {
        TIMGroupManagerExt.ModifyGroupInfoParam param = new TIMGroupManagerExt.ModifyGroupInfoParam(liveId);
        param.setAddOption(TIMGroupAddOpt.TIM_GROUP_ADD_ANY);
        TIMGroupManagerExt.getInstance().modifyGroupInfo(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "modify group info failed, code:" + code + "|desc:" + desc);
            }

            @Override
            public void onSuccess() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_commit_done:
                addLive();
                break;
            case R.id.live_photo:
                requestPermission();
                break;
            case R.id.live_time:
                selecteTime();
                break;
        }
    }

    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECTED_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedPhoto = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedPhoto, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mLivePhotoPath = cursor.getString(columnIndex);
                    Glide.with(AddLiveActivity.this).load(MyImageResizer.decodeSampledBitmapFromFile(mLivePhotoPath, mLivePhotoView.getWidth(), mLivePhotoView.getHeight())).into(mLivePhotoView);
                    cursor.close();
                }
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(AddLiveActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddLiveActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
