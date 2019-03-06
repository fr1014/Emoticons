package com.study.emoticons.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.emoticons.R;
import com.study.emoticons.view.adapter.ImageAdapter_second;
import com.study.emoticons.base.BaseFragment;
import com.study.emoticons.bmob.Operation;
import com.study.emoticons.greendao.dao.ImageDao;
import com.study.emoticons.imageselector.utils.ImageSelector;
import com.study.emoticons.model.Image;
import com.study.emoticons.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmoticonsFragment extends BaseFragment {
    private static final String TAG = "EmoticonsFragment";

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
    private ImageAdapter_second imageAdapter;
    private List<Image> imageList = new ArrayList<>();

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
        initListener(this);
        initData();

    }

    private void initListener(Fragment fragment) {
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(true)
                        .setSingle(false)
                        .setViewImage(true)
                        .setMaxSelectCount(9)
                        .start(fragment, REQUEST_CODE);
            }
        });
    }

    //初始化数据
    private void initData() {
        imageList = QueryDao();

        if (!ListUtil.isEmpty(imageList)) {
            imageAdapter.refresh(imageList);
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
    public void WriteToGreenDao(ArrayList<Image> images) {
        List<Image> imagesList = new ArrayList<>();

        for (Image image : images) {
            Image img = new Image();
            img.setUrl(image.getUrl());
            img.setObjectId(image.getObjectId());
            imagesList.add(img);
        }

        ImageDao imagesDao = daoSession.getImageDao();
        //删除之前的数据库
        if (!ListUtil.isEmpty(imagesList)) {
            List<Image> list = QueryDao();
            for (Image oldImage : list) {
                imagesDao.delete(oldImage);
            }
        }

        for (Image newImage : imagesList) {
            imagesDao.insert(newImage);
        }

        imageAdapter.refresh(QueryDao());
    }

    //查询本地数据库
    private List<Image> QueryDao() {
        imageList = daoSession.getImageDao().queryBuilder().list();

        return imageList;
    }

//    public void LoadEmoticons(List<Image> imageList, Boolean load_flag) {
//        imageAdapter.refresh_url(imageList, load_flag);
//    }

    public void updateImage(Image image) {

        //使用主键查询需要修改的数据
        ImageDao imageDao = daoSession.getImageDao();
        Image newImage = imageDao.load(image.getId());

        //修改本地路径
        newImage.setPath(image.getPath());
        //修改
        imageDao.update(newImage);
    }

}
