package com.telchina.init.http;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import com.telchina.pub.http.IRequestCallback;
import com.telchina.pub.utils.RequestUtils;

public class JSONHttpResponseHandler extends JsonHttpResponseHandler {

    private static final String SUCCESS = "success";

    /**
     * 接口回调
     */
    private IRequestCallback mCallback;

    /**
     * 请求标识
     */
    private RequestUtils.RequestCode mCode;

    public JSONHttpResponseHandler(RequestUtils.RequestCode code, IRequestCallback callback) {
        this.mCallback = callback;
        this.mCode = code;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mCallback != null)
            mCallback.onStart(mCode);
    }

    @Override
    public void onRetry(int retryNo) {
        super.onRetry(retryNo);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        response = responseFilter(statusCode, response);

        if (!response.optBoolean("success", true)) {
            onFailure(statusCode, headers, null, response);
        } else {
            Log.i("输出", "statusCode:" + statusCode + ", header:" + headers
                    + ", response:" + response);
            if (mCallback != null)
                mCallback.onSuccess(mCode, response);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers,
                          Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.i("输出", "statusCode:" + statusCode + ", header:" + headers
                + ", trowable:" + throwable + ", errorResponse:"
                + errorResponse);
        if (mCallback != null)
            mCallback.onFailure(mCode, errorResponse);
    }

    /**
     * 数据过滤器，对返回数据进行异常处理
     *
     * @param response
     * @return
     * @author GISirFive
     */
    private static JSONObject responseFilter(int statusCode, JSONObject response) {
        try {
            if (response == null || response.equals("{}")
                    || response.equals("null")) {
                response = new JSONObject();
                response.put(SUCCESS, true);
            }
            // 若不包含success，则插入false
            if (!response.has(SUCCESS)) {
                response.put("success", true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

}
