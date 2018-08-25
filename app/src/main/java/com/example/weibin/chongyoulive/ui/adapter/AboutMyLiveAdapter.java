package com.example.weibin.chongyoulive.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.ui.activity.AboutMeDetailActivity;
import com.example.weibin.chongyoulive.ui.activity.AddLiveActivity;
import com.example.weibin.chongyoulive.ui.activity.LoginActivity;
import com.example.weibin.chongyoulive.util.FastBlur;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by weibin on 2018/8/21
 */


public class AboutMyLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TIMGroupBaseInfo> mGroupBaseInfos;
    private TIMUserProfile mTIMUserProfile;
    private static final int TYPE_DATA = 0;
    private static final int TYPE_ADD_LIVE = 1;
    private static final int TYPE_LIVE = 2;
    private Context mContext;

    public AboutMyLiveAdapter(Context context) {
        mContext = context;
    }

    public void setTIMUserProfile(TIMUserProfile TIMUserProfile) {
        this.mTIMUserProfile = TIMUserProfile;
    }

    public void setGroupBaseInfos(List<TIMGroupBaseInfo> groupBaseInfos) {
        this.mGroupBaseInfos = groupBaseInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == TYPE_DATA) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_about_my_data, viewGroup, false);
            return new AboutMyDataViewHolder(view);
        } else if (i == TYPE_ADD_LIVE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_about_my_add_live, viewGroup, false);
            return new AboutMyAddLiveViewHolder(view);
        }
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_about_my_live, viewGroup, false);
        return new AboutMyLiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AboutMyDataViewHolder) {
            ((AboutMyDataViewHolder) holder).mAboutMeName.setText(mTIMUserProfile.getNickName());
            ((AboutMyDataViewHolder) holder).mAboutMeSlogan.setText(mTIMUserProfile.getSelfSignature());
            Glide.with(holder.itemView.getContext()).load(mTIMUserProfile.getFaceUrl()).into(((AboutMyDataViewHolder) holder).mAboutMeImage);
            Glide.with(holder.itemView.getContext()).asBitmap().load(mTIMUserProfile.getFaceUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Glide.with(holder.itemView.getContext()).load(FastBlur.doBlur(resource, 20, false)).into(((AboutMyDataViewHolder) holder).mAboutMeBg);
                }
            });
            if(TIMManager.getInstance().getLoginUser().equals(""))
            {
                ((AboutMyDataViewHolder) holder).mAboutMeCard.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, LoginActivity.class)));
            } else {
                ((AboutMyDataViewHolder) holder).mAboutMeCard.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, AboutMeDetailActivity.class)));
            }
        } else if (holder instanceof AboutMyAddLiveViewHolder) {
            ((AboutMyAddLiveViewHolder) holder).mAddLiveText.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, AddLiveActivity.class)));
        } else if (holder instanceof AboutMyLiveViewHolder) {
            ((AboutMyLiveViewHolder) holder).mLiveName.setText(mGroupBaseInfos.get(position - 2).getGroupName());
            Glide.with(holder.itemView.getContext()).load(mGroupBaseInfos.get(position - 2).getFaceUrl()).into(((AboutMyLiveViewHolder) holder).mLiveImage);
            ((AboutMyLiveViewHolder) holder).mCardView.setOnClickListener(v -> Toast.makeText(mContext, "这是第" + holder.getAdapterPosition()+ "个item", Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public int getItemCount() {
        return mGroupBaseInfos ==  null || mTIMUserProfile == null? 0 : mGroupBaseInfos.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_DATA;
        } else if (position == 1) {
            return TYPE_ADD_LIVE;
        }
        return TYPE_LIVE;
    }

    static class AboutMyLiveViewHolder extends RecyclerView.ViewHolder {
        TextView mLiveName;
        ImageView mLiveImage;
        CardView mCardView;
        AboutMyLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.about_my_live);
            mLiveName = itemView.findViewById(R.id.about_my_live_title);
            mLiveImage = itemView.findViewById(R.id.about_my_live_image);
        }
    }

    static class AboutMyAddLiveViewHolder extends RecyclerView.ViewHolder {
        TextView mAddLiveText;
        AboutMyAddLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddLiveText = itemView.findViewById(R.id.about_me_add_live);
        }
    }

    static class AboutMyDataViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mAboutMeImage;
        TextView mAboutMeName, mAboutMeSlogan, mAboutMeNext;
        CardView mAboutMeCard;
        ImageView mAboutMeBg;
        AboutMyDataViewHolder(@NonNull View view) {
            super(view);
            mAboutMeImage = view.findViewById(R.id.about_me_image);
            mAboutMeName = view.findViewById(R.id.about_me_name);
            mAboutMeSlogan = view.findViewById(R.id.about_me_slogan);
            mAboutMeNext = view.findViewById(R.id.about_me_next);
            mAboutMeCard = view.findViewById(R.id.about_me_card);
            mAboutMeBg = view.findViewById(R.id.about_me_data_bg);
        }
    }
}
