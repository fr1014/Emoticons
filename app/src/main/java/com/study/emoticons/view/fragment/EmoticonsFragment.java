package com.study.emoticons.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.emoticons.R;
import com.study.emoticons.bean.Image_cloud;
import com.study.emoticons.iview.IMainView;
import com.study.emoticons.presenter.MainPresenter;
import com.study.emoticons.utils.ToastUtils;
import com.study.emoticons.view.adapter.ImageAdapter_second;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.imageselector.utils.ImageSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmoticonsFragment extends BaseFragment implements View.OnClickListener, IMainView {
    private static final int REQUEST_CODE = 0x00000011;

    private static EmoticonsFragment emoticonsFragment;
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.text_toolbar)
    TextView tv_toolbar;
    @BindView(R.id.gird_recycler)
    RecyclerView grid_recycler;
    @BindView(R.id.select)
    ImageView iv_select;
    @BindView(R.id.refresh)
    ImageView iv_refresh;
    @BindView(R.id.delet)
    ImageView iv_delet;
    ArrayList<Image_cloud> imageClouds = new ArrayList<>();
    private ImageAdapter_second imageAdapter;

    private MainPresenter mainPresenter;

    public static EmoticonsFragment newInstance(String s) {
        emoticonsFragment = new EmoticonsFragment();
        Bundle args = new Bundle();
        args.putString("args", s);
        emoticonsFragment.setArguments(args);
        return emoticonsFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle bundle) {
        iv_select.setVisibility(View.VISIBLE);
        iv_refresh.setVisibility(View.VISIBLE);
        iv_delet.setVisibility(View.VISIBLE);
        if (bundle != null) {
            String text = bundle.getString("args");
            tv_toolbar.setText(text);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoticons;
    }

    @Override
    protected void daoBusiness() {

        mainPresenter = new MainPresenter(this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        grid_recycler.setLayoutManager(gridLayoutManager);
        imageAdapter = new ImageAdapter_second(context, mainPresenter);
        grid_recycler.setAdapter(imageAdapter);
        iv_select.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        iv_delet.setOnClickListener(this);


        mainPresenter.InitData(rootView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                mainPresenter.SelectImg();
                break;
            case R.id.refresh:
                mainPresenter.equel(rootView);
                break;
            case R.id.delet:
                mainPresenter.DeletImg(rootView, imageAdapter);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {

            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (String path : images) {
                Image_cloud image_cloud = new Image_cloud();
                image_cloud.setPath(path);
                image_cloud.setName(name);
                imageClouds.add(image_cloud);
            }

            mainPresenter.upload(imageClouds, rootView);

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.shortToast(context, msg);
    }

    @Override
    public void refresh(List<Image_cloud> images) {
        imageAdapter.refresh(images);
    }
}
