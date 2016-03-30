package com.telchina.pub.construction;


import android.os.Bundle;

import org.json.JSONObject;

import com.telchina.pub.application.BaseApp;
import com.telchina.pub.construction.base.IModelCallback;
import com.telchina.pub.http.IRequest;
import com.telchina.pub.http.IRequestCallback;
import com.telchina.pub.http.IRequestParams;
import com.telchina.pub.utils.RequestUtils;

/**
 * Created by GISirFive on 2016-3-18.
 */
public abstract class AbsBaseModel implements IRequest, IRequestCallback {

    protected IModelCallback mCallback = null;

    public AbsBaseModel(IModelCallback callback) {
        this.mCallback = callback;
    }

    /***
     * POST方式从服务器获取数据/向服务器提交数据
     *
     * @param code
     * @param params
     */
    @Override
    public final void post(RequestUtils.RequestCode code, IRequestParams params) {
        BaseApp.HttpClient.post(code, params, this);
    }

    /***
     * GET方式从服务器获取数据/向服务器提交数据
     *
     * @param code
     * @param params
     */
    @Override
    public final void get(RequestUtils.RequestCode code, IRequestParams params) {
        BaseApp.HttpClient.post(code, params, this);
    }

    @Override
    public void onStart(RequestUtils.RequestCode code) {

    }

    /**
     * 请求成功，返回请求成果
     *
     * @param code
     * @param response 结果是json形式
     * @author GISirFive
     */
    @Override
    public final void onSuccess(RequestUtils.RequestCode code, JSONObject response) {
        Bundle bundle = onRequestSuccess(code, response);
        mCallback.onSuccess(code, bundle);
    }

    /**
     * 请求失败
     *
     * @param code
     * @param errorResponse
     */
    @Override
    public final void onFailure(RequestUtils.RequestCode code, JSONObject errorResponse) {
        Bundle bundle = onRequestSuccess(code, errorResponse);
        mCallback.onSuccess(code, bundle);
    }

    /**
     * 请求成功，返回请求成果
     * @param code
     * @param response 结果是json形式
     * @return 返回结果封装成
     */
    protected abstract Bundle onRequestSuccess(RequestUtils.RequestCode code, JSONObject response);

    /**
     * 请求失败
     *
     * @param code
     * @param response 结果是json形式
     * @return 返回结果封装成
     */
    protected abstract Bundle onRequestFailure(RequestUtils.RequestCode code, JSONObject response);

}
