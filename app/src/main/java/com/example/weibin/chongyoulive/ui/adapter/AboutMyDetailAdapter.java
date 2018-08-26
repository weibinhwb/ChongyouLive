package com.example.weibin.chongyoulive.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.util.TimeExchangeUtil;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static android.content.ContentValues.TAG;

/**
 * Created by weibin on 2018/8/25
 */


public class AboutMyDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE1 = 0;
    private static final int TYPE2 = 1;

    public interface onItemClick {
        void onItemClick(View view, int position);
    }

    private static final String ITEMS[] = {"账号", "用户名", "一句话描述", "性别", "生日", "位置", "语言"};
    private List<String> mItem2List;

    private onItemClick mOnItemClick;

    public void setOnItemClick(onItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setItem2List(List<String> item2List) {
        mItem2List = item2List;
        switch (mItem2List.get(3)) {
            case "Male":
                mItem2List.set(3, "男");
                break;
            case "Female":
                mItem2List.set(3, "女");
                break;
            default:
                mItem2List.set(3, "保密");
                break;
        }
        mItem2List.set(4, TimeExchangeUtil.Timestamp2String(Long.valueOf(mItem2List.get(4))).substring(0, 10));
        Log.d(TAG, "setItem2List: " + mItem2List.get(4));
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_about_me_detail, parent, false);
            return new AboutDetailViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_about_me_logout, parent, false);
        return new LogoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AboutDetailViewHolder) {
            ((AboutDetailViewHolder) holder).mItem1.setText(ITEMS[position]);
            ((AboutDetailViewHolder) holder).mItem2.setText(mItem2List.get(position));
            ((AboutDetailViewHolder) holder).mItemCard.setTag(position);
            ((AboutDetailViewHolder) holder).mItemCard.setOnClickListener(v -> {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(v, (Integer) v.getTag());
                }
            });
        } else if (holder instanceof LogoutViewHolder) {
            ((LogoutViewHolder) holder).mLogout.setTag(position);
            ((LogoutViewHolder) holder).mLogout.setOnClickListener(v -> {
                if (mOnItemClick != null){
                    mOnItemClick.onItemClick(v, (Integer) v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItem2List.size()) {
            return TYPE2;
        }
        return TYPE1;
    }

    @Override
    public int getItemCount() {
        return mItem2List == null ? 0 : mItem2List.size() + 1;
    }

    static class AboutDetailViewHolder extends RecyclerView.ViewHolder {
        TextView mItem1, mItem2;
        View mline;
        CardView mItemCard;

        AboutDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            mItem1 = itemView.findViewById(R.id.detail_item_text1);
            mItem2 = itemView.findViewById(R.id.detail_item_text2);
            mline = itemView.findViewById(R.id.detail_line);
            mItemCard = itemView.findViewById(R.id.detail_item);
        }
    }

    static class LogoutViewHolder extends RecyclerView.ViewHolder {
        TextView mLogout;
        LogoutViewHolder(@NonNull View itemView) {
            super(itemView);
            mLogout = itemView.findViewById(R.id.logout);
        }
    }
}
