package com.example.weibin.chongyoulive.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weibin.chongyoulive.R;

import java.util.List;

/**
 * Created by weibin on 2018/8/25
 */


public class AboutMyDetailAdapter extends RecyclerView.Adapter<AboutMyDetailAdapter.AboutDetailViewHolder> {

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
    }

    @NonNull
    @Override
    public AboutDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_about_me_detail, parent, false);
        return new AboutDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutDetailViewHolder holder, int position) {
        holder.mItem1.setText(ITEMS[position]);
        holder.mItem2.setText(mItem2List.get(position));
        holder.mItemCard.setTag(position);
        holder.mItemCard.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(v, (Integer) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItem2List == null ? 0 : mItem2List.size();
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
}
