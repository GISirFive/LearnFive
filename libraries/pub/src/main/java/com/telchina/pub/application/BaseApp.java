package com.telchina.pub.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.http.IRequestController;

public class BaseApp extends Application {

    private IAppInitController mInitController = null;

    public static Context CONTEXT = null;

    public static IRequestController HttpClient = null;

    public static IImageLoader ImageLoader = null;

    @Override
    public void onCreate() {
        CONTEXT = this;

        try {
            Class<?> clazz = Class
                    .forName("com.telchina.init.application.AppInitControllerImp");
            Constructor constructor = clazz.getConstructor(Application.class);
            mInitController = (IAppInitController) constructor
                    .newInstance(this);
        } catch (ClassNotFoundException | NoSuchMethodException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        mInitController.init();
        // 初始化网络请求
        HttpClient = mInitController.getHttpClient();
        // 加载图片
        ImageLoader = mInitController.getImageLoader();

        // 持久化Cookie
        mInitController.initCookieStore();

        super.onCreate();
    }

    /**
     * 获取该应用公共缓存路径
     *
     * @return
     * @author GISirFive
     */
    public static String getDiskCachePath() {
        return Environment.getExternalStorageDirectory() + "/test/";
    }


}
