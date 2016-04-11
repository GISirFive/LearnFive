package com.telchina.init.glide;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.telchina.pub.image.IBaseImageLoadingListener;
import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.image.LoaderType;

import java.io.File;

/**
 * Created by GISirFive on 2016-3-30.
 */
public class IImageLoaderImp implements IImageLoader {

    private Context mContext;

    /**
     * 开源库，加载图片的核心类
     **/
    private static com.nostra13.universalimageloader.core.ImageLoader BaseLoader = null;

    public IImageLoaderImp(Context context) {
        mContext = context;
        init(context);
        Log.i("输出", Glide.getPhotoCacheDir(context).getAbsolutePath() + "");
    }

    private void init(Context context) {
    }

    /**
     * 获取图片显示核心类
     *
     * @return
     */
    @Override
    public Glide getLoader() {
        return Glide.get(mContext);
    }

    /**
     * 以默认方式加载图片{@link LoaderType#_DEFAULT}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    @Override
    public void display(String uri, ImageView imageView) {
        Glide.with(mContext).load(uri).into(imageView);
    }

    /**
     * 加载迷你型缩略图{@link LoaderType#_MINI}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    @Override
    public void displayMini(String uri, ImageView imageView) {
        Glide.with(mContext).load(uri).into(imageView);
    }

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @param type
     */
    @Override
    public void display(String uri, ImageView imageView, LoaderType type) {
        Glide.with(mContext).load(uri).into(imageView);
    }

    /**
     * 自定义加载方式{@link LoaderType}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @param type
     * @param listener
     */
    @Override
    public void display(String uri, ImageView imageView, LoaderType type, IBaseImageLoadingListener listener) {
        Glide.with(mContext).load(uri).into(imageView);
    }

    /**
     * 加载图片，通过接口返回加载结果
     *
     * @param uri
     * @param type
     * @param listener
     */
    @Override
    public void load(String uri, LoaderType type, IBaseImageLoadingListener listener) {

    }

    /**
     * 根据图片加载地址、图片加载类型，获取对应的文件<br>
     *
     * @param uri  图片加载地址
     * @param type 图片加载类型，若type为null，则图片将按照以下顺序返回：<br/>
     *             缓存的默认图片、缓存的原图、缓存的迷你型缩略图、缓存的微型缩略图、NULL（无缓存）
     * @return File
     */
    @Override
    public File getCacheFile(String uri, LoaderType type) {
        return null;
    }
}
