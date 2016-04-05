package com.telchina.init.image;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.telchina.pub.image.FailedReason;
import com.telchina.pub.image.IBaseImageLoadingListener;

/**
 * Created by GISirFive on 2016-4-1.
 */
class LoadingListenerPresenter implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener, ImageLoadingProgressListener {

    private IBaseImageLoadingListener mBaseLoadingListener = null;

    public LoadingListenerPresenter(IBaseImageLoadingListener listener) {
        this.mBaseLoadingListener = listener;
    }

    /**
     * Is called when image loading task was started
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    @Override
    public void onLoadingStarted(String imageUri, View view) {
        if(mBaseLoadingListener == null)
            return;
        if(mBaseLoadingListener instanceof ImageLoadingListener)
            ((ImageLoadingListener)mBaseLoadingListener).onLoadingStarted(imageUri, view);
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
        if(mBaseLoadingListener == null)
            return;
        FailedReason reason = null;
        if(failReason != null){
            reason = Utils.getFailedReason(failReason);
        }
        mBaseLoadingListener.onLoadingFailed(imageUri, view, reason);
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
        if(mBaseLoadingListener == null)
            return;
        mBaseLoadingListener.onLoadingComplete(imageUri, view, loadedImage);
    }

    /**
     * Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if(mBaseLoadingListener == null)
            return;
        if(mBaseLoadingListener instanceof ImageLoadingListener)
            ((ImageLoadingListener)mBaseLoadingListener).onLoadingCancelled(imageUri, view);
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
        if(mBaseLoadingListener == null)
            return;
        if(mBaseLoadingListener instanceof ImageLoadingListener){
            float percent = current/(float)total;
            ((ImageLoadingListener)mBaseLoadingListener).onProgressUpdate(imageUri, view, percent);
        }
    }
}
