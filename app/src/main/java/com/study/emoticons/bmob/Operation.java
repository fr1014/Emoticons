package com.study.emoticons.bmob;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.study.emoticons.bmob.bean.PersonEmoticon;
import com.study.emoticons.model.Image_cloud;
import com.study.emoticons.utils.ToastUtils;
import com.study.emoticons.view.activity.MainActivity;
import com.study.emoticons.view.fragment.EmoticonsFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class Operation {

    /**
     * 新增多条数据
     */
    public static void save(View view, List<BmobFile> files) {
        List<BmobObject> personEmoticons = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            PersonEmoticon personEmoticon = new PersonEmoticon();
            personEmoticon.setEmoticons(files.get(i));
            personEmoticons.add(personEmoticon);
        }
        new BmobBatch().insertBatch(personEmoticons).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Snackbar.make(view, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, "第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Snackbar.make(view, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }

    /**
     * 批量删除数据
     *
     * @param imageList
     */
    public static void delet(View view, List<Image_cloud> imageList) {
        List<BmobObject> personEmoticons = new ArrayList<>();

        for (int i = 0; i < imageList.size(); i++) {
            PersonEmoticon personEmoticon = new PersonEmoticon();
            personEmoticon.setObject(imageList.get(i).getObjectId());
            personEmoticons.add(personEmoticon);
        }

        new BmobBatch().deleteBatch(personEmoticons).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                deletFiles(view, imageList);
            }
        });
    }

    /**
     * 批量删除文件
     * @param view
     * @param imageList
     */
    public static void deletFiles(View view, List<Image_cloud> imageList) {
        String[] urls = new String[imageList.size()];
        for (int i = 0; i < imageList.size(); i++) {
            urls[i] = String.valueOf(imageList.get(i).getUrl());
        }
        BmobFile.deleteBatch(urls, new DeleteBatchListener() {

            @Override
            public void done(String[] failUrls, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "删除成功!", Snackbar.LENGTH_LONG).show();
                } else {
                    if (failUrls != null) {
                        Snackbar.make(view, "删除失败个数：" + failUrls.length + "," + e.toString(), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, "全部文件删除失败：" + e.getErrorCode() + "," + e.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * 查询多条数据
     */
    public static void query(View view, EmoticonsFragment emoticonsFragment) {
        final List<Image_cloud> imagesList = new ArrayList<>();

        BmobQuery<PersonEmoticon> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<PersonEmoticon>() {
            @Override
            public void done(List<PersonEmoticon> personEmoticons, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < personEmoticons.size(); i++) {
                        Image_cloud image = new Image_cloud();
                        image.setId(personEmoticons.get(i).getObjectId());
                        image.setUrl(personEmoticons.get(i).getEmoticons().getUrl());
                        image.setObjectId(personEmoticons.get(i).getObjectId());
                        imagesList.add(image);
                    }
                    Snackbar.make(view, "加载成功：" + personEmoticons.size(), Snackbar.LENGTH_LONG).show();
                    emoticonsFragment.WriteToGreenDao((ArrayList<Image_cloud>) imagesList);
                    emoticonsFragment.refresh(imagesList);
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }

        });
    }

    /**
     * 批量上传关联文件
     *
     * @param images
     * @param view
     */
    public static void upload(ArrayList<String> images, View view) {

        final String[] filePaths = new String[images.size()];

        for (int i = 0; i < images.size(); i++)
            filePaths[i] = images.get(i);

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                    //2.更新数据对象
                    save(view, files);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                Snackbar.make(view, "错误码" + statuscode + ",错误描述：" + errormsg, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    /**
     * 下载文件
     *
     * @param emoticonsFragment
     * @param image
     */
    public static void downloadFile(EmoticonsFragment emoticonsFragment, Context context, Image_cloud image) {
        MainActivity mainActivity = (MainActivity) context;
        BmobQuery<PersonEmoticon> query = new BmobQuery<>();
        if (image.getPath() != null && new File(image.getPath()).exists()) {
            mainActivity.shareImgToQQ(image.getPath());
        } else {
            query.getObject(image.getObjectId(), new QueryListener<PersonEmoticon>() {
                @Override
                public void done(PersonEmoticon personEmoticon, BmobException e) {
                    if (e == null) {
                        download(emoticonsFragment, context, personEmoticon.getEmoticons(), image, mainActivity);
                    }
                }
            });
        }
    }

    private static void download(EmoticonsFragment emoticonsFragment, Context context, BmobFile emoticons, Image_cloud image, Activity activity) {
        MainActivity mainActivity = (MainActivity) context;
        File saveFile = new File(context.getExternalCacheDir(), emoticons.getFilename());

        emoticons.download(saveFile, new DownloadFileListener() {
            @Override
            public void done(String path, BmobException e) {
                if (e == null) {
                    image.setPath(path);
                    emoticonsFragment.updateImage(image);

                    //QQ分享
                    mainActivity.shareImgToQQ(image.getPath());
                }

            }

            @Override
            public void onProgress(Integer integer, long l) {

            }

            @Override
            public void onStart() {
                super.onStart();
                ToastUtils.shortToast(context, "正在发送...");
            }

            @Override
            public void doneError(int code, String msg) {
                super.doneError(code, msg);
                ToastUtils.shortToast(context, "请检查网络连接！");
            }
        });
    }

}
