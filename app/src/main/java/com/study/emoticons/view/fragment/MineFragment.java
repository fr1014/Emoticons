package com.study.emoticons.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.constans.Constans;
import com.study.emoticons.greendao.dao.ConfiguesDao;
import com.study.emoticons.model.Configues;
import com.study.emoticons.utils.GlideUtils;
import com.study.emoticons.utils.ListUtil;
import com.study.emoticons.utils.ToastUtils;
import com.study.emoticons.view.activity.MainActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;


public class MineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.text_toolbar)
    TextView toolbar;
    @BindView(R.id.iv_head)
    ImageView iv_Head;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.user_name)
    TextView name;
    @BindView(R.id.iv_logout)
    ImageView logout;

    private UserInfo mUserInfo;
    private String user_name;
    private String user_head_img;
    private ConfiguesDao configuesDao;
    private List<Configues> configuesList;

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
        configuesDao = daoSession.getConfiguesDao();
        configuesList = getConfiguesList();

        logout.setOnClickListener(this);
        if (configues != null && configues.getStatus_online()) {
            login.setVisibility(View.GONE);
            loadUserInfo(configues.getUser_name(), configues.getHead_img_url());
        } else {
            login.setVisibility(View.VISIBLE);
            login.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                //all表示获取所有权限
                MainActivity.mTencent.login(MineFragment.this, "all", new BaseUiListener());
                break;
            case R.id.iv_logout:
                login.setVisibility(View.VISIBLE);
                iv_Head.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                MainActivity.mTencent.logout(MyApplication.appContext);
//                Configues newConfigues = configues;
//                newConfigues.setStatus_online(false);
//                WriteToGreenDao(newConfigues);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }

    }

    /**
     * 写入数据库
     *
     * @param configues
     */
    private void WriteToGreenDao(Configues configues) {

        if (ListUtil.isEmpty(configuesList)) {
            configuesDao.insert(configues);
        } else {
            configuesDao.update(configues);
        }
    }

    /**
     *  用户登录后，加载头像和用户名
     */
    private void loadUserInfo(String user_name, String user_head_img) {
        name.setText(user_name);
        name.setVisibility(View.VISIBLE);
        String img_url = user_head_img.replace("http", "https");

        GlideUtils.load(context,img_url,iv_Head,new RequestOptions().circleCrop());
    }

    /**
     * QQ登录回调
     */
    private class BaseUiListener implements IUiListener {
        public void onComplete(Object response) {

            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                MainActivity.mTencent.setOpenId(openID);
                MainActivity.mTencent.setAccessToken(accessToken, expires);

                //保存当前用户的openid做为标识
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("login_user", Context.MODE_PRIVATE).edit();
                editor.putString("openId", openID);
                editor.apply();

                QQToken qqToken = MainActivity.mTencent.getQQToken();
                mUserInfo = new UserInfo(MyApplication.appContext, qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        ToastUtils.shortToast(MyApplication.appContext, "登录成功");
                        login.setVisibility(View.GONE);
                        JSONObject mUserInfoObj = (JSONObject) response;
                        try {
                            user_name = mUserInfoObj.getString("nickname");
                            user_head_img = mUserInfoObj.getString("figureurl_qq_2");
                            if (user_name != null && user_head_img != null) {

                                loadUserInfo(user_name, user_head_img);

                                Configues configues = new Configues();
                                configues.setOpenid(openID);
                                configues.setUser_name(user_name);
                                configues.setHead_img_url(user_head_img);
                                configues.setStatus_online(true);
                                WriteToGreenDao(configues);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        ToastUtils.shortToast(MyApplication.appContext, "登录失败");
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.shortToast(MyApplication.appContext, "登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            ToastUtils.shortToast(MyApplication.appContext, "授权失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.shortToast(MyApplication.appContext, "授权取消");
        }

    }

}
