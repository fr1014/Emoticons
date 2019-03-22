package com.study.emoticons.iview;

import com.study.emoticons.base.IBaseView;
import com.study.emoticons.bean.Image_cloud;

import java.util.List;

public interface IMainView extends IBaseView {
    void refresh(List<Image_cloud> images);
}
