package com.study.emoticons.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseActivity;
import com.study.emoticons.constans.Constans;
import com.study.emoticons.qq.ThreadManager;
import com.study.emoticons.view.fragment.TextTabFragment;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private TextTabFragment textTabFragment;
    public static Tencent mTencent;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000011;

    @Override
    protected void initBundleData(Bundle bundle) {
//        setImmersiveStatusBar(true);
        setDeepColorStatusBar(true);
        hideToolBar(true);
    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBusiness() {

        Bmob.initialize(this,"02f82c2bfdf2a8204bdc09ca4d1aaa83");

        mTencent = Tencent.createInstance(Constans.APP_ID, MyApplication.appContext);
        setBottomNavView();

        checkPermission();
    }

    /**
     * 检查权限。
     */
    private void checkPermission() {

        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限

        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
    }

    private void setBottomNavView() {
        textTabFragment = TextTabFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_view, textTabFragment).commit();
    }

    public void shareImgToQQ(String imagePath) {
        Bundle shareParams = new Bundle();
        shareParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_IMAGE);    //分享的类型。图文分享(普通分享)
        shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imagePath);
        doShareToQQ(shareParams);

    }

    private void doShareToQQ(Bundle params) {

        //QQ分享需要在主线程中进行
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mTencent != null){
                    mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
                }
            }
        });

    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }

}
