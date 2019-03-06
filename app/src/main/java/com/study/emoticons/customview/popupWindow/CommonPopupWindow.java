package com.study.emoticons.customview.popupWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public class CommonPopupWindow extends PopupWindow {

    final PopupController popupController;

    public CommonPopupWindow(Context context){
        popupController = new PopupController(context,this);
    }

    @Override
    public int getWidth() {
        return popupController.mPopupWindow.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return popupController.mPopupWindow.getMeasuredHeight();
    }

    public interface ViewInterface{
        void getChildView(View view,int layoutResId);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        popupController.setBackgroundLevel(1.0f);
    }

    public static class Builder{
        private final PopupController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow
         * @return Builder
         */
        public Builder setView(int layoutResId){
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow
         * @return Builder
         */
        public Builder setView(View view){
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setViewOnclickListener(ViewInterface listener){
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         * @param width 宽
         * @param height 高
         * @return Builder
         */
        public Builder setWidthAndHeight(int width,int height){
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackgroundLevel(float level){
            params.isShowBg = true;
            params.bg_level = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutsideTouchable(boolean touchable){
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         * @param animationStyle
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle){
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        /**
         * 设置显示位置
         * @param parent
         * @param gravity
         * @param x
         * @param y
         * @return
         */
        public Builder showAtLocation(View parent,int gravity,int x,int y){
            params.parent = parent;
            params.gravity = gravity;
            params.x = x;
            params.y = y;
            return this;
        }

        public CommonPopupWindow create(){
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.context);
            params.apply(popupWindow.popupController);
            if (listener != null && params.layoutResId != 0){
                listener.getChildView(popupWindow.popupController.mPopupWindow,params.layoutResId);
            }
            return popupWindow;
        }
    }
}
