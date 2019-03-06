package com.study.emoticons.customview.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

public class MyImageView extends AppCompatImageView {

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desireWidth;

        Drawable drawable = getDrawable();
        if (drawable == null) {
            desireWidth = 0;
        } else {
            desireWidth = drawable.getIntrinsicWidth();
        }

        //获取期望值,基于MeasureSpec
        int widthSize = View.resolveSize(desireWidth, widthMeasureSpec);
        int heightSize = widthSize;

        //检查heightSize不要太大
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST) {
            if (heightSize > size) {
                heightSize = size;
            }
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}
