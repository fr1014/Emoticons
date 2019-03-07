package com.study.emoticons.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {

    public static void load(Context context, String url, ImageView imageView , RequestOptions options){
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
