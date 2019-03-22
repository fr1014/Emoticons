package com.study.emoticons.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.customview.AlertDialog_Common;
import com.study.emoticons.utils.DataCleanManager;
import com.study.emoticons.utils.GlideUtils;
import com.study.emoticons.utils.ToastUtils;
import com.study.emoticons.view.activity.LoginActivity;
import com.study.emoticons.view.activity.SplashActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @BindView(R.id.text_toolbar)
    TextView toolbar;
    @BindView(R.id.head_img)
    ImageView iv_Head;
    //    @BindView(R.id.user_name)
//    TextView name;
    @BindView(R.id.int_cache)
    TextView tv_cache;

    private String data;

    public static MineFragment newInstance(String s) {
        MineFragment mineFragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("args", s);
        mineFragment.setArguments(args);
        return mineFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle bundle) {
        if (bundle != null) {
            String text = bundle.getString("args");
            toolbar.setText(text);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;

    }

    @Override
    protected void daoBusiness() {

        initHeadImg();
        initCache();
    }

    private void initCache() {
        try {
            data = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_cache.setText(data);
    }

    private void initHeadImg() {
        if (configues != null) {
            loadUserInfo(configues.getHead_img_url());
        }
    }

    @OnClick({R.id.rel_un_login, R.id.rel_cache, R.id.rel_update, R.id.rel_about})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_un_login:
                popUnLoginDialog();
                break;
            case R.id.rel_cache:
                popCacheDialog();
                break;
            case R.id.rel_update:
                ToastUtils.shortToast(context, "暂未完成");
                break;
            case R.id.rel_about:
                ToastUtils.shortToast(context, "暂未完成");
                break;
        }
    }

    private void popUnLoginDialog() {
        new AlertDialog_Common(context, "确定退出登录？", "提示", "确定", "取消")
                .setOnDiaLogListener(new AlertDialog_Common.OnDialogListener() {
                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        SplashActivity.mTencent.logout(MyApplication.appContext);
                        startActivity(LoginActivity.class);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

                    }
                }).showDialog();
    }

    private void popCacheDialog() {
        new AlertDialog_Common(context, "删除该应用缓存", "提示", "确定", "取消")
                .setOnDiaLogListener(new AlertDialog_Common.OnDialogListener() {

                    @Override
                    public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                        DataCleanManager.clearAllCache(context);
                        tv_cache.setText("0k");
                    }

                    @Override
                    public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {

                    }
                }).showDialog();
    }

    /**
     * 用户登录后，加载头像和用户名
     */
    private void loadUserInfo(String user_head_img) {

//        String img_url = user_head_img.replace("http", "https");

        GlideUtils.load(context, user_head_img, iv_Head, new RequestOptions().placeholder(R.drawable.ic_launcher).circleCrop());
    }

}
