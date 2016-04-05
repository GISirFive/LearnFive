package com.telchina.pub.utils;

import com.utils.AESUtils;

/**
 * Created by GISirFive on 2016-3-30.
 */
public class Config {
    /** 服务器地址，加密后 */
    private static final String SERVER = "A0F7889A917E8264CFB3255E720ADE667FEDFEBD17BB8A00982CBDB3E0D35F1C";
    /** 配置文件名 */
    private static final String NAME_CONFIG = "jbth";
    /** 缓存文件夹名称 */
    private static final String NAME_CACHE_DIR = "publicCache";
    /** 用户信息 */
    private static final String USER_INFO = "userInfo";

    /** 默认每一页可以装载的信息数量 */
    public static final int PAGE_SIZE = 12;
    /** 数据库版本 */
    public static final int DB_VERSION = 102;
    /** 是否为调试模式 */
    public static final boolean DEBUG = true;
    /** Log输出标签 */
    public static final String LOG_TAG = "输出";

    public enum KEY {
        /** 服务器地址*/
        SERVER,
        /** 配置文件名 */
        NAME_CONFIG,
        /** 缓存文件夹名称 */
        NAME_CACHE_DIR,
        /** 用户信息 */
        USER_INFO,
    }

    /**
     * 根据key获取指定项的配置信息
     * @param key
     * @return
     */
    public static String get(KEY key){
        switch (key){
            case SERVER:
                return AESUtils.decode(SERVER);
            case NAME_CONFIG:
                return NAME_CONFIG;
            case NAME_CACHE_DIR:
                return NAME_CACHE_DIR;
            case USER_INFO:
                return USER_INFO;
        }
        return null;
    }
}
