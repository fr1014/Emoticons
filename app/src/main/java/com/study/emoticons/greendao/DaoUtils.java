package com.study.emoticons.greendao;

import android.content.Context;
import android.content.SharedPreferences;

import com.study.emoticons.app.MyApplication;
import com.study.emoticons.greendao.dao.ConfiguesDao;
import com.study.emoticons.greendao.dao.DaoSession;
import com.study.emoticons.bean.Configues;

import java.util.List;

public class DaoUtils {

    public static DaoSession getDaosession(){
        return DaoHelper.getDaoHelper(MyApplication.appContext).getDaoSession();
    }

    /**
     * 获取配置
     * @return
     */
    public static List<Configues> getConfiguesList() {
        ConfiguesDao configuesDao = getDaosession().getConfiguesDao();
        return configuesDao
                .queryBuilder()
                .where(ConfiguesDao.Properties.User_name.eq(getLoginUser()))
                .list();
    }

    /**
     * 获取当前登录用户的标识id
     * @return
     */
    public static String getLoginUser() {
        SharedPreferences preferences = MyApplication.appContext.getSharedPreferences("login_user", Context.MODE_PRIVATE);
        return preferences.getString("name","*");
    }
}
