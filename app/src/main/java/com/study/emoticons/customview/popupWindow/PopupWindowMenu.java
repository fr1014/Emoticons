package com.study.emoticons.customview.popupWindow;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.study.emoticons.R;

public class PopupWindowMenu implements CommonPopupWindow.ViewInterface, View.OnClickListener {
    private CommonPopupWindow popupWindow;
    private Context context;
    private View view;
    private RelativeLayout layout;
    private Handler mHandler = new Handler();

    private ImageView upload;
    private ImageView close;

    public PopupWindowMenu(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void init() {

        layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.more_window, null);

        if (popupWindow != null && popupWindow.isShowing()) return;

        popupWindow = new CommonPopupWindow
                .Builder(context)
                .setView(R.layout.more_window)
                .setBackgroundLevel(0.8f)
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .showAtLocation(view, Gravity.BOTTOM, 0, 0)
                .create();
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获取PopupWindow布局
        switch (layoutResId) {
            case R.layout.more_window:
                upload = view.findViewById(R.id.ic_upload);
                close = view.findViewById(R.id.close);
                upload.setOnClickListener(this::onClick);
                close.setOnClickListener(this::onClick);
                colseAnimation();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_upload:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.close:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    private void colseAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                close.animate().rotation(90).setDuration(600).start();
            }
        });
    }

}
