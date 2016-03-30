package com.telchina.pub.image;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by GISirFive on 2016-3-28.
 */
public interface ImageLoadingListener {
    /**
     * Is called when an error was occurred during image loading
     *
     * @param imageUri   Loading image URI
     * @param view       View for image. Can be <b>null</b>.
     */
    void onLoadingFailed(String imageUri, View view);

    /**
     * Is called when image is loaded successfully (and displayed in View if one was specified)
     *
     * @param imageUri    Loaded image URI
     * @param view        View for image. Can be <b>null</b>.
     * @param result Bitmap of loaded and decoded image
     */
    void onLoadingComplete(String imageUri, View view, Bitmap result);
}
