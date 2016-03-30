package com.telchina.init.http;

import org.json.JSONObject;

import com.telchina.pub.http.IRequestCallback;
import com.telchina.pub.utils.RequestUtils;


public class IRequestCallbackImpl implements IRequestCallback {

    private IRequestCallback mCallback;

    public IRequestCallbackImpl(IRequestCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onStart(RequestUtils.RequestCode code) {
        if (mCallback != null)
            mCallback.onStart(code);
    }

    @Override
    public void onSuccess(RequestUtils.RequestCode code, JSONObject response) {
        if (mCallback != null)
            mCallback.onSuccess(code, response);
    }

    @Override
    public void onFailure(RequestUtils.RequestCode code, JSONObject errorResponse) {
        if (mCallback != null)
            mCallback.onFailure(code, errorResponse);
    }

}
