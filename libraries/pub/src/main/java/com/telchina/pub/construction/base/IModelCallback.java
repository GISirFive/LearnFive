package com.telchina.pub.construction.base;

import android.os.Bundle;

import com.telchina.pub.utils.RequestUtils;

/**
 * Created by GISirFive on 2016-3-22.
 */
public interface IModelCallback {
    /***
     * 开始请求
     *
     */
    void onStart(RequestUtils.RequestCode code);

    /**
     * 请求成功，返回请求成果
     * @param code
     * @param bundle
     */
    void onSuccess(RequestUtils.RequestCode code, Bundle bundle);

    /**
     * 请求失败
     * @param code
     * @param bundle
     */
    void onFailure(RequestUtils.RequestCode code, Bundle bundle);
}
