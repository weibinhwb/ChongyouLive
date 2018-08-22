package com.example.weibin.chongyoulive.ui;

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

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.base.BaseFragment;
import com.example.weibin.chongyoulive.base.DetailLiveBean;
import com.example.weibin.chongyoulive.base.HomeLivePresenter;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeFragment extends BaseFragment implements View.OnClickListener, IHomeView<DetailLiveBean>{

    private RecyclerView mHomeLiveRecycler;
    private HomeLiveAdapter mLiveAdapter;
    private IHomePresenter mIHomePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeLiveRecycler = (RecyclerView) view.findViewById(R.id.main_recycler);
        mLiveAdapter = new HomeLiveAdapter(getContext());
        mHomeLiveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeLiveRecycler.setAdapter(mLiveAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void loadData() {
        super.loadData();
        mIHomePresenter = new HomeLivePresenter(this);
    }

    @Override
    public void showView(DetailLiveBean detailLiveBean) {
        mLiveAdapter.setDetailLiveBean(detailLiveBean);
        mLiveAdapter.notifyDataSetChanged();
    }

    @Override
    public void failed() {
        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
    }
}
