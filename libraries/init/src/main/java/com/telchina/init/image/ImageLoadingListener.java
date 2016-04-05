package com.telchina.init.image;

import android.view.View;

import com.telchina.pub.image.IBaseImageLoadingListener;

/**
 * 图片加载监听
 * Created by GISirFive on 2016-4-1.
 */
public interface ImageLoadingListener extends IBaseImageLoadingListener {

    void onLoadingStarted(String imageUri, View view);

    void onLoadingCancelled(String imageUri, View view);

    /**
     * Is called when image loading progress changed.
     *
     * @param imageUri Image URI
     * @param view     View for image. Can be <b>null</b>.
     * @param percent   下载完成的百分比 = (Downloaded size)/(Total size)
     */
    void onProgressUpdate(String imageUri, View view, float percent);
}
