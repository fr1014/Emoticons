package com.study.emoticons.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.study.emoticons.R;
import com.study.emoticons.app.MyApplication;
import com.study.emoticons.base.BaseActivity;
import com.study.emoticons.constans.Constans;
import com.study.emoticons.utils.ListUtil;
import com.tencent.tauth.Tencent;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {
    public static Tencent mTencent;
    private static final int what = 520;
    private MyHandler handler = new MyHandler(this);

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
        return R.layout.activity_splash;
    }

    @Override
    protected void doBusiness() {
        mTencent = Tencent.createInstance(Constans.APP_ID, MyApplication.appContext);
        handler.sendEmptyMessage(what);
    }

    private boolean isLogin() {
        return name != null && !name.equals("*") && !ListUtil.isEmpty(getConfiguesList());
    }

    @Override
    protected void onDestroy() {
        handler = null;
        super.onDestroy();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> activityWeakReference;

        private MyHandler(SplashActivity splashActivity) {
            this.activityWeakReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = activityWeakReference.get();
            switch (msg.what) {
                case what:
                    if (splashActivity != null) {
                        if (splashActivity.isLogin()) {
                            splashActivity.startActivity(MainActivity.class);
                        } else {
                            splashActivity.startActivity(LoginActivity.class);
                        }
                        splashActivity.finish();
                    }
                    break;
            }
        }
    }
}
