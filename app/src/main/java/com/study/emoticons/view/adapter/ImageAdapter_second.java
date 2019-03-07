package com.study.emoticons.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.emoticons.R;
import com.study.emoticons.utils.GlideUtils;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.view.fragment.EmoticonsFragment;
import com.study.emoticons.model.Image;

import java.util.List;

public class ImageAdapter_second extends RecyclerView.Adapter<ImageAdapter_second.PalceViewHolder> {
    private static final String TAG = "ImageAdapter_second";
    private Context context;
    private List<Image> mImages;
    private LayoutInflater mInFlater;
    private EmoticonsFragment emoticonsFragment;

    private Boolean LOAD_FLAG = false;

    public ImageAdapter_second(Context context, EmoticonsFragment emoticonsFragment) {
        this.context = context;
        mInFlater = LayoutInflater.from(context);
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
        String imageUrl = mImages.get(i).getUrl();

        GlideUtils.load(context,imageUrl,palceViewHolder.mImage);

        /**
         * 单击图片分享给好友
         */
        palceViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Operation.downloadFile(emoticonsFragment,context,mImages.get(i));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }

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
