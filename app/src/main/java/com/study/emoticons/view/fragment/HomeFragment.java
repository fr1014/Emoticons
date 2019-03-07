package com.study.emoticons.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.study.emoticons.R;
import com.study.emoticons.view.adapter.ImageAdapter_first;
import com.study.emoticons.base.BaseFragment;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.gird_recycler)
    RecyclerView gird_recycler;
    @BindView(R.id.text_toolbar)
    TextView tv_toolbar;

    int[] mPlaceList = new int[]{};

    public static HomeFragment newInstance(String s) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("args", s);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle arguments) {
        if (arguments != null) {
            String text = arguments.getString("args");
            tv_toolbar.setText(text);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void daoBusiness() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 4);
        gird_recycler.setLayoutManager(mGridLayoutManager);
        ImageAdapter_first mGirdLayoutAdapter = new ImageAdapter_first(context, mPlaceList);
        gird_recycler.setAdapter(mGirdLayoutAdapter);
    }
}
