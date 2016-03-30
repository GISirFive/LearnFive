package com.telchina.pub.http;

import org.json.JSONObject;

import com.telchina.pub.utils.RequestUtils;

/**
 * 请求回调接口
 *
 * @author GISirFive
 */
public interface IRequestCallback {

    /***
     * 开始请求
     *
     */
    void onStart(RequestUtils.RequestCode code);

    /**
     * 请求成功，返回请求成果
     *
     * @param response 结果是json形式
     * @author GISirFive
     */
    void onSuccess(RequestUtils.RequestCode code, JSONObject response);

    /**
     * 请求失败
     */
    void onFailure(RequestUtils.RequestCode code, JSONObject errorResponse);

}
