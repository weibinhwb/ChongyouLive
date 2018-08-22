package com.example.weibin.chongyoulive.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.LiveMessageModel;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

public class LiveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LiveActivity";
    private EditText mLiveEdit;
    private String mLiveId;
    private Button mButton;
    private RecyclerView mChatRecycler;
    private LiveChatRecyclerAdapter mAdapter;
    private TIMConversation mConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        mLiveEdit = findViewById(R.id.live_edit);
        mButton = findViewById(R.id.message_commit);
        mChatRecycler = findViewById(R.id.live_recycler);
        mChatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LiveChatRecyclerAdapter();
        mChatRecycler.setAdapter(mAdapter);
        mButton.setOnClickListener(this);
        Intent intent = getIntent();
        mLiveId = intent.getStringExtra(LiveDetailActivity.OPEN_A_LIVE);
        initLive();
    }

    private void initLive() {
        mConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, mLiveId);
        TIMManager.getInstance().addMessageListener(list -> {
            for (int i = 0; i < list.size(); i++) {
                TIMMessage message = list.get(i);
                TIMElem elem = message.getElement(i);
                TIMElemType elemType = elem.getType();
                if (elemType == TIMElemType.Text) {
                    TIMTextElem e = (TIMTextElem) elem;
                    LiveMessageModel model = new LiveMessageModel();
                    model.setSelf(message.isSelf());
                    model.setText(e.getText());
                    model.setDate(message.timestamp() + "");
                    Toast.makeText(getApplicationContext(), e.getText(), Toast.LENGTH_SHORT).show();
                    mAdapter.receiveMsg(model);
                    mChatRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            }
            return false;
        });
    }

    private void sendText() {
        String text = mLiveEdit.getText().toString();
        mLiveEdit.setText("");
        LiveMessageModel model = new LiveMessageModel();
        model.setSelf(true);
        model.setText(text);
        TIMMessage msg = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(text);
        if (msg.addElement(elem) != 0) {
            Log.d(TAG, "sendText: " + "发送失败");
            return;
        }
        mConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "错误码" + i);
                Log.d(TAG, "onError: " + "消息发送失败" + s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Log.d(TAG, "onSuccess: " + "消息发送成功");
                mAdapter.receiveMsg(model);
                mChatRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_commit:
                sendText();
                break;
        }
    }
}
