package com.example.weibin.chongyoulive.ui.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.Base;
import com.example.weibin.chongyoulive.base.LiveData;
import com.example.weibin.chongyoulive.ui.adapter.HomeLiveAdapter;
import com.example.weibin.chongyoulive.util.TimeExchangeUtil;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMGroupManager;

import static com.example.weibin.chongyoulive.base.Base.LIVE_TITLE;
import static com.example.weibin.chongyoulive.base.Base.OPEN_A_LIVE;

public class LiveDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mLiveFace;
    private TextView mLiveTime, mLiveIntroduction, mLiveOwnerIntroduction, mLiveOutLine;
    private FloatingActionButton mFb;

    private LiveData mLiveData;
    private static final String TAG = "LiveDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        Intent intent = getIntent();
        mLiveData = (LiveData) intent.getSerializableExtra(Base.SHOW_DETAIL);
        mToolbar = findViewById(R.id.live_detail_toolbar);
        mCollapsingToolbar = findViewById(R.id.live_detail_collapsing_toolbar);
        mLiveTime = findViewById(R.id.live_detail_time);
        mLiveFace = findViewById(R.id.live_detail_photo);
        mLiveIntroduction = findViewById(R.id.live_introduction);
        mLiveOwnerIntroduction = findViewById(R.id.live_owner_introduction);
        mFb = findViewById(R.id.live_buy_button);
        mLiveOutLine = findViewById(R.id.live_out_line);
        setFunc();
    }

    private void setFunc(){
        setSupportActionBar(mToolbar);
        mCollapsingToolbar.setTitle(mLiveData.getLiveName());
        mToolbar.setNavigationOnClickListener(v -> finish());
        Glide.with(this).load(mLiveData.getLiveFace()).into(mLiveFace);
        mLiveTime.setText(mLiveData.getLiveTime());
        mLiveIntroduction.setText(mLiveData.getLiveIntroduce());
        mLiveOwnerIntroduction.setText(mLiveData.getLiveOwnerIntroduce());
        mLiveOutLine.setText(mLiveData.getLiveOutLine());

        mFb.setOnClickListener(v -> TIMGroupManager.getInstance().applyJoinGroup(mLiveData.getLiveId(), "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "加入失败" + s + "错误码" + i);
                if (i == 10013) {
                    Intent intent = new Intent(LiveDetailActivity.this, LiveActivity.class);
                    intent.putExtra(OPEN_A_LIVE, mLiveData.getLiveId());
                    intent.putExtra(LIVE_TITLE, mLiveData.getLiveName());
                    startActivity(intent);
                }
            }
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: " + "加入成功");
                Intent intent = new Intent(LiveDetailActivity.this, LiveActivity.class);
                intent.putExtra(OPEN_A_LIVE, mLiveData.getLiveId());
                intent.putExtra(LIVE_TITLE, mLiveData.getLiveName());
                startActivity(intent);
            }
        }));
    }
}
