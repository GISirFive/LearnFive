package com.telchina.pub.construction.base;

/**
 * 基础视图控制
 * Created by GISirFive on 2016-3-21.
 */
public interface ILoadingController {

    /**
     * 显示进度框或正在加载提示
     * @param msgs 显示在屏幕上的提示
     */
    void showLoading(String... msgs);

    /**
     * 隐藏进度框或正在加载提示
     */
    void hideLoading();

}
