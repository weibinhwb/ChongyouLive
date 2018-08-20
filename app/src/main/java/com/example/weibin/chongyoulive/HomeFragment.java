package com.example.weibin.chongyoulive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weibin.chongyoulive.base.HomeLivePresenter;
import com.example.weibin.chongyoulive.base.IHomeLiveContract;
import com.example.weibin.chongyoulive.base.LiveBean;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeFragment extends Fragment implements View.OnClickListener, IHomeView{

    private RecyclerView mHomeLiveRecycler;
    private HomeLiveAdapter mLiveAdapter;
    private IHomePresenter mIHomePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIHomePresenter = new HomeLivePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeLiveRecycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        mLiveAdapter = new HomeLiveAdapter();
        mHomeLiveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeLiveRecycler.setAdapter(mLiveAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showView(List<TIMGroupDetailInfo> timGroupDetailInfos) {
        mLiveAdapter.setLiveBean(timGroupDetailInfos);
        mLiveAdapter.notifyDataSetChanged();
    }

    @Override
    public void failed() {
        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
    }
}
