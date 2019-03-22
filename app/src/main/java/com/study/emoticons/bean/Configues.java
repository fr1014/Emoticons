package com.study.emoticons.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Configues {
    @Id(autoincrement = true)
    Long id;

    String head_img_url;
    String user_name;
    @Generated(hash = 1604941257)
    public Configues(Long id, String head_img_url, String user_name) {
        this.id = id;
        this.head_img_url = head_img_url;
        this.user_name = user_name;
    }
    @Generated(hash = 1086736944)
    public Configues() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
}
