package com.study.emoticons.view.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.utils.GlideUtils;
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

        if (configues != null) {
            loadUserInfo(configues.getHead_img_url());
        }
    }

    @OnClick({R.id.rel_un_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_un_login:

                SplashActivity.mTencent.logout(MyApplication.appContext);
                startActivity(LoginActivity.class);
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
        }
    }

    /**
     * 用户登录后，加载头像和用户名
     */
    private void loadUserInfo(String user_head_img) {

        String img_url = user_head_img.replace("http", "https");

        GlideUtils.load(context, img_url, iv_Head, new RequestOptions().circleCrop());
    }

}
