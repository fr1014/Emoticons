package com.study.emoticons.greendao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.study.emoticons.bean.Configues;
import com.study.emoticons.bean.Emoticons;
import com.study.emoticons.bean.Image_cloud;

import com.study.emoticons.greendao.dao.ConfiguesDao;
import com.study.emoticons.greendao.dao.EmoticonsDao;
import com.study.emoticons.greendao.dao.Image_cloudDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig configuesDaoConfig;
    private final DaoConfig emoticonsDaoConfig;
    private final DaoConfig image_cloudDaoConfig;

    private final ConfiguesDao configuesDao;
    private final EmoticonsDao emoticonsDao;
    private final Image_cloudDao image_cloudDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        configuesDaoConfig = daoConfigMap.get(ConfiguesDao.class).clone();
        configuesDaoConfig.initIdentityScope(type);

        emoticonsDaoConfig = daoConfigMap.get(EmoticonsDao.class).clone();
        emoticonsDaoConfig.initIdentityScope(type);

        image_cloudDaoConfig = daoConfigMap.get(Image_cloudDao.class).clone();
        image_cloudDaoConfig.initIdentityScope(type);

        configuesDao = new ConfiguesDao(configuesDaoConfig, this);
        emoticonsDao = new EmoticonsDao(emoticonsDaoConfig, this);
        image_cloudDao = new Image_cloudDao(image_cloudDaoConfig, this);

        registerDao(Configues.class, configuesDao);
        registerDao(Emoticons.class, emoticonsDao);
        registerDao(Image_cloud.class, image_cloudDao);
    }
    
    public void clear() {
        configuesDaoConfig.clearIdentityScope();
        emoticonsDaoConfig.clearIdentityScope();
        image_cloudDaoConfig.clearIdentityScope();
    }

    public ConfiguesDao getConfiguesDao() {
        return configuesDao;
    }

    public EmoticonsDao getEmoticonsDao() {
        return emoticonsDao;
    }

    public Image_cloudDao getImage_cloudDao() {
        return image_cloudDao;
    }

}
