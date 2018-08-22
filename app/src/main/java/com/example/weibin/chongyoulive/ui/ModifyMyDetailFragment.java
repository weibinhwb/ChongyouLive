package com.example.weibin.chongyoulive.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.util.MyImageResizer;
import com.example.weibin.chongyoulive.util.UploadPhoto;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by weibin on 2018/8/21
 */


public class ModifyMyDetailFragment extends Fragment implements View.OnClickListener{

    private CircleImageView mModifyPhoto;
    private EditText mModifyName, mModifySlogan;
    private String mModifyPhotoPath;
    private final static int SELECTED_PHOTO = 1;
    private static final String TAG = "ModifyMyDetailFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_mydetail, container, false);
        mModifyName = view.findViewById(R.id.modify_name);
        mModifySlogan = view.findViewById(R.id.modify_slogan);
        mModifyPhoto = view.findViewById(R.id.modify_photo);
        mModifyPhoto.setOnClickListener(this);
        getMyDetailInfo();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        modifyMyDetail();
    }

    private void getMyDetailInfo() {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "获取个人资料出错");
            }

            @Override
            public void onSuccess(TIMUserProfile timUserProfile) {
                Log.d(TAG, "onSuccess: " + "获取个人资料成功");
                mModifyName.setText(timUserProfile.getNickName());
                mModifySlogan.setText(timUserProfile.getSelfSignature());
                Glide.with(mModifyPhoto.getContext()).load(timUserProfile.getFaceUrl()).into(mModifyPhoto);
            }
        });
    }

    private void modifyMyDetail() {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        String nickName = mModifyName.getText().toString();
        String slogan = mModifySlogan.getText().toString();

        if (mModifyPhotoPath != null && !mModifyPhotoPath.equals("")) {
            String key = new Date().getTime() + "";
            param.setFaceUrl(Base.UPLOAD_PHOTO_BASE_URL + key);
            UploadPhoto.getUploadInstance.instance()
                    .uploadImage2QiNiu(MyImageResizer.bitmap2Byte(mModifyPhotoPath, 400, 300), key);
        }

        param.setNickname(nickName);
        param.setSelfSignature(slogan);

        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "修改信息失败");
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: " + "修改信息成功");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_photo:
                requestPermission();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PHOTO:
                if (resultCode == RESULT_OK){
                    Uri selectedPhoto = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContext().getContentResolver().query(selectedPhoto,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mModifyPhotoPath = cursor.getString(columnIndex);
                    Glide.with(getContext()).load(MyImageResizer
                            .decodeSampledBitmapFromFile(mModifyPhotoPath, mModifyPhoto.getWidth(), mModifyPhoto.getHeight())).into(mModifyPhoto);
                    cursor.close();
                }
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                    Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
