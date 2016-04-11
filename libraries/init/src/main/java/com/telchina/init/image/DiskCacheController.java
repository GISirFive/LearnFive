package com.telchina.init.image;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.*;
import com.telchina.pub.image.LoaderType;

import java.io.IOException;

/**
 * Created by GISirFive on 2016-4-4.
 */
class DiskCacheController implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener,
        ImageLoadingProgressListener, DownloadController.DownloadListener {

    private ImageView mImageView = null;

    private LoadingListenerPresenter mListenerPresenter = null;

    private String mUri;

    private LoaderType mLoaderType;

    public DiskCacheController(ImageView imageView, String uri, LoaderType type) {
        mImageView = imageView;
        this.mUri = uri;
        this.mLoaderType = type;
    }

    public void setLoadingListenerPresenter(LoadingListenerPresenter listener) {
        this.mListenerPresenter = listener;
    }

    public void setUri(String uri) {
        this.mUri = uri;
    }

    public void load() {
        if (mLoaderType == LoaderType._DEFAULT || mLoaderType == LoaderType._ORIGIN) {
            ImageLoader.getLoader().displayImage(mUri, mImageView, Utils.getOptions(mLoaderType));
        } else {
            ImageLoader.getLoader().loadImage(mUri, Utils.getStandardImageSize(mLoaderType), Utils.getOptions(mLoaderType), this, this);
        }
    }

    /**
     * Is called when image loading task was started
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    @Override
    public void onLoadingStarted(String imageUri, View view) {
        if (mListenerPresenter != null)
            mListenerPresenter.onLoadingStarted(imageUri, view);
    }

    /**
     * Is called when an error was occurred during image loading
     *
     * @param imageUri   Loading image URI
     * @param view       View for image. Can be <b>null</b>.
     * @param failReason {@linkplain FailReason The reason} why image
     */
    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (mListenerPresenter != null)
            mListenerPresenter.onLoadingFailed(imageUri, view, failReason);
    }

    /**
     * Is called when image is loaded successfully (and displayed in View if one was specified)
     *
     * @param imageUri    Loaded image URI
     * @param view        View for image. Can be <b>null</b>.
     * @param loadedImage Bitmap of loaded and decoded image
     */
    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        DiskCache diskCache = ImageLoader.getLoader().getDiskCache();
        String uri = Utils.getRealURI(imageUri, mLoaderType);
        try {
            long time1 = System.currentTimeMillis();
            boolean b = diskCache.save(uri, loadedImage);
            long time2 = System.currentTimeMillis();
            Log.i("输出", (time2 - time1) + "---" + imageUri);
            if (b) {
                ImageLoader.getLoader().displayImage(uri, mImageView, Utils.getOptions(mLoaderType));
            }
            if (mListenerPresenter != null)
                mListenerPresenter.onLoadingComplete(imageUri, view, loadedImage);
        } catch (IOException e) {
            e.printStackTrace();
            if (mListenerPresenter != null) {
                FailReason reason = new FailReason(FailReason.FailType.IO_ERROR, e);
                mListenerPresenter.onLoadingFailed(imageUri, view, reason);
            }
        }
    }

    /**
     * Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (mListenerPresenter != null)
            mListenerPresenter.onLoadingCancelled(imageUri, view);
    }

    /**
     * Is called when image loading progress changed.
     *
     * @param imageUri Image URI
     * @param view     View for image. Can be <b>null</b>.
     * @param current  Downloaded size in bytes
     * @param total    Total size in bytes
     */
    @Override
    public void onProgressUpdate(String imageUri, View view, int current, int total) {
        if (mListenerPresenter != null)
            mListenerPresenter.onProgressUpdate(imageUri, view, current, total);

    }

    @Override
    public void onDownloadComplete(String cacheUri) {
        setUri(cacheUri);
        load();
    }

    @Override
    public void onDownloadFailed() {

    }
}
