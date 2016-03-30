package com.telchina.pub.construction.base;

import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by GISirFive on 2016-3-21.
 */
public class ILoadingControllerImp implements ILoadingController {

    private Context mContext;

    public ILoadingControllerImp(Context context) {
        this.mContext = context;
    }

    /**
     * 显示进度框或正在加载提示
     *
     * @param msgs 显示在屏幕上的提示
     */
    @Override
    public void showLoading(String... msgs) {
        Logger.i("showLoading");
    }

    /**
     * 隐藏进度框或正在加载提示
     */
    @Override
    public void hideLoading() {
        Logger.i("hideLoading");
    }
}
