package com.study.emoticons.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Emoticons {
    @Id
    Long id;
    String url;
    @Generated(hash = 292855115)
    public Emoticons(Long id, String url) {
        this.id = id;
        this.url = url;
    }
    @Generated(hash = 1852366807)
    public Emoticons() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
