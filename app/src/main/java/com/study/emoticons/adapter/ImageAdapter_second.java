package com.study.emoticons.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.emoticons.R;
import com.study.emoticons.activity.MainActivity;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.fragment.EmoticonsFragment;
import com.study.emoticons.model.Image;

import java.util.List;

public class ImageAdapter_second extends RecyclerView.Adapter<ImageAdapter_second.PalceViewHolder> {
    private static final String TAG = "ImageAdapter_second";
    private Context context;
    private List<Image> mImages;
    private LayoutInflater mInFlater;

    private MainActivity mainActivity;
    private EmoticonsFragment emoticonsFragment;

    private Boolean LOAD_FLAG  = false;

    public ImageAdapter_second(Context context,EmoticonsFragment emoticonsFragment) {
        this.context = context;
        mInFlater = LayoutInflater.from(context);
        mainActivity = (MainActivity) context;
        this.emoticonsFragment = emoticonsFragment;
    }

    public List<Image> getImages() {
        return mImages;
    }

    @NonNull
    @Override
    public PalceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInFlater.inflate(R.layout.layout_recyclerview_custom, viewGroup, false);
        return new PalceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalceViewHolder palceViewHolder, int i) {
        String image = mImages.get(i).getUrl();
//        if (LOAD_FLAG){
            Glide
                    .with(context)
                    .load(image)
                    .into(palceViewHolder.mImage);
//        }else {
//            Glide
//                    .with(context)
//                    .load(new File(image))
//                    .into(palceViewHolder.mImage);
//        }

        /**
         * 单击图片分享给好友
         */
        palceViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.shareToQQ(mImages.get(i).getPath());
                Log.d(TAG, "fanrui"+mImages.get(i).getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public  void refresh(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }

//    public void refresh_url(List<Image> imageList,Boolean load_flag) {
//        this.LOAD_FLAG = load_flag;
//        mImages = imageList;
//        notifyDataSetChanged();
//    }

    public class PalceViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        ImageView ivGif;

        public PalceViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.iv_image);
            ivGif = itemView.findViewById(R.id.iv_gif);
        }
    }
}
