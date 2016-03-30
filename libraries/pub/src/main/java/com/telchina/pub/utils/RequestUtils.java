package com.telchina.pub.utils;

import android.util.Log;

/**
 * 接口
 *
 * @author GISirFive
 */
public class RequestUtils {

    /**
     * 接口标识
     */
    public enum RequestCode {
        /**
         * 检查版本更新
         */
        APP_UPDATE,

        /**
         * 用户登录
         */
        USER_LOGIN,

    }

    /**
     * 根据接口标识，获取请求地址
     *
     * @param flag
     * @return
     */
    public static String getUrlWithFlag(RequestCode flag) {

//		String server = ConfigUtils.getFromConfig(KEY.server);
        String server = "http://10.10.41.73:8080/pms/";
        String api = "";
        switch (flag) {
            default:
                api = "login.do";
                break;
        }
        Log.i("业主请求", server + api);
        return server + api;
    }


}
