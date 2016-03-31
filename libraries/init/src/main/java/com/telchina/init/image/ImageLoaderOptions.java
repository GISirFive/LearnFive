package com.telchina.init.image;

import android.graphics.Bitmap;
import android.view.animation.Animation;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 图片展示方式的封装类
 *
 * @author GISirFive
 * @since 2016-1-23 上午11:15:56
 */
public class ImageLoaderOptions {

    public static DisplayImageOptions _default = null;
    public static DisplayImageOptions _highlevel = null;
    public static DisplayImageOptions _icon = null;
    public static DisplayImageOptions _mini = null;
    public static DisplayImageOptions _micro = null;

    public static void initInstance() {
        _default = getDetaultOptions().build();
        _highlevel = getHighLevelOptions().build();
        _icon = getIconOptions().build();
        _mini = getMiniOptions().build();
        _micro = getMicroOptions().build();
    }


    /**默认，SD卡缓存，无内存缓存*/
    public static Builder getDetaultOptions() {
        Builder builder = new Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_4444)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**高清大图，SD卡缓存，无内存缓存*/
    public static Builder getHighLevelOptions() {
        Builder builder = new Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**加载图标*/
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
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**加载迷你型*/
    public static Builder getMiniOptions() {
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
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_4444)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /** 微型缩略图（比迷你型更小的缩略图）*/
    public static Builder getMicroOptions() {
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
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_4444)//RGB_565模式消耗的内存比ARGB_8888模式少两倍
                .displayer(new FadeInBitmapDisplayer(300));// 淡入
        return builder;
    }

    /**
     * 缩略图_禁用本地缓存
     */
/*    public static Builder getThumbnailUnCacheOptions() {
        Builder builder = getThumbnailOptions().cacheOnDisk(false);
        return builder;
    }*/
}
