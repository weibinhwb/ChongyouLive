package com.example.weibin.chongyoulive.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.LiveData;
import com.example.weibin.chongyoulive.ui.activity.LiveDetailActivity;

import java.util.List;

public class HomeLiveAdapter extends RecyclerView.Adapter<HomeLiveAdapter.HomeLiveViewHolder> {

    private List<LiveData>  mLiveData;

    public void setLiveData(List<LiveData> liveData) {
        mLiveData = liveData;
    }

    private Context mContext;
    public static final String SHOW_DETAIL = "show_detail";

    public HomeLiveAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public HomeLiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_recycler_home_live, viewGroup, false);
        return new HomeLiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLiveViewHolder holder, int i) {
        holder.mLiveName.setText(mLiveData.get(i).getLiveName());
        holder.mLiveJoinPerson.setText(String.format("已有%s人参与", mLiveData.get(i).getLiveJoinPerson()));
        Glide.with(holder.itemView.getContext()).load(mLiveData.get(i).getLiveFace()).into(holder.mLivePhoto);
        holder.mLiveOwerName.setText(mLiveData.get(i).getOwnerName());
        holder.mLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LiveDetailActivity.class);
//            LiveDetailModel model = new LiveDetailModel();
//            model.setLiveName(mLiveData.get(i).getLiveName());
////            model.setLiveIntroduce(mLiveData.get(i));
//            model.setLivePhotoUrl(groupInfoBean.getFaceUrl());
//            model.setLiveOwener(groupInfoBean.getOwner_Account());
//            model.setLiveDate(groupInfoBean.getCreateTime() + "");
//            model.setLiveJoinPerson(groupInfoBean.getMemberList().size() + "");
////            model.setLiveOwnerIntroduce(groupInfoBean.ge);
//            model.setLiveId(groupInfoBean.getGroupId());
            intent.putExtra(SHOW_DETAIL, mLiveData.get(i));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mLiveData == null ? 0 : mLiveData.size();
    }

    static class HomeLiveViewHolder extends RecyclerView.ViewHolder {
        TextView mLiveName, mLiveOwerName, mLiveJoinPerson;
        ImageView mLivePhoto;
        ConstraintLayout mLayout;

        HomeLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mLiveName = itemView.findViewById(R.id.home_live_name);
            mLiveOwerName = itemView.findViewById(R.id.home_live_ower_name);
            mLiveJoinPerson = itemView.findViewById(R.id.home_live_join);
            mLivePhoto = itemView.findViewById(R.id.home_live_photo);
            mLayout = itemView.findViewById(R.id.home_live_layout);
        }
    }
}
