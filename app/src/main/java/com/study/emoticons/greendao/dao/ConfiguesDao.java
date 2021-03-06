package com.study.emoticons.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.study.emoticons.bean.Configues;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONFIGUES".
*/
public class ConfiguesDao extends AbstractDao<Configues, Long> {

    public static final String TABLENAME = "CONFIGUES";

    /**
     * Properties of entity Configues.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Head_img_url = new Property(1, String.class, "head_img_url", false, "HEAD_IMG_URL");
        public final static Property User_name = new Property(2, String.class, "user_name", false, "USER_NAME");
    }


    public ConfiguesDao(DaoConfig config) {
        super(config);
    }
    
    public ConfiguesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONFIGUES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"HEAD_IMG_URL\" TEXT," + // 1: head_img_url
                "\"USER_NAME\" TEXT);"); // 2: user_name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONFIGUES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Configues entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String head_img_url = entity.getHead_img_url();
        if (head_img_url != null) {
            stmt.bindString(2, head_img_url);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(3, user_name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Configues entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String head_img_url = entity.getHead_img_url();
        if (head_img_url != null) {
            stmt.bindString(2, head_img_url);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(3, user_name);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Configues readEntity(Cursor cursor, int offset) {
        Configues entity = new Configues( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // head_img_url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // user_name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Configues entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setHead_img_url(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUser_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Configues entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Configues entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Configues entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
