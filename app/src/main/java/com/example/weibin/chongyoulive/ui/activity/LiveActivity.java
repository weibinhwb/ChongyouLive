package com.example.weibin.chongyoulive.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.ui.adapter.LiveChatRecyclerAdapter;
import com.example.weibin.chongyoulive.util.audio.Audio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.weibin.chongyoulive.util.LiveChatUtil.*;


public class LiveActivity extends AppCompatActivity implements View.OnClickListener, onRefreshView {

    private boolean isOpen = false;
    private boolean isRecord = false;
    private String mLiveTitle;
    private static final String TAG = "LiveActivity";

    private long startSpeakTime;
    private long stopSpeakTime;
    private Toolbar mLiveToolbar;
    private EditText mLiveEdit;
    private CardView mRecordLayout;
    private Button mSendButton, mAudioButton, mStartRecordButton;
    private RecyclerView mChatRecycler;
    private LiveChatRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;


    private final int mRequestcode = 100;
    private List<String> mPermissions = new ArrayList<>();
    private String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


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
        String liveId = intent.getStringExtra(Base.OPEN_A_LIVE);
        mLiveTitle = intent.getStringExtra(Base.LIVE_TITLE);
        initView();
        getInstance().initLive(liveId);
        getInstance().setAdapter(mAdapter);
        getInstance().setOnRefreshView(this);
        getInstance().initRoamMessage();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mLiveEdit = findViewById(R.id.live_edit);
        mSendButton = findViewById(R.id.message_commit);
        mLiveToolbar = findViewById(R.id.live_toolbar);
        mAudioButton = findViewById(R.id.live_audio_openRecord);
        mChatRecycler = findViewById(R.id.live_recycler);
        mRecordLayout = findViewById(R.id.live_record);
        mRefreshLayout = findViewById(R.id.message_refresh);
        mStartRecordButton = findViewById(R.id.live_start_record);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        mChatRecycler.setLayoutManager(manager);
        mAdapter = new LiveChatRecyclerAdapter();
        mChatRecycler.setAdapter(mAdapter);
        mSendButton.setOnClickListener(this);
        mAudioButton.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(() -> getInstance().initRoamMessage());
        mLiveToolbar.setNavigationOnClickListener(v -> finish());
        mLiveToolbar.setTitle(mLiveTitle);
        mStartRecordButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartRecordButton.setBackground(getDrawable(R.drawable.selected_record_button));
                    startSpeakTime = new Date().getTime();
                    Audio.getInstance().startCapture();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    stopSpeakTime = new Date().getTime();
                    mStartRecordButton.setBackground(getDrawable(R.drawable.unselected_record_button));
                    if ((stopSpeakTime - startSpeakTime) / 1000.0f < 0.6) {
                        Audio.getInstance().stopCapture();
                        Toast.makeText(LiveActivity.this, "录音时间太短", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    getInstance().sendSound(Audio.getInstance().stopCapture(), startSpeakTime, stopSpeakTime);
                    mStartRecordButton.setBackground(getDrawable(R.drawable.unselected_record_button));
                    break;
            }
            return true;
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_commit:
                String text = mLiveEdit.getText().toString();
                if (text.equals("")) {
                    return;
                }
                mLiveEdit.setText("");
                getInstance().sendText(text);
                break;
            case R.id.live_audio_openRecord:
                if (!isOpen) {
                    mRecordLayout.setVisibility(View.VISIBLE);
                    mAudioButton.setBackground(getDrawable(R.drawable.live_close_record_arrow));
                    isOpen = true;
                    mLiveEdit.setEnabled(false);
                } else {
                    mAudioButton.setBackground(getDrawable(R.drawable.live_chat_audio_button));
                    mRecordLayout.setVisibility(View.GONE);
                    isOpen = false;
                    mLiveEdit.setEnabled(true);
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
