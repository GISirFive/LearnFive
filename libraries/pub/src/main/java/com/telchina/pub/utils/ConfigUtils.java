package com.telchina.pub.utils;

import java.util.ResourceBundle;

public class ConfigUtils {

	public static final String LOG_TAG = "输出";
	
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("config");
	
	public enum KEY{
		/** 服务器地址 */
		server,
		/** 配置文件名 */
		privateInfo,
		/** 默认每一页可以装载的信息数量 */
		pageSize,
		/** 自动登录 */
		autoLogin,
		/** 是否为调试模式 */
		debug,
		/** 公共缓存 */
		cachePublic,
		/** 用户信息 */
		userInfo,
		/** 数据库版本 */
		dbVersion,
	}
	
	public static final String getFromConfig(KEY name){
		switch (name) {
//		case server:
//			return AESUtils.decode(BUNDLE.getString(name.toString()));
		default:
			return BUNDLE.getString(name.toString());
		}
	}

}