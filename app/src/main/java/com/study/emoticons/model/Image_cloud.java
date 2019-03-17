package com.study.emoticons.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Image_cloud {

    @Id
    String id;

    String path; //本地路径
    String url; //网络路径
    String objectId; //bomb数据库对象标识
    String name;  //用户唯一标识
    @Generated(hash = 1728983365)
    public Image_cloud(String id, String path, String url, String objectId,
            String name) {
        this.id = id;
        this.path = path;
        this.url = url;
        this.objectId = objectId;
        this.name = name;
    }
    @Generated(hash = 275081787)
    public Image_cloud() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
