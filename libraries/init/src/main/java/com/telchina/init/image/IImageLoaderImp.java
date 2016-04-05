package com.telchina.init.image;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.image.IBaseImageLoadingListener;
import com.telchina.pub.image.LoaderType;
import com.telchina.pub.utils.ConfigUtils;

import java.io.File;

/**
 * Created by GISirFive on 2016-3-30.
 */
class IImageLoaderImp implements IImageLoader {

    /**
     * 开源库，加载图片的核心类
     **/
    private static com.nostra13.universalimageloader.core.ImageLoader BaseLoader = null;

    public IImageLoaderImp(Context context) {
        init(context);
    }

    private void init(Context context) {
        DisplayOptions.init();
        File cacheDir = StorageUtils.getIndividualCacheDirectory(context,
                ConfigUtils.getFromConfig(ConfigUtils.KEY.cachePublic));
        UnlimitedDiskCache diskCache = new UnlimitedDiskCache(cacheDir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY)
                // .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个
                // 内存缓存5M
                .memoryCacheSize(5 * 1024 * 1024)
                // 缓存到内存中的图片的最大长宽
                .memoryCacheExtraOptions(512, 512)
                .diskCache(diskCache)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 20 * 1000))
                .defaultDisplayImageOptions(DisplayOptions._default)
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
        display(uri, imageView, LoaderType._DEFAULT);
    }

    /**
     * 加载迷你型缩略图{@link LoaderType#_MINI}，并显示在imageView中
     *
     * @param uri
     * @param imageView
     */
    @Override
    public void displayMini(String uri, ImageView imageView) {
        display(uri, imageView, LoaderType._MINI);
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
        display(uri, imageView, type, null);
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
        LoadingListenerPresenter listenerPresenter = null;
        if (listener != null)
            listenerPresenter = new LoadingListenerPresenter(listener);
        uri = Utils.translateURI(uri);
        String realUri = Utils.getRealURI(uri, type);

        //要获取的图片是否缓存于硬盘中
        if (Utils.isCacheExist(uri, type)) {
            //有缓存，直接加载
            BaseLoader.displayImage(realUri, imageView,
                    Utils.getOptions(type), listenerPresenter, listenerPresenter);
        } else {
            //无缓存，加载原图
            DiskCacheController diskController = new DiskCacheController(imageView, uri, type);
            diskController.setLoadingListenerPresenter(listenerPresenter);

            if (Utils.isLocalURI(uri)) {//当前uri指向本地图片
                diskController.load();
            } else {//从网上下载图片
                //将网络图片缓存到硬盘
                DownloadController downloadController = new DownloadController(uri);
                downloadController.setOnDownloadCallbackListener(diskController);
                downloadController.download();
            }

        }

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
        if (listener == null)
            throw new NullPointerException("IBaseImageLoadingListener 不能为空");
        LoadingListenerPresenter loadingListener = new LoadingListenerPresenter(listener);
        uri = Utils.translateURI(uri);
        String typeUri = Utils.getRealURI(uri, type);
        BaseLoader.loadImage(typeUri, Utils.getOptions(type), loadingListener);
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
        uri = Utils.translateURI(uri);
        if (Utils.isCacheExist(uri, LoaderType._DEFAULT))
            return BaseLoader.getDiskCache().get(Utils.getRealURI(uri, LoaderType._DEFAULT));
        return null;
    }


}
