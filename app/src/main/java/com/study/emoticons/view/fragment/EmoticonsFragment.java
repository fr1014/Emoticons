package com.study.emoticons.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.emoticons.R;
import com.study.emoticons.greendao.dao.Image_cloudDao;
import com.study.emoticons.model.Image_cloud;
import com.study.emoticons.utils.ToastUtils;
import com.study.emoticons.view.activity.PhotoViewActivity;
import com.study.emoticons.view.adapter.ImageAdapter_second;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.imageselector.utils.ImageSelector;
import com.study.emoticons.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmoticonsFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 0x00000011;
    private static EmoticonsFragment emoticonsFragment;
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.text_toolbar)
    TextView tv_toolbar;
    @BindView(R.id.gird_recycler)
    RecyclerView grid_recycler;
    @BindView(R.id.select)
    ImageView iv_select;
    @BindView(R.id.refresh)
    ImageView iv_refresh;
    @BindView(R.id.delet)
    ImageView iv_delet;
    private ImageAdapter_second imageAdapter;
    private List<Image_cloud> imageList = new ArrayList<>();

    public static EmoticonsFragment newInstance(String s) {
        emoticonsFragment = new EmoticonsFragment();
        Bundle args = new Bundle();
        args.putString("args", s);
        emoticonsFragment.setArguments(args);
        return emoticonsFragment;
    }

    @Override
    protected void visibleToUser(boolean isVisible, boolean isFirstVisible) {

    }

    @Override
    protected void initConfig(Bundle savedInstanceState) {

    }

    @Override
    protected void initArguments(Bundle bundle) {
        iv_select.setVisibility(View.VISIBLE);
        iv_refresh.setVisibility(View.VISIBLE);
        iv_delet.setVisibility(View.VISIBLE);
        if (bundle != null) {
            String text = bundle.getString("args");
            tv_toolbar.setText(text);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoticons;
    }

    @Override
    protected void daoBusiness() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        grid_recycler.setLayoutManager(gridLayoutManager);
        imageAdapter = new ImageAdapter_second(context, emoticonsFragment);
        grid_recycler.setAdapter(imageAdapter);
        iv_select.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        iv_delet.setOnClickListener(this);
        initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                ImageSelector.builder()
                        .useCamera(true)
                        .setSingle(false)
                        .setViewImage(true)
                        .setMaxSelectCount(9)
                        .start(this, REQUEST_CODE);
                break;
            case R.id.refresh:
                Operation.query(rootView, emoticonsFragment);
                break;
            case R.id.delet:
                List<Image_cloud> image_clouds = imageAdapter.getSelectImages();
                if (ListUtil.isEmpty(image_clouds)) {
                    imageAdapter.setVisibility(true);
                    ToastUtils.shortToast(context, "请选择需要删除的表情后，再次点击此处！");
                } else {
                    Operation.delete(rootView, image_clouds, emoticonsFragment);
//                    Operation.delete(rootView,image_clouds,emoticonsFragment);
                    imageAdapter.clearSelectImages();
                    imageAdapter.setVisibility(false);
                }
                break;

        }
    }

    //初始化数据
    private void initData() {
        imageList = QueryDao();

        if (!ListUtil.isEmpty(imageList)) {
            emoticonsFragment.refresh(imageList);
        } else {
            Operation.query(rootView, emoticonsFragment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {

            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);

            //上传文件到云端数据库
            Operation.upload(images, rootView);

            Operation.query(rootView, emoticonsFragment);
        }
    }

    //写入本地数据库
    public void WriteToGreenDao(ArrayList<Image_cloud> images) {

        Image_cloudDao imagesDao = daoSession.getImage_cloudDao();

        for (Image_cloud newImage : images) {
            imagesDao.insertOrReplace(newImage);
        }

    }

    //查询本地数据库
    public List<Image_cloud> QueryDao() {

        imageList = daoSession.getImage_cloudDao().queryBuilder().list();
        return imageList;
    }

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
        imageAdapter.refresh(QueryDao());
    }

    public void refresh(List<Image_cloud> images) {
        imageAdapter.refresh(images);
    }

    public void photoView(String path) {
        Bundle bundle = new Bundle();
        bundle.putString("path_args",path);
        emoticonsFragment.startActivity(PhotoViewActivity.class,bundle);
    }

}
