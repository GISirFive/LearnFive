package com.telchina.init.image;

import android.content.Context;
import android.widget.ImageView;

import com.telchina.pub.image.IBaseImageLoadingListener;
import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.image.LoaderType;

import java.io.File;

/**
 * Created by GISirFive on 2016-4-1.
 */
public class ImageLoader{

    private static IImageLoader ImageLoader = null;

    public static IImageLoader init(Context context){
        if(ImageLoader == null){
            DisplayOptions.init();
            ImageLoader = new IImageLoaderImp(context);
        }
        return ImageLoader;
    }

    /**
     * 获取图片显示核心类
     *
     * @return
     */
    public static com.nostra13.universalimageloader.core.ImageLoader getLoader() {
        return (com.nostra13.universalimageloader.core.ImageLoader) ImageLoader.getLoader();
    }

    /**
     * 以默认方式加载图片{@link LoaderType#_DEFAULT}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    public static void display(String uri, ImageView imageView) {
        ImageLoader.display(uri, imageView);
    }

    /**
     * 加载迷你型缩略图{@link LoaderType#_MINI}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    public static void displayMini(String uri, ImageView imageView) {
        ImageLoader.displayMini(uri, imageView);
    }

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @param type
     */
    public static void display(String uri, ImageView imageView, LoaderType type) {
        ImageLoader.display(uri, imageView, type);
    }

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @param type
     * @param listener
     */
    public static void display(String uri, ImageView imageView, LoaderType type, IBaseImageLoadingListener listener) {
        ImageLoader.display(uri, imageView, type, listener);
    }

    /**
     * 加载图片，通过接口返回加载结果
     *
     * @param uri
     * @param type
     * @param listener
     */
    public static void load(String uri, LoaderType type, IBaseImageLoadingListener listener) {
        ImageLoader.load(uri, type, listener);
    }

    /**
     * 根据uri获取对应的文件<br>
     * 图片返回顺序：
     * <pre>
     *     缓存的默认图片
     *     缓存的原图
     *     缓存的迷你型缩略图
     *     缓存的微型缩略图
     *     自定义大小的图片
     *     NULL（无缓存）
     * </pre>
     *
     * @param uri
     * @return File
     */
    public static File getCacheFile(String uri, LoaderType type) {
        return ImageLoader.getCacheFile(uri, type);
    }
}
