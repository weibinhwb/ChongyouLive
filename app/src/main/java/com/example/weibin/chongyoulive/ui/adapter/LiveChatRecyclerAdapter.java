package com.example.weibin.chongyoulive.ui.adapter;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.bean.Message;
import com.example.weibin.chongyoulive.util.LiveChatUtil;
import com.example.weibin.chongyoulive.util.audio.Audio;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weibin on 2018/8/23
 */


public class LiveChatRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int LEFT_TEXT = 0;
    private static final int RIGHT_TEXT = 1;
    private static final int LEFT_SOUND = 2;
    private static final int RIGHT_SOUND = 3;
    private static final String TAG = "LiveChatAdapter";

    private List<Message> mMessageModels;

    public LiveChatRecyclerAdapter() {
        mMessageModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LEFT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_text_left, parent, false);
            return new LeftTextViewHolder(view);
        } else if (viewType == RIGHT_TEXT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_text_right, parent, false);
            return new RightTextViewHolder(view);
        } else if (viewType == LEFT_SOUND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_sound_left, parent, false);
            return new LeftSoundViewHolder(view);
        } else if (viewType == RIGHT_SOUND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_sound_right, parent, false);
            return new RightSoundViewHolder(view);
        }
        return super.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mMessageModels.get(position);
        int type = message.getType();
        if (message.isSelf()) {
            if (type == Message.TEXT) {
                Glide.with(holder.itemView.getContext()).load(message.getSpeakerFace()).into(((RightTextViewHolder) holder).mFaceView);
                ((RightTextViewHolder) holder).mRightText.setText(message.getText());
            } else {
                LiveChatUtil.getInstance().setAudioLayoutWidth(((RightSoundViewHolder) holder).mLayout, message.getDuration());
                Glide.with(holder.itemView.getContext()).load(message.getSpeakerFace()).into(((RightSoundViewHolder) holder).mFaceView);
                String path = holder.itemView.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/" + message.getSoundUUid() + ".pcm";
                Log.d(TAG, "onBindViewHolder: " + message.getDuration());
                ((RightSoundViewHolder) holder).mSoundTime.setText(MessageFormat.format("{0}\"", (message.getDuration() + 500) / 1000));
                ((RightSoundViewHolder) holder).mLayout.setOnClickListener(v -> Audio.getInstance().play(message.getSoundUUid() == null ? new File(message.getLocalSoundFile()) : new File(path)));
            }
        } else {
            if (type == Message.TEXT) {
                Glide.with(holder.itemView.getContext()).load(message.getSpeakerFace()).into(((LeftTextViewHolder) holder).mFaceView);
                ((LeftTextViewHolder) holder).mLeftText.setText(message.getText());
            } else {
                if (!message.isRead()) {
                    ((LeftSoundViewHolder) holder).mIsRead.setVisibility(View.VISIBLE);
                }
                ((LeftSoundViewHolder) holder).mSoundTime.setText(MessageFormat.format("{0}\"", (message.getDuration() + 500) / 1000));
                Glide.with(holder.itemView.getContext()).load(message.getSpeakerFace()).into(((LeftSoundViewHolder) holder).mFaceView);
                LiveChatUtil.getInstance().setAudioLayoutWidth(((LeftSoundViewHolder) holder).mLayout, (int) message.getDuration());
                ((LeftSoundViewHolder) holder).mLayout.setOnClickListener(v -> {
                    ((LeftSoundViewHolder) holder).mIsRead.setVisibility(View.INVISIBLE);
//                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + message.getSoundUUid() + ".pcm";
                    String path = holder.itemView.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/" + message.getSoundUUid() + ".pcm";
                    Audio.getInstance().play(new File(path));
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageModels.get(position);
        if (message.isSelf()) {
            if (message.getType() == Message.TEXT) {
                return RIGHT_TEXT;
            } else if (message.getType() == Message.SOUND) {
                return RIGHT_SOUND;
            }
        } else {
            if (message.getType() == Message.TEXT) {
                return LEFT_TEXT;
            } else if (message.getType() == Message.SOUND) {
                return LEFT_SOUND;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mMessageModels.size();
    }

    public void receiveCurrentMsg(Message model) {
        mMessageModels.add(model);
        notifyItemInserted(mMessageModels.size() - 1);
        Log.d(TAG, "receiveCurrentMsg: " + mMessageModels.size());
    }

    public void receiveBeforeMsg(Message model) {
        mMessageModels.add(0, model);
        notifyItemInserted(0);
    }

    static class LeftTextViewHolder extends ViewHolder {
        TextView mLeftText;
        ImageView mFaceView;

        LeftTextViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftText = itemView.findViewById(R.id.live_left_msg);
            mFaceView = itemView.findViewById(R.id.message_face_left);
        }
    }

    static class RightTextViewHolder extends ViewHolder {
        TextView mRightText;
        ImageView mFaceView;

        RightTextViewHolder(@NonNull View itemView) {
            super(itemView);
            mRightText = itemView.findViewById(R.id.live_right_msg);
            mFaceView = itemView.findViewById(R.id.message_face_right);
        }
    }

    static class LeftSoundViewHolder extends ViewHolder {
        RelativeLayout mLayout;
        ImageView mFaceView, mIsRead;
        TextView mSoundTime;

        LeftSoundViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.live_left);
            mFaceView = itemView.findViewById(R.id.message_face_left);
            mIsRead = itemView.findViewById(R.id.isRead);
            mSoundTime = itemView.findViewById(R.id.live_left_audio_time);
        }
    }

    static class RightSoundViewHolder extends ViewHolder {
        RelativeLayout mLayout;
        ImageView mFaceView;
        TextView mSoundTime;

        RightSoundViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.live_right_sound_layout);
            mFaceView = itemView.findViewById(R.id.message_face_right);
            mSoundTime = itemView.findViewById(R.id.live_right_audio_time);
        }
    }
}
