package com.study.emoticons.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseActivity;
import com.study.emoticons.model.Configues;
import com.study.emoticons.utils.ListUtil;
import com.study.emoticons.utils.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private UserInfo mUserInfo;
    private List<Configues> configuesList;

    @Override
    protected void initBundleData(Bundle bundle) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {
        setDeepColorStatusBar(true);
        hideToolBar(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void doBusiness() {
        SharedPreferences.Editor editor = getSharedPreferences("login_user", MODE_PRIVATE).edit();
        editor.putString("name", "*");
        editor.apply();
    }

    @OnClick({R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                ToastUtils.shortToast(context, "正在登录...");
                //all表示获取所有权限
                SplashActivity.mTencent.login(this, "all", new BaseUiListener());
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

        if (ListUtil.isEmpty(getConfiguesList())) {
            getDaoSession().getConfiguesDao().insert(configues);
        } else {
            Configues newCofigures = getConfiguesList().get(0);
            newCofigures.setUser_name(configues.getUser_name());
            newCofigures.setHead_img_url(configues.getHead_img_url());
            newCofigures.setUser_name(configues.getUser_name());
            getDaoSession().getConfiguesDao().update(newCofigures);
        }
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
                SplashActivity.mTencent.setOpenId(openID);
                SplashActivity.mTencent.setAccessToken(accessToken, expires);

                QQToken qqToken = SplashActivity.mTencent.getQQToken();
                mUserInfo = new UserInfo(MyApplication.appContext, qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        ToastUtils.shortToast(MyApplication.appContext, "登录成功");
                        JSONObject mUserInfoObj = (JSONObject) response;
                        try {
                            String user_name = mUserInfoObj.getString("nickname");
                            String user_head_img = mUserInfoObj.getString("figureurl_qq_2");

                            //保存当前用户的openid做为标识
                            SharedPreferences.Editor editor = getSharedPreferences("login_user", Context.MODE_PRIVATE).edit();
                            editor.putString("name", user_name);
                            editor.apply();

                            if (user_name != null && user_head_img != null) {

                                Configues configues = new Configues();
                                configues.setUser_name(user_name);
                                configues.setHead_img_url(user_head_img);

                                WriteToGreenDao(configues);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(MainActivity.class);
                        finish();
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
