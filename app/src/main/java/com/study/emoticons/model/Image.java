package com.study.emoticons.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Image {

    @Id
    Long id;

    String path; //本地路径
    String url; //网络路劲
    String objectId; //bomb数据库对象标识
    @Generated(hash = 20386611)
    public Image(Long id, String path, String url, String objectId) {
        this.id = id;
        this.path = path;
        this.url = url;
        this.objectId = objectId;
    }
    @Generated(hash = 1590301345)
    public Image() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
   
}
