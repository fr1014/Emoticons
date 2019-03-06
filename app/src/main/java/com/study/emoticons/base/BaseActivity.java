package com.study.emoticons.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;

import com.study.emoticons.greendao.DaoUtils;
import com.study.emoticons.greendao.dao.DaoSession;
import com.study.emoticons.listener.ActivityLifeCycleListener;
import com.study.emoticons.manager.ActivityStackManager;
import com.study.emoticons.utils.StatusBarUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {

    protected Context context;
    protected Activity activity;
    protected ActivityLifeCycleListener lifeCycleListener;
    protected Unbinder unbinder;
    protected DaoSession daoSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (lifeCycleListener != null) {
            lifeCycleListener.onCreate(savedInstanceState);
        }

        ActivityStackManager.getManager().push(this);
        initConfig(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        context = this;
        activity = this;
        daoSession = getDaoSession();

        Bundle bundle = getIntent().getExtras();
        initBundleData(bundle);
        doBusiness();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (lifeCycleListener != null) {
            lifeCycleListener.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lifeCycleListener != null) {
            lifeCycleListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lifeCycleListener != null) {
            lifeCycleListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lifeCycleListener != null) {
            lifeCycleListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getManager().remove(this);
        if (lifeCycleListener != null){
            lifeCycleListener.onDestroy();
        }
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (lifeCycleListener != null){
            lifeCycleListener.onRestart();
        }
    }

    /**
     * 设置生命周期监听
     * @param lifeCycleListener
     */
    public void setOnLifeCycleListener(ActivityLifeCycleListener lifeCycleListener){
        this.lifeCycleListener = lifeCycleListener;
    }

    protected DaoSession getDaoSession() {
        return DaoUtils.getDaosession();
    }

    protected abstract void initBundleData(Bundle bundle);

    protected abstract void initConfig(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract void doBusiness();

    /**
     * 设置屏幕旋转
     * SENSOR根据传感器自动旋转  0 :PORTRAIT竖屏  1:LANDSCAPE横屏
     *
     * @param flag
     */
    public void setScreenRotate(int flag) {
        switch (flag) {
            case 0:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case 1:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    /**
     * 页面跳转
     * @param clazz
     */
    public void startActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    /**
     * 页面携带数据跳转
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 包含回调的页面跳转
     * @param clazz
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz,int requestCode){
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        startActivityForResult(intent,requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startActivityWithOptions(Class<?> clazz, Bundle options){
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        if (options != null){
            startActivity(intent,options);
        }
    }

    public void hideToolBar(boolean hide){
        if (hide){
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.hide();
            }
        }
    }

    /**
     * 设置沉浸式状态栏
     * @param isSetStatusBar
     */
    public void setImmersiveStatusBar(boolean isSetStatusBar){
        if (isSetStatusBar){
            StatusBarUtils.setImmersiveStatusBar(this.getWindow());
        }
    }

    public void setDeepColorStatusBar(boolean isDeepColor){
        if (isDeepColor){
            StatusBarUtils.setDeepColorStatusBar(this.getWindow());
        }
    }

    /**
     * 使布局背景填充状态栏
     * @param isNoLimits
     */
    public void setLayoutNoLimits(boolean isNoLimits){
        if (isNoLimits){
            getWindow()
                    .addFlags(
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    );
        }
    }
}
