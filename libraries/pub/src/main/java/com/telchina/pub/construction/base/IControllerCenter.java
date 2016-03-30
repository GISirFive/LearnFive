package com.telchina.pub.construction.base;

/**
 * Created by GISirFive on 2016-3-21.
 */
public interface IControllerCenter {

    ILifeRecycler getLifeRecycler();

    ILoadingController getLoading();

    IMessageController getMessager();
}
