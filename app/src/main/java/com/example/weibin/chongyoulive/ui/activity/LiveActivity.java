package com.example.weibin.chongyoulive.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.adapter.LiveChatRecyclerAdapter;
import com.example.weibin.chongyoulive.util.LiveChatUtil;
import com.example.weibin.chongyoulive.util.audio.Audio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.weibin.chongyoulive.util.LiveChatUtil.*;


public class LiveActivity extends AppCompatActivity implements View.OnClickListener, onRefreshView {

    private boolean isClick = false;
    private static final String TAG = "LiveActivity";

    private long startSpeakTime;
    private long stopSpeakTime;
    private EditText mLiveEdit;
    private Button mSendButton, mAudioButton;
    private RecyclerView mChatRecycler;
    private LiveChatRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;


    private final int mRequestcode = 100;
    private List<String> mPermissions = new ArrayList<>();
    private String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onPause() {
        super.onPause();
        getInstance().pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initPermission();
        Intent intent = getIntent();
        String liveId = intent.getStringExtra(LiveDetailActivity.OPEN_A_LIVE);
        initView();
        getInstance().initLive(liveId);
        getInstance().setAdapter(mAdapter);
        getInstance().setOnRefreshView(this);
//        getInstance().initMessage();
        getInstance().initRoamMessage();
    }

    private void initView() {
        mLiveEdit = findViewById(R.id.live_edit);
        mSendButton = findViewById(R.id.message_commit);
        mAudioButton = findViewById(R.id.live_audio);
        mChatRecycler = findViewById(R.id.live_recycler);
        mRefreshLayout = findViewById(R.id.message_refresh);
        mChatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LiveChatRecyclerAdapter();
        mChatRecycler.setAdapter(mAdapter);
        mSendButton.setOnClickListener(this);
        mAudioButton.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(() -> getInstance().initRoamMessage());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_commit:
                String text = mLiveEdit.getText().toString();
                mLiveEdit.setText("");
                getInstance().sendText(text);
                break;
            case R.id.live_audio:
                if (!isClick) {
                    startSpeakTime = new Date().getTime();
                    Audio.getInstance().startCapture();
                    isClick = true;
                } else {
                    stopSpeakTime = new Date().getTime();
                    getInstance().sendSound(Audio.getInstance().stopCapture(), startSpeakTime, stopSpeakTime);
                    isClick = false;
                }
                break;
        }
    }

    private void initPermission() {
        mPermissions.clear();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissions.add(permission);
            }
        }
        if (mPermissions.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, mRequestcode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        switch (requestCode) {
            case mRequestcode:
                for (int grantResult : grantResults) {
                    if (grantResult == -1) {
                        hasPermissionDismiss = true;
                    }
                }
                if (hasPermissionDismiss) {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void refreshRecycler(int position) {
        mChatRecycler.scrollToPosition(position);
    }

    @Override
    public void refreshSwipeRefresh() {
        mRefreshLayout.setRefreshing(false);
    }
}