package com.telchina.init.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.image.ImageLoadingListener;
import com.telchina.pub.image.LoaderType;
import com.telchina.pub.utils.Config;
import com.telchina.pub.utils.ConfigUtils;

import java.io.File;

/**
 * Created by GISirFive on 2016-3-30.
 */
public class ImageLoader implements IImageLoader {

    /**
     * 开源库，加载图片的核心类
     **/
    private static com.nostra13.universalimageloader.core.ImageLoader BaseLoader = null;

    public ImageLoader(Context context) {
        init(context);
    }

    private void init(Context context) {
        ImageLoaderOptions.initInstance();
        File cacheDir = StorageUtils.getIndividualCacheDirectory(context,
                /*ConfigUtils.getFromConfig(ConfigUtils.KEY.cachePublic)*/
                //Modify by zh
                ConfigUtils.cachePublic);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY)
                // .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个
                // 内存缓存5M
                .memoryCacheSize(5 * 1024 * 1024)
                // 缓存到内存中的图片的最大长宽
                .memoryCacheExtraOptions(512, 512)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 20 * 1000))
                .defaultDisplayImageOptions(ImageLoaderOptions._default)
                .writeDebugLogs()// 打印日志
                .build();
//        L.writeLogs(false);
        BaseLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        BaseLoader.init(config);
    }

    /**
     * 获取图片显示核心类
     *
     * @return
     * @author GISirFive
     */
    @Override
    public com.nostra13.universalimageloader.core.ImageLoader getLoader() {
        return BaseLoader;
    }

    /**
     * 以默认方式加载图片{@link LoaderType#_DEFAULT}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @author GISirFive
     */
    @Override
    public void display(String uri, ImageView imageView) {
        uri = getRealURI(uri);
        BaseLoader.displayImage(uri, imageView);
    }

    /**
     * 加载迷你型缩略图{@link LoaderType#_MINI}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    @Override
    public void displayMini(String uri, ImageView imageView) {
        BaseLoader.displayImage(uri, imageView, ImageLoaderOptions._mini);
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

    }

    /**
     * 公共图片显示，以默认方式加载图片{@link LoaderType#_DEFAULT}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     * @param listener
     */
    @Override
    public void display(String uri, ImageView imageView, ImageLoadingListener listener) {

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
    public void display(String uri, ImageView imageView, LoaderType type, ImageLoadingListener listener) {

    }

    /**
     * 加载图片，通过接口返回加载结果
     *
     * @param uri
     * @param type
     * @param listener
     */
    @Override
    public void load(String uri, LoaderType type, ImageLoadingListener listener) {

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
     * @return
     * @author GISirFive
     */
    @Override
    public File getCacheFile(String uri) {
        return null;
    }

    /**
     * 处理URI
     *
     * @param uri
     * @return
     * @author GISirFive
     */
    private String getRealURI(String uri) {
        if (TextUtils.isEmpty(uri) || uri.trim().isEmpty()) {
            throw new NullPointerException("传入的图片加载路径为空！");
        }
        if (!uri.contains("http://") && !uri.contains("file://")
                && !uri.contains("drawable://") && !uri.contains("assets://")
                && !uri.contains("content://"))
//            return ConfigUtils.getFromConfig(ConfigUtils.KEY.server) + uri;
            return ConfigUtils.server + uri;
        return uri;
    }
}
