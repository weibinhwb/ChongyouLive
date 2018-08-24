package com.example.weibin.chongyoulive.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.bean.DetailLiveBean;
import com.example.weibin.chongyoulive.base.LiveDetailModel;
import com.example.weibin.chongyoulive.ui.activity.LiveDetailActivity;

public class HomeLiveAdapter extends RecyclerView.Adapter<HomeLiveAdapter.HomeLiveViewHolder> {

    private DetailLiveBean mDetailLiveBean;
    private Context mContext;
    public static final String SHOW_DETAIL = "show_detail";

    public HomeLiveAdapter(Context context) {
        mContext = context;
    }

    public void setDetailLiveBean(DetailLiveBean detailLiveBean) {
        mDetailLiveBean = detailLiveBean;
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
        holder.mHomeLiveName.setText(mDetailLiveBean.getGroupInfo().get(i).getName());
        Glide.with(holder.itemView.getContext()).load(mDetailLiveBean.getGroupInfo().get(i).getFaceUrl()).into(holder.mHomeLivePhoto);
        holder.mCardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LiveDetailActivity.class);
            LiveDetailModel model = new LiveDetailModel();
            DetailLiveBean.GroupInfoBean groupInfoBean = mDetailLiveBean.getGroupInfo().get(holder.getAdapterPosition());
            model.setLiveName(groupInfoBean.getName());
            model.setLiveIntroduce(groupInfoBean.getIntroduction());
            model.setLivePhotoUrl(groupInfoBean.getFaceUrl());
            model.setLiveOwener(groupInfoBean.getOwner_Account());
            model.setLiveDate(groupInfoBean.getCreateTime() + "");
            model.setLiveJoinPerson(groupInfoBean.getMemberList().size() + "");
//            model.setLiveOwenerIntroduce(groupInfoBean.ge);
            model.setLiveId(groupInfoBean.getGroupId());
            intent.putExtra(SHOW_DETAIL, model);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDetailLiveBean == null ? 0 : mDetailLiveBean.getGroupInfo().size();
    }

    static class HomeLiveViewHolder extends RecyclerView.ViewHolder {
        TextView mHomeLiveName;
        ImageView mHomeLivePhoto;
        CardView mCardView;

        HomeLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mHomeLiveName = itemView.findViewById(R.id.home_live_name);
            mHomeLivePhoto = itemView.findViewById(R.id.home_live_photo);
            mCardView = itemView.findViewById(R.id.home_live_card);
        }
    }
}
