package com.telchina.init.image;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.telchina.pub.image.LoaderType;

/**
 * 图片加载方式的配置项
 *
 * @author GISirFive
 * @since 2016-1-23 上午11:15:56
 */
public class DisplayOptions {

    public static DisplayImageOptions _default = null;
    public static DisplayImageOptions _highLevel = null;
    public static DisplayImageOptions _icon = null;
    public static DisplayImageOptions _thumbnail = null;

    public static void init() {
        _default = getDefaultOptions().build();
        _highLevel = getHighLevelOptions().build();
        _icon = getIconOptions().build();
        _thumbnail = getThumbnailOptions().build();
    }


    /**
     * 默认，SD卡缓存，无内存缓存
     */
    public static Builder getDefaultOptions() {
        Builder builder = new Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**
     * 高清大图，SD卡缓存，无内存缓存
     */
    public static Builder getHighLevelOptions() {
        Builder builder = new Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.ARGB_8888)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**
     * 加载图标
     */
    public static Builder getIconOptions() {
        Builder builder = new Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(com.telchina.R.drawable.url_image_loading)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(com.telchina.R.drawable.url_image_failed)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(com.telchina.R.drawable.url_image_failed)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**
     * 加载迷你型
     */
    public static Builder getThumbnailOptions() {
        Builder builder = new Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(com.telchina.R.drawable.url_image_loading)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(com.telchina.R.drawable.url_image_failed)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(com.telchina.R.drawable.url_image_failed)
                .resetViewBeforeLoading(true)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.ARGB_4444)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

}
