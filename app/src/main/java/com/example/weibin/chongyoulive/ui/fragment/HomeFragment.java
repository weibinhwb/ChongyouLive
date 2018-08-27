package com.example.weibin.chongyoulive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.weibin.chongyoulive.R;
import com.example.weibin.chongyoulive.bean.LiveData;
import com.example.weibin.chongyoulive.base.HomeLivePresenter;
import com.example.weibin.chongyoulive.ui.adapter.HomeLiveAdapter;

import java.util.List;

import static com.example.weibin.chongyoulive.base.IHomeLiveContract.*;

public class HomeFragment extends BaseFragment implements View.OnClickListener, IHomeView<List<LiveData>>{

    private RecyclerView mHomeLiveRecycler;
    private SwipeRefreshLayout mHomeRefresh;
    private HomeLiveAdapter mLiveAdapter;
    private ProgressBar mLoading;

    private IHomePresenter mIHomePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeLiveRecycler = view.findViewById(R.id.main_recycler);
        mHomeRefresh = view.findViewById(R.id.main_refresh);
        mLoading = view.findViewById(R.id.loading_data);
        mLiveAdapter = new HomeLiveAdapter(getContext());
        mHomeLiveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeLiveRecycler.setAdapter(mLiveAdapter);
        mHomeRefresh.setOnRefreshListener(() -> {
            mIHomePresenter.loadData();
            mHomeRefresh.setRefreshing(false);
        });
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
    public void showView(List<LiveData> liveData) {
        mLoading.setVisibility(View.INVISIBLE);
        mLiveAdapter.setLiveData(liveData);
        mLiveAdapter.notifyDataSetChanged();
    }

    @Override
    public void failed() {
        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
    }
}
