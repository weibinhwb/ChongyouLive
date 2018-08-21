package com.example.weibin.chongyoulive;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weibin.chongyoulive.base.LiveBean;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;
import com.tencent.imsdk.protocol.msg;

import java.util.List;

public class HomeLiveAdapter extends RecyclerView.Adapter<HomeLiveAdapter.HomeLiveViewHolder>{

    private List<TIMGroupDetailInfo> mTIMGroupDetailInfos;

    public void setLiveBean(List<TIMGroupDetailInfo> timGroupDetailInfos) {
        this.mTIMGroupDetailInfos = timGroupDetailInfos;
    }

    @NonNull
    @Override
    public HomeLiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_recycler_home_live, viewGroup, false);
        return new HomeLiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLiveViewHolder homeLiveViewHolder, int i) {
        homeLiveViewHolder.mHomeLiveName.setText(mTIMGroupDetailInfos.get(i).getGroupName());
        Glide.with(homeLiveViewHolder.itemView.getContext()).load(mTIMGroupDetailInfos.get(i).getFaceUrl()).into(homeLiveViewHolder.mHomeLivePhoto);
    }

    @Override
    public int getItemCount() {
        return mTIMGroupDetailInfos == null ? 0 : mTIMGroupDetailInfos.size();
    }

    static class HomeLiveViewHolder extends RecyclerView.ViewHolder{
        TextView mHomeLiveName;
        ImageView mHomeLivePhoto;
        HomeLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mHomeLiveName = (TextView) itemView.findViewById(R.id.home_live_name);
            mHomeLivePhoto = (ImageView) itemView.findViewById(R.id.home_live_photo);
        }
    }
}
