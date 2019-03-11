package com.study.emoticons.view.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.study.emoticons.R;
import com.study.emoticons.customview.popupWindow.CommonPopupWindow;

public class PopupWindowPhoto implements CommonPopupWindow.ViewInterface {
    private CommonPopupWindow popupWindow;
    private Context context;
    private View view;

    public PopupWindowPhoto(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void init() {
        if (popupWindow != null && popupWindow.isShowing())
            return;
        popupWindow = new CommonPopupWindow
                .Builder(context)
                .setView(R.layout.photoview)
                .setBackgroundLevel(0.8f)
                .setAnimationStyle(R.style.AnimUp)
                .showAtLocation(view, Gravity.BOTTOM,0,0)
                .create();
    }

    @Override
    public void getChildView(View view, int layoutResId) {

    }
}
