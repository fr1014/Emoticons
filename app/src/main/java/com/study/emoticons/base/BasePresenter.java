package com.study.emoticons.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.study.emoticons.bean.Configues;
import com.study.emoticons.greendao.DaoUtils;
import com.study.emoticons.greendao.dao.DaoSession;
import com.study.emoticons.listener.ActivityLifeCycleListener;
import com.study.emoticons.listener.FragementLifeCycleListener;
import com.study.emoticons.utils.ListUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

public class BasePresenter<V, T> implements ActivityLifeCycleListener, FragementLifeCycleListener {

    protected Reference<V> viewRef;
    protected V view;

    protected Reference<T> activityRef;
    protected T activity;
    protected DaoSession daoSession;
    protected String name;
    //Configure 配置
    protected Configues configure;

    public BasePresenter(V view, T activity) {
        attchView(view);
        attchActivity(activity);
        setListener(activity);
        name = getLoginUser();
        daoSession = getDaoSession();

        List<Configues> configureList = getConfigureList();
        if (!ListUtil.isEmpty(configureList)){
            configure = configureList.get(0);
        }
    }

    private DaoSession getDaoSession() {
        return DaoUtils.getDaosession();
    }

    private String getLoginUser() {
        return DaoUtils.getLoginUser();
    }

    /**
     * 获取配置
     * @return
     */
    private List<Configues> getConfigureList() {
        return DaoUtils.getConfiguesList();
    }

    /**
     * 设置生命周期监听
     *
     * @param activity
     */
    private void setListener(T activity) {
        if (getActivity() != null) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
            } else if (activity instanceof BaseFragment) {
                ((BaseFragment) getActivity()).setOnLifeCycleListener(this);
            }
        }
    }

    /**
     * 获取activity
     *
     * @return
     */
    private T getActivity() {
        if (activityRef == null) {
            return null;
        }
        return activityRef.get();
    }

    /**
     * 获取View
     *
     * @return
     */
    private V getView() {
        if (viewRef == null) {
            return null;
        }
        return viewRef.get();
    }

    /**
     * 绑定Activity
     *
     * @param activity
     */
    private void attchActivity(T activity) {
        activityRef = new WeakReference<T>(activity);
        this.activity = activityRef.get();
    }

    /**
     * 绑定View
     *
     * @param view
     */
    private void attchView(V view) {
        viewRef = new WeakReference<V>(view);
        this.view = viewRef.get();
    }

    /**
     * 解除Activity绑定
     */
    private void detachActivity() {
        if (isActivityAttached()) {
            activityRef.clear();
            activityRef = null;
        }
    }

    /**
     * 解除View绑定
     */
    private void detachView() {
        if (isViewAttached()) {
            viewRef.clear();
            viewRef = null;
        }
    }

    private boolean isActivityAttached() {
        return activityRef != null && activityRef.get() != null;
    }

    private boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {

    }

    @Override
    public void Resume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (activity instanceof BaseActivity) {
            detachView();
            detachActivity();
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroyView() {
        if (activity instanceof BaseFragment) {
            detachView();
            detachActivity();
        }
    }
}
