package com.study.emoticons.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.study.emoticons.constans.Constans;
import com.study.emoticons.greendao.dao.DaoMaster;
import com.study.emoticons.greendao.dao.DaoSession;

public class DaoHelper {
    private volatile static DaoHelper instance;
    private SQLiteDatabase db;
    private DaoSession daoSession;

    private DaoHelper(Context context){
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,Constans.DBNAME);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    static DaoHelper getDaoHelper(Context context){
        if (instance == null){
            synchronized (DaoHelper.class){
                if (instance == null){
                    instance = new DaoHelper(context);
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public SQLiteDatabase getDb(){
        return db;
    }
}
