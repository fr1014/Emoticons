package com.study.emoticons.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.emoticons.greendao.DaoUtils;
import com.study.emoticons.greendao.dao.DaoSession;
import com.study.emoticons.listener.FragementLifeCycleListener;
import com.study.emoticons.bean.Configues;
import com.study.emoticons.utils.ListUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {
    protected Unbinder unbinder;
    protected FragementLifeCycleListener lifeCycleListener;
    protected Context context;
    protected Activity activity;
    protected boolean isViewCreat;
    protected boolean isUIVisible;
    protected boolean isFirstVisible;
    protected DaoSession daoSession;
    protected String name;
    protected Configues configues;

    /**
     * 获取daoSession
     *
     * @return
     */
    protected DaoSession getDaoSession() {
        return DaoUtils.getDaosession();
    }

    /**
     * 获取配置
     *
     * @return
     */
    protected List<Configues> getConfiguesList() {
        return DaoUtils.getConfiguesList();
    }

    protected String getLoginUser() {
        return DaoUtils.getLoginUser();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisible = isVisibleToUser;

        if (isViewCreat && isUIVisible && isFirstVisible) {
            visibleToUser(true, true);
            isFirstVisible = false;
        }

        if (isViewCreat && isUIVisible) {
            visibleToUser(true, false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (lifeCycleListener != null) {
            lifeCycleListener.onAttach(context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (lifeCycleListener != null) {
            lifeCycleListener.onCreate(savedInstanceState);
        }
        isFirstVisible = true;

        List<Configues> configuesList = getConfiguesList();
        if (!ListUtil.isEmpty(configuesList)) {
            configues = configuesList.get(0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (lifeCycleListener != null) {
            lifeCycleListener.onCreateView(inflater, container, savedInstanceState);
        }

        View view = inflater.inflate(getLayoutId(), container, false);

        initConfig(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        initArguments(arguments);


        context = getContext();

        isViewCreat = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (lifeCycleListener != null) {
            lifeCycleListener.onActivityCreated(savedInstanceState);
        }
        activity = getActivity();
        daoSession = getDaoSession();
        name = getLoginUser();
        daoBusiness();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifeCycleListener != null) {
            lifeCycleListener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lifeCycleListener != null) {
            lifeCycleListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifeCycleListener != null) {
            lifeCycleListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (lifeCycleListener != null) {
            lifeCycleListener.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (lifeCycleListener != null) {
            lifeCycleListener.onDestroyView();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lifeCycleListener != null) {
            lifeCycleListener.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (lifeCycleListener != null) {
            lifeCycleListener.onDetach();
        }
    }

    /**
     * 设置生命周期监听
     *
     * @param lifeCycleListener
     */
    public void setOnLifeCycleListener(FragementLifeCycleListener lifeCycleListener) {
        this.lifeCycleListener = lifeCycleListener;
    }

    /**
     * fragment对用户是否可见
     *
     * @param isVisible      是否可见
     * @param isFirstVisible 是否第一次可见
     */
    protected abstract void visibleToUser(boolean isVisible, boolean isFirstVisible);

    /**
     * 初始化Activity配置
     */
    protected abstract void initConfig(Bundle savedInstanceState);


    /**
     * 初始化Bundle参数
     */
    protected abstract void initArguments(Bundle bundle);

    /**
     * 获取xml layout
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 业务逻辑代码
     */
    protected abstract void daoBusiness();

    /**
     * 页面跳转
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    /**
     * 页面携带数据跳转
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 包含回调的页面跳转
     *
     * @param clazz
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityWithOptions(Class<?> clazz, Bundle options) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        if (options != null) {
            startActivity(intent, options);
        }
    }
}
