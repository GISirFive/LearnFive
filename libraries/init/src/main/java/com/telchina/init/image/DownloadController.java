package com.telchina.init.image;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * Created by GISirFive on 2016-4-4.
 */
class DownloadController implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener, ImageLoadingProgressListener {

    private static DisplayImageOptions _download = null;

    private String mUri;
    private ImageLoadingListener mImageLoadingListener;
    private ImageLoadingProgressListener mProgressListener;
    private DownloadListener mDownloadListener;

    public DownloadController(String uri) {
        this.mUri = uri;
        if (_download == null)
            _download = DisplayOptions.getHighLevelOptions().cacheOnDisk(true).build();
    }

    public void download() {
        ImageLoader.getLoader().loadImage(mUri, null, _download,
                mImageLoadingListener, mProgressListener);
    }

    public void setImageLoadingListener(ImageLoadingListener listener) {
        this.mImageLoadingListener = listener;
    }

    public void setProgressListener(ImageLoadingProgressListener listener) {
        this.mProgressListener = listener;
    }

    public void setOnDownloadCallbackListener(DownloadListener listener) {
        this.mDownloadListener = listener;
    }

    /**
     * Is called when image loading task was started
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    @Override
    public void onLoadingStarted(String imageUri, View view) {
        if (mImageLoadingListener != null)
            mImageLoadingListener.onLoadingStarted(imageUri, view);
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
        if (mImageLoadingListener != null)
            mImageLoadingListener.onLoadingFailed(imageUri, view, failReason);
        if (mDownloadListener != null)
            mDownloadListener.onDownloadFailed();
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
//        if(mImageLoadingListener != null)
//            mImageLoadingListener.onLoadingComplete(imageUri, view, loadedImage);
        if (mDownloadListener != null)
            mDownloadListener.onDownloadComplete(imageUri);
    }

    /**
     * Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (mImageLoadingListener != null)
            mImageLoadingListener.onLoadingCancelled(imageUri, view);
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
        if (mProgressListener != null)
            mProgressListener.onProgressUpdate(imageUri, view, current, total);

    }

    interface DownloadListener {

        /**
         *
         * @param cacheUri  下载的文件保存的地址
         */
        void onDownloadComplete(String cacheUri);

        void onDownloadFailed();
    }
}
