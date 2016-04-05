package com.telchina.pub.image;

import java.io.File;

import android.widget.ImageView;

/**
 * 图片加载器 <br>
 *     For example:
 *     <pre>
 *         // from SD card
 *         IImageLoader.display("file:///mnt/sdcard/image.png", ImageView);
 *         // from SD card (video thumbnail)
 *         IImageLoader.display("file:///mnt/sdcard/video.mp4", ImageView);
 *         // from content provider
 *         IImageLoader.display("content://media/external/images/media/13", ImageView);
 *         // from content provider (video thumbnail)
 *         IImageLoader.display("content://media/external/video/media/13", ImageView);
 *         // from assets
 *         IImageLoader.display("assets://image.png", ImageView);
 *         // from drawables (non-9patch images)
 *         IImageLoader.display("drawable://" + R.drawable.img, ImageView);
 *     </pre>
 *     <b>NOTE: </b>
 *     <pre>
 *         Use drawable:// only if you really need it!
 *         Always consider the native way to load drawables:
 *           ImageView.setImageResource(...) instead of using of ImageLoader.
 *     </pre>
 */
public interface IImageLoader {

    /**
     * 获取图片显示核心类
     *
     * @return
     */
    Object getLoader();

    /**
     * 以默认方式加载图片{@link LoaderType#_DEFAULT}，并显示在imageView中
     * @param uri
     * @param imageView
     */
    void display(String uri, ImageView imageView);

    /**
     * 加载迷你型缩略图{@link LoaderType#_MINI}，并显示在imageView中
     * @param uri
     * @param imageView
     */
    void displayMini(String uri, ImageView imageView);

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     * @param uri
     * @param imageView
     * @param type
     */
    void display(String uri, ImageView imageView, LoaderType type);

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     * @param uri
     * @param imageView
     * @param type
     * @param listener
     */
    void display(String uri, ImageView imageView, LoaderType type, IBaseImageLoadingListener listener);

    /**
     * 加载图片，通过接口返回加载结果
     * @param uri
     * @param type
     * @param listener
     */
    void load(String uri, LoaderType type, IBaseImageLoadingListener listener);

    /**
     * 根据图片加载地址、图片加载类型，获取对应的文件<br>
     * @param uri   图片加载地址
     * @param type  图片加载类型，若type为null，则图片将按照以下顺序返回：<br/>
     *              缓存的默认图片、缓存的原图、缓存的迷你型缩略图、缓存的微型缩略图、NULL（无缓存）
     * @return File
     */
    File getCacheFile(String uri, LoaderType type);
}