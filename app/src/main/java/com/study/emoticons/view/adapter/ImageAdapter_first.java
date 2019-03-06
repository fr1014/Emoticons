package com.study.emoticons.view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.emoticons.R;
import com.study.emoticons.utils.ToastUtils;

public class ImageAdapter_first extends RecyclerView.Adapter<ImageAdapter_first.PlaceViewHoder> {

    private Context context;
    private int[] mPlaceList;

    public ImageAdapter_first(Context context, int[] mPlaceList) {
        this.context = context;
        this.mPlaceList = mPlaceList;
    }

    @NonNull
    @Override
    public PlaceViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_custom, viewGroup, false);
        return new PlaceViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHoder placeViewHoder, int i) {
        Glide
                .with(context)
                .load(mPlaceList[i])
                .into(placeViewHoder.mPlace);

        placeViewHoder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.shortToast(context, mPlaceList[i] + "点击");
            }
        });

        placeViewHoder.itemView.setOnLongClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ToastUtils.shortToast(context, "长按");
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceList.length;
    }

    public class PlaceViewHoder extends RecyclerView.ViewHolder {

        ImageView mPlace;

        public PlaceViewHoder(@NonNull View itemView) {
            super(itemView);

            mPlace = itemView.findViewById(R.id.iv_image);
        }
    }
}
