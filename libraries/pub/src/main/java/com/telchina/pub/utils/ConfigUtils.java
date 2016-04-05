package com.telchina.pub.utils;

import java.util.ResourceBundle;

public class ConfigUtils {

    /***
     * 服务器地址，加密后
     */
    public static final String server = "A0F7889A917E8264CFB3255E720ADE667FEDFEBD17BB8A00982CBDB3E0D35F1C";
    /**
     * 配置文件名
     */
    public static final String privateInfo = "jbth";
    /***
     * 数据库版本
     */
    public static final int dbVersion = 102;

    public static final String startNum = "startNum";
    /**
     * 是否为调试模式
     */
    public static final boolean debug = true;
    /**
     * 默认每一页可以装载的信息数量
     */
    public static final int pageSize = 12;
    /**
     * 公共缓存
     */
    public static final String cachePublic = "cachePublic";
    /***
     * 用户信息
     */
    public static final String userInfo = "userInfo";
    /***
     * Log输出标签
     */
    public static final String LOG_TAG = "输出";

//    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("config");
//
//    public enum KEY {
//        /**
//         * 服务器地址
//         */
//        server,
//        /**
//         * 配置文件名
//         */
//        privateInfo,
//        /**
//         * 默认每一页可以装载的信息数量
//         */
//        pageSize,
//        /**
//         * 自动登录
//         */
//        autoLogin,
//        /**
//         * 是否为调试模式
//         */
//        debug,
//        /**
//         * 公共缓存
//         */
//        cachePublic,
//        /**
//         * 用户信息
//         */
//        userInfo,
//        /**
//         * 数据库版本
//         */
//        dbVersion,
//    }
//
//    public static final String getFromConfig(KEY name) {
//        switch (name) {
////		case server:
////			return AESUtils.decode(BUNDLE.getString(name.toString()));
//            default:
//                return BUNDLE.getString(name.toString());
//        }
//    }

}