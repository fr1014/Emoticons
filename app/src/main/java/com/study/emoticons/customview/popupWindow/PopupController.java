package com.study.emoticons.customview.popupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupController {
    public int layoutResId; //布局id
    public Context context;
    public PopupWindow popupWindow;
    public View mPopupWindow;  //弹窗布局View
    public View mView;
    public Window mWindow;

    PopupController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    public void setView(int layoutResId) {
        mView = null;
        this.layoutResId = layoutResId;
        installContent();
    }

    public void setView(View view) {
        mView = view;
        this.layoutResId = 0;
        installContent();
    }

    private void installContent() {
        if (layoutResId != 0) {
            mPopupWindow = LayoutInflater.from(context).inflate(layoutResId, null);
        } else if (mView != null) {
            mPopupWindow = mView;
        }

        popupWindow.setContentView(mPopupWindow);
    }

    /**
     * 设置宽高
     *
     * @param width  宽
     * @param height 高
     */
    public void setWidthAndHeight(int width, int height) {
        if (width == 0 || height == 0) {
            //如果设置宽高，默认是WRAP_CONTENT
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow.setWidth(width);
            popupWindow.setHeight(height);
        }
    }

    /**
     * 设置背景透明度
     *
     * @param level 0.0f-1.0f
     */
    public void setBackgroundLevel(float level) {
        mWindow = ((Activity) context).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = level;
        mWindow.setAttributes(params);
    }

    /**
     * 设置动画
     *
     * @param animationStyle
     */
    public void setAnimationStyle(int animationStyle) {
        popupWindow.setAnimationStyle(animationStyle);
    }

    public void setOutsideTouchable(boolean touchable) {
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置透明背景
        popupWindow.setOutsideTouchable(touchable); //设置outside可点击
        popupWindow.setFocusable(touchable);
    }

    /**
     * 设置popUpWindow的显示位置
     * @param parent
     * @param gravity
     * @param x
     * @param y
     */
    public void  showAtLocation(View parent, int gravity, int x, int y){
        popupWindow.showAtLocation(parent,gravity,x,y);
    }

    static class PopupParams {
        public Context context;
        public int layoutResId;
        int mWidth, mHeight;
        boolean isShowBg, isShowAnim;
        float bg_level;
        int animationStyle;
        View mView;
        boolean isTouchable = true;
        View parent;
        int gravity,x,y;

        public PopupParams(Context context) {
            this.context = context;
        }

        public void apply(PopupController popupController) {
            if (mView != null) {
                popupController.setView(mView);
            } else if (layoutResId != 0) {
                popupController.setView(layoutResId);
            } else {
                throw new IllegalArgumentException("PopupView's contentView is null");
            }
            popupController.setWidthAndHeight(mWidth, mHeight);
            popupController.setOutsideTouchable(isTouchable); //设置outside可点击
            if (isShowBg) {
                //设置背景
                popupController.setBackgroundLevel(bg_level);
            }
            if (isShowAnim) {
                popupController.setAnimationStyle(animationStyle);
            }
            if (parent!=null){
                popupController.showAtLocation(parent,gravity,x,y);
            }
        }
    }
}
