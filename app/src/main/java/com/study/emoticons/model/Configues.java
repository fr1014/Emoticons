package com.study.emoticons.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Configues {
    @Id(autoincrement = true)
    Long id;
    
    String openid;
    boolean status_online;
    String head_img_url;
    String user_name;

    @Generated(hash = 595372477)
    public Configues(Long id, String openid, boolean status_online,
            String head_img_url, String user_name) {
        this.id = id;
        this.openid = openid;
        this.status_online = status_online;
        this.head_img_url = head_img_url;
        this.user_name = user_name;
    }

    @Generated(hash = 1086736944)
    public Configues() {
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean getStatus_online() {
        return this.status_online;
    }

    public void setStatus_online(boolean status_online) {
        this.status_online = status_online;
    }

    public String getHead_img_url() {
        return this.head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
