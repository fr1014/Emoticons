package com.study.emoticons.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.emoticons.R;
import com.study.emoticons.presenter.MainPresenter;
import com.study.emoticons.utils.GlideUtils;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.bean.Image_cloud;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter_second extends RecyclerView.Adapter<ImageAdapter_second.PalceViewHolder> {

    private Context context;
    private List<Image_cloud> mImages;
    private LayoutInflater mInFlater;
    private MainPresenter mainPresenter;
    private List<ImageView> selectViewList = new ArrayList<>();

    private boolean isViewImage;
    //保存选中的图片
    private ArrayList<Image_cloud> mSelectImages = new ArrayList<>();

    public ImageAdapter_second(Context context, MainPresenter mainPresenter) {
        this.context = context;
        mInFlater = LayoutInflater.from(context);
        this.mainPresenter = mainPresenter;
    }

    @NonNull
    @Override
    public PalceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInFlater.inflate(R.layout.layout_recyclerview_custom, viewGroup, false);
        return new PalceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalceViewHolder palceViewHolder, int i) {
        selectViewList.add(palceViewHolder.iv_select);

        String imageUrl = mImages.get(i).getUrl();

        GlideUtils.load(context, imageUrl, palceViewHolder.mImage);

        /**
         * 单击图片分享给好友
         */
        palceViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Operation.downloadFile(mainPresenter, context, mImages.get(i));

            }
        });

        palceViewHolder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedImage(palceViewHolder, mImages.get(i));
            }
        });

        palceViewHolder.mImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String path = mImages.get(i).getPath();
                if (path == null) {
                    mainPresenter.photoView(imageUrl);
                } else {
                    mainPresenter.photoView(path);
                }
                return true;
            }
        });
    }

    private void checkedImage(PalceViewHolder palceViewHolder, Image_cloud image_cloud) {
        if (mSelectImages.contains(image_cloud)) {
            //如果图已选择，取消选择
            unSelectImage(image_cloud);
            setItemSelect(palceViewHolder, false);
        } else {
            selectImage(image_cloud);
            setItemSelect(palceViewHolder, true);
        }
    }

    /**
     * 选中图片
     *
     * @param image_cloud
     */
    private void selectImage(Image_cloud image_cloud) {
        mSelectImages.add(image_cloud);
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(PalceViewHolder palceViewHolder, boolean isSelect) {
        if (isSelect) {
            palceViewHolder.iv_select.setImageResource(R.drawable.icon_select);
        } else {
            palceViewHolder.iv_select.setImageResource(R.drawable.icon_un_select);
        }
    }

    public void setVisibility(Boolean view) {
        if (view) {
            for (ImageView select : selectViewList) {
                select.setImageResource(R.drawable.icon_un_select);
                select.setVisibility(View.VISIBLE);
            }

        } else {
            for (ImageView select : selectViewList) {
                select.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 取消选择图片
     *
     * @param image_cloud
     */
    private void unSelectImage(Image_cloud image_cloud) {
        mSelectImages.remove(image_cloud);
    }

    public ArrayList<Image_cloud> getSelectImages() {
        return mSelectImages;
    }

    public void clearSelectImages() {
        mSelectImages.clear();
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(List<Image_cloud> images) {
        mImages = images;
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mImages.remove(position);
        notifyItemRemoved(position);
    }

    public class PalceViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        ImageView iv_select;

        public PalceViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.iv_image);
            iv_select = itemView.findViewById(R.id.iv_select_delet);
        }
    }

}
