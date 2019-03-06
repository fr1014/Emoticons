package com.study.emoticons.bmob.bean;

import cn.bmob.v3.BmobObject;

public class PersonImage extends BmobObject {
    private String id;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
