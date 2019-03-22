package com.study.emoticons.presenter;

import android.os.Bundle;
import android.view.View;

import com.study.emoticons.base.BasePresenter;
import com.study.emoticons.bean.Image_cloud;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.greendao.dao.Image_cloudDao;
import com.study.emoticons.imageselector.utils.ImageSelector;
import com.study.emoticons.iview.IMainView;
import com.study.emoticons.utils.ListUtil;
import com.study.emoticons.view.activity.PhotoViewActivity;
import com.study.emoticons.view.adapter.ImageAdapter_second;
import com.study.emoticons.view.fragment.EmoticonsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter {
    private static final int REQUEST_CODE = 0x00000011;
    private IMainView view;
    private EmoticonsFragment emoticonsFragment;
    private List<Image_cloud> imageList;

    public MainPresenter(IMainView view, EmoticonsFragment emoticonsFragment) {
        super(view, emoticonsFragment);
        this.view = view;
        this.emoticonsFragment = emoticonsFragment;
    }

    //初始化数据
    public void InitData(View view) {

        imageList = QueryDao();
        if (!ListUtil.isEmpty(imageList)) {
            emoticonsFragment.refresh(imageList);
        } else {
            Operation.equal(view, name, this);
        }

    }

    public void SelectImg() {
        ImageSelector.builder()
                .useCamera(true)
                .setSingle(false)
                .setViewImage(true)
                .setMaxSelectCount(9)
                .start(emoticonsFragment, REQUEST_CODE);
    }

    /**
     * 删除上传的表情
     * @param mView
     * @param imageAdapter
     */
    public void DeletImg(View mView, ImageAdapter_second imageAdapter) {
        List<Image_cloud> image_clouds = imageAdapter.getSelectImages();
        if (ListUtil.isEmpty(image_clouds)) {
            imageAdapter.setVisibility(true);
            view.showMessage("请选择需要删除的表情后，再次点击此处！");
        } else {
            Operation.delete(mView, image_clouds, this);
            imageAdapter.clearSelectImages();
            imageAdapter.setVisibility(false);
        }
    }

    /**
     *  查询本地数据库
     */
    public List<Image_cloud> QueryDao() {

        return daoSession.getImage_cloudDao()
                .queryBuilder()
                .where(Image_cloudDao.Properties.Name.eq(name))
                .list();
    }

    //写入本地数据库
    public void WriteToGreenDao(ArrayList<Image_cloud> newImages) {

        Image_cloudDao imagesDao = daoSession.getImage_cloudDao();

        for (Image_cloud newImage : newImages) {
            imagesDao.insertOrReplace(newImage);
        }

    }

    /**
     * 更新本地表情数据库
     * @param image
     */
    public void updateImage(Image_cloud image) {

        //使用主键查询需要修改的数据
        Image_cloudDao imageDao = daoSession.getImage_cloudDao();
        Image_cloud newImage = imageDao.load(image.getId());

        //修改本地路径
        newImage.setPath(image.getPath());
        //修改
        imageDao.update(newImage);
    }

    /**
     * 删除相应数据
     *
     * @param image_clouds
     */
    public void deletImage(List<Image_cloud> image_clouds) {
        Image_cloudDao image_cloudDao = daoSession.getImage_cloudDao();
        for (Image_cloud image_cloud : image_clouds) {
            image_cloudDao.delete(image_cloud);
        }
        view.refresh(QueryDao());
    }

    /**
     * 查询bomb云端数据
     *
     * @param view
     */
    public void equel(View view){
        Operation.equal(view,name,this);
    }

    /**
     *  上传文件到云端数据库
     * @param imageClouds
     * @param view
     */
    public void upload(ArrayList<Image_cloud> imageClouds,View view){
        Operation.upload(imageClouds, view,this);
    }

    public void photoView(String path) {
        Bundle bundle = new Bundle();
        bundle.putString("path_args", path);
        emoticonsFragment.startActivity(PhotoViewActivity.class, bundle);
    }

    public void refresh(List<Image_cloud> image_clouds){
        view.refresh(image_clouds);
    }
}
