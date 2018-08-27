package com.example.weibin.chongyoulive.util;

import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.weibin.chongyoulive.base.LiveApplication;
import com.example.weibin.chongyoulive.base.Message;
import com.example.weibin.chongyoulive.database.UserDatabase;
import com.example.weibin.chongyoulive.database.UserEntity;
import com.example.weibin.chongyoulive.ui.adapter.LiveChatRecyclerAdapter;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMMessageExt;

import java.io.File;
import java.util.Date;
import java.util.List;


/**
 * Created by weibin on 2018/8/23
 */


public class LiveChatUtil {

    private static LiveChatUtil sChatUtil;
    private static final String TAG = "LIVE_CHAT";
    private static final int BEFORE_MSG = 0;
    private static final int CURRENT_MSG = 1;

    private TIMConversation mConversation;
    private TIMMessage mLastMessage;
    private int mLoadMessageCount = 10;
    private LiveChatRecyclerAdapter mAdapter;
    private onRefreshView mOnRefreshView;
    private UserEntity mEntity;

    public void setOnRefreshView(onRefreshView onRefreshView) {
        mOnRefreshView = onRefreshView;
    }

    private LiveChatUtil() {
    }

    public interface onRefreshView {
        void refreshRecycler(int position);

        void refreshSwipeRefresh();
    }

    public static LiveChatUtil getInstance() {
        if (sChatUtil == null) {
            synchronized (LiveChatUtil.class) {
                if (sChatUtil == null) {
                    sChatUtil = new LiveChatUtil();
                }
            }
        }
        return sChatUtil;
    }

    public void setAudioLayoutWidth(RelativeLayout layout, long duration) {

        float perSecondWidth = 4.0f;
        float second = duration / 1000.0f;
        float width = second * perSecondWidth;

        if (width < 240) {
            width = 50 + second * perSecondWidth;
        } else if (width > 240) {
            width = 290.0f;
        }

        int dpWidth = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, LiveApplication.getInstance().getResources().getDisplayMetrics()));

        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.width = dpWidth;
        layout.setLayoutParams(params);
    }

    public void initLive(String mLiveId) {
        mConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, mLiveId);
        mEntity = new UserEntity();
        new Thread(() -> mEntity = UserDatabase.getINSTANCE(LiveApplication.getInstance()).getUserEntityDao().findUser(getLoginUser())).start();
        TIMManager.getInstance().addMessageListener(list -> {
            readMessage(list, CURRENT_MSG);
            return true;
        });
    }

    private String getLoginUser() {
        return TIMManager.getInstance().getLoginUser();
    }

    public void initMessage() {
        TIMConversationExt ext = new TIMConversationExt(mConversation);
        ext.getLocalMessage(10, null, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "错误代码" + i + s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                Log.d(TAG, "onSuccess: " + "获取本地消息");
                readMessage(timMessages, BEFORE_MSG);
                if (timMessages.size() == 0) {
                    return;
                }
                mLastMessage = timMessages.get(timMessages.size() - 1);
            }
        });
    }

    public void initRoamMessage() {
        TIMConversationExt ext = new TIMConversationExt(mConversation);
        ext.getMessage(mLoadMessageCount, mLastMessage, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "错误代码" + i + s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                Log.d(TAG, "onSuccess: " + "获取漫游消息");
                readMessage(timMessages, BEFORE_MSG);
                mOnRefreshView.refreshSwipeRefresh();
                if (timMessages.size() == 0) {
                    return;
                }
                mLastMessage = timMessages.get(timMessages.size() - 1);
            }
        });
    }

    public void pause() {
        mLastMessage = null;
    }

    public void sendText(String text) {

        TIMMessage msg = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(text);
        if (msg.addElement(elem) != 0) {
            Log.d(TAG, "sendText: " + "发送失败");
            Toast.makeText(LiveApplication.getInstance(), "消息发送失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "错误码" + i);
                Log.d(TAG, "onError: " + "消息发送失败" + s);
                Toast.makeText(LiveApplication.getInstance(), "消息发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Log.d(TAG, "onSuccess: " + "消息发送成功");
                Message model = new Message();
                model.setSelf(true);
                model.setText(text);
                model.setType(Message.TEXT);
                model.setSpeakerFace(mEntity.getFaceUrl());
                mAdapter.receiveCurrentMsg(model);
                mOnRefreshView.refreshRecycler(mAdapter.getItemCount() - 1);
            }
        });
    }

    public void sendSound(String filePath, long startSpeakTime, long stopSpeakTime) {
        TIMMessage msg = new TIMMessage();
        TIMSoundElem elem = new TIMSoundElem();
        elem.setPath(filePath);
        elem.setDuration(stopSpeakTime - startSpeakTime);
        Message message = new Message();
        message.setSelf(true);
        message.setDuration(stopSpeakTime - startSpeakTime);
        message.setLocalSoundFile(filePath);
        message.setType(Message.SOUND);
        message.setSpeakerFace(mEntity.getFaceUrl());
        Log.e(TAG, "SendMsg ok" + "语音发送成功");
        mAdapter.receiveCurrentMsg(message);
        mOnRefreshView.refreshRecycler(mAdapter.getItemCount() - 1);
        if (msg.addElement(elem) != 0) {
            Log.d(TAG, "addElement failed");
            return;
        }
        mConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {
                TIMElem elem = msg.getElement(0);
                TIMSoundElem e = (TIMSoundElem) elem;
                saveSound(e);
            }
        });
    }

    private void readMessage(List<TIMMessage> list, int time) {
        for (int i = 0; i < list.size(); i++) {
            TIMMessage message = list.get(i);
            TIMElem elem = message.getElement(0);
            TIMElemType type = elem.getType();
            Message msg = new Message();
            if (type == TIMElemType.Text) {
                TIMTextElem e = (TIMTextElem) elem;
                msg.setText(e.getText());
                msg.setType(Message.TEXT);
            } else if (type == TIMElemType.Sound) {
                TIMMessageExt ext = new TIMMessageExt(message);
                TIMSoundElem e = (TIMSoundElem) elem;
                msg.setType(Message.SOUND);
                msg.setDuration(e.getDuration());
                msg.setSoundUUid(e.getUuid());
                msg.setRead(ext.isRead());
                saveSound(e);
            }
            msg.setSenderName(message.getSender());
            msg.setDate(message.timestamp() + "");
            msg.setSpeakerFace(message.getSenderProfile() != null ? message.getSenderProfile().getFaceUrl() : "");
            if (message.isSelf()) {
                msg.setSpeakerFace(mEntity.getFaceUrl());
            }
            Log.d(TAG, "readMessage: " + msg.getSpeakerFace());
            Log.d(TAG, "readMessage: " + message.getSender());
            msg.setSelf(message.isSelf());
            if (time == CURRENT_MSG) {
                mAdapter.receiveCurrentMsg(msg);
                mOnRefreshView.refreshRecycler(mAdapter.getItemCount() - 1);
            } else if (time == BEFORE_MSG) {
                mAdapter.receiveBeforeMsg(msg);
                mOnRefreshView.refreshRecycler(0);
            }
        }
    }


    private void saveSound(TIMSoundElem e) {
        String fileName = e.getUuid() + ".pcm";
        File file = new File(LiveApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);
        try {
            if (!file.createNewFile()) {
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        e.getSoundToFile(file.getAbsolutePath(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + "语音播放失败" + "错误码" + i + s);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: " + "缓存成功");
            }
        });
    }

    public void setAdapter(LiveChatRecyclerAdapter adapter) {
        mAdapter = adapter;
    }


}
