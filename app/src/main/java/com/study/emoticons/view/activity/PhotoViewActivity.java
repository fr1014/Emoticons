package com.study.emoticons.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.study.emoticons.R;
import com.study.emoticons.base.BaseActivity;
import com.study.emoticons.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotoViewActivity extends BaseActivity{

    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.back)
    ImageView backView;
    private String path;

    @Override
    protected void initConfig(Bundle savedInstanceState) {
        setDeepColorStatusBar(true);
        hideToolBar(true);
    }

    @Override
    protected void initBundleData(Bundle bundle) {
        if (bundle != null) {
            path = bundle.getString("path_args");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void doBusiness() {
        if (path != null) {
            GlideUtils.load(context, path, photoView);
        }
    }

    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
