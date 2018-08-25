package com.example.weibin.chongyoulive.ui.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.ui.activity.AboutMeDetailActivity;
import com.example.weibin.chongyoulive.ui.adapter.AboutMyDetailAdapter;
import com.example.weibin.chongyoulive.util.photo.MyImageResizer;
import com.example.weibin.chongyoulive.util.photo.UploadPhoto;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendGenderType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.protocol.msg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by weibin on 2018/8/21
 */


public class AboutMeDetailFragment extends Fragment implements View.OnClickListener, AboutMyDetailAdapter.onItemClick {

    private ImageView mDetailPhotoView;
    private RecyclerView mAboutMeRecycler;
    private AboutMyDetailAdapter mAdapter;
    private CollapsingToolbarLayout mCollaps_Toolbar;
    private FloatingActionButton mFb;

    private Toolbar mDetailToolbar;
    private AboutMeDetailActivity mActivity;

    private List<String> mSelfList;
    private final static String TAG = "AboutMeDetailFragment";
    private String mModifyPhotoPath;
    private String mUserSelectedGender = "";
    private final static int SELECTED_PHOTO = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AboutMeDetailActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me_detail, container, false);
        mFb = view.findViewById(R.id.detail_fb);
        mFb.setOnClickListener(this);
        mDetailToolbar = view.findViewById(R.id.detail_toolbar);
        mDetailToolbar.setNavigationIcon(R.drawable.back_icon);
        mActivity.setSupportActionBar(mDetailToolbar);
        mDetailToolbar.setNavigationOnClickListener(v -> mActivity.finish());
        mDetailPhotoView = view.findViewById(R.id.detail_photo);
        mAboutMeRecycler = view.findViewById(R.id.detail_recycler);
        mCollaps_Toolbar = view.findViewById(R.id.detail_collapsing_toolbar);
        mAboutMeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AboutMyDetailAdapter();
        mAboutMeRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClick(this);
        getMyDetailInfo();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            notifyAdapter();
        }
        Log.d(TAG, "onHiddenChanged: " + "重新启动" + hidden);
    }

    private void getMyDetailInfo() {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "获取个人资料出错");
            }

            @Override
            public void onSuccess(TIMUserProfile t) {
                mSelfList = new ArrayList<>();
                mSelfList.add(t.getIdentifier());
                mSelfList.add(t.getNickName());
                mSelfList.add(t.getSelfSignature());
                mSelfList.add(t.getGender().name());
                mSelfList.add(t.getBirthday() + "");
                mSelfList.add(t.getLocation());
                mSelfList.add(t.getLanguage() + "");
                mAdapter.setItem2List(mSelfList);
                mAdapter.notifyDataSetChanged();
                Glide.with(mDetailPhotoView.getContext()).load(t.getFaceUrl()).into(mDetailPhotoView);
                mCollaps_Toolbar.setTitle(t.getNickName());
                Log.d(TAG, "onSuccess: " + "获取个人资料成功");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_fb:
                requestPermission();
                break;
        }
    }

    private void updateMyFace() {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        if (mModifyPhotoPath != null && !mModifyPhotoPath.equals("")) {
            String key = new Date().getTime() + "";
            param.setFaceUrl(Base.UPLOAD_PHOTO_BASE_URL + key);
            UploadPhoto.getUploadInstance.instance().uploadImage2QiNiu(MyImageResizer.bitmap2Byte(mModifyPhotoPath, 400, 300), key);
        }
        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + i + s);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: " + "更新图片成功");
                Toast.makeText(mActivity, "更新图片成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notifyAdapter() {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s + "获取个人资料出错");
            }

            @Override
            public void onSuccess(TIMUserProfile t) {
                mSelfList = new ArrayList<>();
                mSelfList.add(t.getIdentifier());
                mSelfList.add(t.getNickName());
                mSelfList.add(t.getSelfSignature());
                mSelfList.add(t.getGender().name());
                mSelfList.add(t.getBirthday() + "");
                mSelfList.add(t.getLocation());
                mSelfList.add(t.getLanguage() + "");
                mAdapter.setItem2List(mSelfList);
                mAdapter.notifyDataSetChanged();
                mCollaps_Toolbar.setTitle(t.getNickName());
                Log.d(TAG, "onSuccess: " + "获取个人资料成功");
            }
        });
    }

    private void updateMyGender(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextView tv = view.findViewById(R.id.detail_item_text2);
        int checkedItem;
        if (tv.getText().toString().equals("女")) {
            checkedItem = 1;
        } else if (tv.getText().toString().equals("男")) {
            checkedItem = 0;
        } else {
            checkedItem = 2;
        }
        final String[] gender = {"男", "女", "保密"};
        builder.setSingleChoiceItems(gender, checkedItem, (dialog, which) -> {
            mUserSelectedGender = gender[which];
        });
        builder.setPositiveButton("确定", (dialog, which) -> {
            if (which == -1) {
                dialog.dismiss();
            }
            TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
            switch (mUserSelectedGender) {
                case "女":
                    param.setGender(TIMFriendGenderType.Female);
                    break;
                case "男":
                    param.setGender(TIMFriendGenderType.Male);
                    break;
                case "保密":
                    param.setGender(TIMFriendGenderType.Unknow);
                    break;
            }
            TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    Log.e(TAG, "modifyProfile failed: " + code + " desc" + desc);
                }

                @Override
                public void onSuccess() {
                    tv.setText(mUserSelectedGender);
                    Log.d(TAG, "onSuccess: " + "获取个人资料成功");

                }
            });
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void updateMyBirthday(View v, int position) {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            updateMyBirthday(year, monthOfYear, dayOfMonth, v, position);
        };
        new DatePickerDialog(getContext(), onDateSetListener, mYear, mMonth, mDay).show();

    }

    private void updateMyBirthday(int yy, int mm, int dd, View v, int position) {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        //生日设置为 2017/5/2
        String time = yy + "-" + mm + "-" + dd + " 00:00:00";
        int birthday = StringToTimestamp(time);
        Toast.makeText(getContext(), birthday + "", Toast.LENGTH_SHORT).show();
        param.setBirthday(birthday);

        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "modifyProfile succ");
                TextView tv = (TextView) v.findViewById(R.id.detail_item_text2);
                String time = yy + "年" + mm + "月" + dd + "日";
                tv.setText(time);
            }
        });
    }

    private void updateMyLocation() {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setLocation("location");

        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e(TAG, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "modifyProfile succ");
            }
        });
    }

    private void updateMyLanguage() {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        //这里假设 1 表示中文
        param.setLanguage(1);

        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e(TAG, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "modifyProfile succ");
            }
        });
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
                if (resultCode == RESULT_OK) {
                    Uri selectedPhoto = null;
                    if (data != null) {
                        selectedPhoto = data.getData();
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    if (selectedPhoto != null) {
                        try (Cursor cursor = getContext().getContentResolver().query(selectedPhoto, filePathColumn, null, null, null)) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            mModifyPhotoPath = cursor.getString(columnIndex);
                            Glide.with(getContext()).load(MyImageResizer.decodeSampledBitmapFromFile(mModifyPhotoPath, mDetailPhotoView.getWidth(), mDetailPhotoView.getHeight())).into(mDetailPhotoView);
                            cursor.close();
                        }
                        updateMyFace();
                    }
                }
                break;
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                }
                break;
            default:
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
            case 2:
                TextView t1 = (TextView) view.findViewById(R.id.detail_item_text1);
                TextView t2 = (TextView) view.findViewById(R.id.detail_item_text2);
                Fragment fragment = new ModifyDataFragment();
                Bundle bundle = new Bundle();
                bundle.putString("param1", String.valueOf(t1.getText()));
                bundle.putString("param2", String.valueOf(t2.getText()));
                fragment.setArguments(bundle);
                mActivity.showFragment(fragment, this);
                break;
            case 3:
                updateMyGender(view, position);
                break;
            case 4:
                updateMyBirthday(view, position);
                break;
            case 5:
                break;
            case 6:
                break;
            default:
        }
    }

    public static Integer StringToTimestamp(String time) {

        int times = 0;
        try {
            times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (times == 0) {
            System.out.println("String转10位时间戳失败");
        }
        return times;

    }
}
