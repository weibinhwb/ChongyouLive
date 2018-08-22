package com.example.weibin.chongyoulive.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.LiveMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weibin on 2018/8/23
 */


public class LiveChatRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<LiveMessageModel> mMessageModels;
    private static final int LEFT_TYPE = 0;
    private static final int RIGHT_TYPE = 1;
    private static final String TAG = "LiveChatAdapter";

    public LiveChatRecyclerAdapter() {
        mMessageModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LEFT_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_chat_left, parent, false);
            return new LeftChatViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_live_chat_right, parent, false);
            return new RightChatViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiveMessageModel model = mMessageModels.get(position);
        if (mMessageModels.get(position).isSelf()) {
            ((RightChatViewHolder) holder).mRightText.setText(model.getText());
        } else {
            ((LeftChatViewHolder) holder).mLeftText.setText(model.getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessageModels.get(position).isSelf()) {
            return RIGHT_TYPE;
        } else {
            return LEFT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageModels.size();
    }

    public void receiveMsg(LiveMessageModel model) {
        mMessageModels.add(model);
        notifyItemInserted(mMessageModels.size() - 1);
        Log.d(TAG, "receiveMsg: " + mMessageModels.size());
    }

    static class LeftChatViewHolder extends ViewHolder {
        TextView mLeftText;

        LeftChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftText = itemView.findViewById(R.id.live_left_msg);
        }
    }

    static class RightChatViewHolder extends ViewHolder {
        TextView mRightText;

        RightChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mRightText = itemView.findViewById(R.id.live_right_msg);
        }
    }
}
