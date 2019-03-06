package com.study.emoticons.bmob.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class PersonEmoticon extends BmobObject {

    private BmobFile emoticons;
    private BmobRelation likes;

    public PersonEmoticon(){

    }

    public PersonEmoticon(String objectId) {
        setObjectId(objectId);
    }

    public BmobFile getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(BmobFile emoticons) {
        this.emoticons = emoticons;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}
