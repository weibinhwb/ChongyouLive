package com.example.weibin.chongyoulive.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.DetailLiveBean;
import com.example.weibin.chongyoulive.base.LiveBean;
import com.example.weibin.chongyoulive.base.LiveDetailModel;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMGroupManager;

public class LiveDetailActivity extends AppCompatActivity {


    private LiveDetailModel mLiveBean;
    private TextView mLiveName, mLiveDate, mLiveJoinPerson, mLiveIntroduce, mLiveOwenerIntroduce;
    private ImageView mOwenerPhoto;
    private Button mBuyButton;
    public static final String OPEN_A_LIVE = "open_a_live";
    private static final String TAG = "LiveDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        Intent intent = getIntent();
        mLiveBean = (LiveDetailModel) intent.getSerializableExtra(HomeLiveAdapter.SHOW_DETAIL);
        mLiveDate = findViewById(R.id.live_time);
        mLiveName = findViewById(R.id.live_name);
        mLiveJoinPerson = findViewById(R.id.live_join_person);
        mLiveIntroduce = findViewById(R.id.live_introduce);
        mLiveOwenerIntroduce = findViewById(R.id.live_owener_introduce);
        mBuyButton = findViewById(R.id.live_buy);
        initData();
    }

    private void initData() {
//        mLiveOwenerIntroduce.setText(mLiveBean.getLiveIntroduce());
        mLiveIntroduce.setText(mLiveBean.getLiveIntroduce());
        mLiveJoinPerson.setText(mLiveBean.getLiveJoinPerson());
        mLiveDate.setText(mLiveBean.getLiveDate());
        mLiveName.setText(mLiveBean.getLiveName());
        mBuyButton.setOnClickListener(v -> {
            TIMGroupManager.getInstance().applyJoinGroup(mLiveBean.getLiveId(), "", new TIMCallBack() {
                @Override
                public void onError(int i, String s) {
                    Log.d(TAG, "onError: " + "加入失败" + s + "错误码" + i);
                    if (i == 10013) {
                        Intent intent = new Intent(LiveDetailActivity.this, LiveActivity.class);
                        intent.putExtra(OPEN_A_LIVE, mLiveBean.getLiveId());
                        startActivity(intent);
                    }
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: " + "加入成功");
                    Intent intent = new Intent(LiveDetailActivity.this, LiveActivity.class);
                    intent.putExtra(OPEN_A_LIVE, mLiveBean.getLiveId());
                    startActivity(intent);
                }
            });
        });
    }
}
