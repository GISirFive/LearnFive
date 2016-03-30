package com.telchina.init.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import com.telchina.pub.http.IRequestCallback;
import com.telchina.pub.http.IRequestController;
import com.telchina.pub.http.IRequestParams;
import com.telchina.pub.utils.RequestUtils;

/***
 * @author GISirFive
 */
public class IRequestControllerImp implements IRequestController {

    private static IRequestControllerImp httpClient = null;

    private AsyncHttpClient mAsyncHttpClient = null;

    private IRequestControllerImp() {
        initialize();
    }

    public static IRequestControllerImp getInstance() {
        if (httpClient == null)
            httpClient = new IRequestControllerImp();
        return httpClient;
    }

    private void initialize() {
        mAsyncHttpClient = new AsyncHttpClient();
        mAsyncHttpClient.setTimeout(20000);
        mAsyncHttpClient
                .setUserAgent("Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                        + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
    }

    public AsyncHttpClient getBase() {
        return mAsyncHttpClient;
    }

    @Override
    public void post(RequestUtils.RequestCode code, IRequestParams params,
                     IRequestCallback callback) {
        mAsyncHttpClient.post(RequestUtils.getUrlWithFlag(code),
                (params == null ? null : (RequestParams) params.getParams()),
                getCallbackInstance(code, callback));
    }

    @Override
    public void get(RequestUtils.RequestCode code, IRequestParams params,
                    IRequestCallback callback) {
        mAsyncHttpClient.post(RequestUtils.getUrlWithFlag(code),
                (params == null ? null : (RequestParams) params.getParams()),
                getCallbackInstance(code, callback));
    }

    private static ResponseHandlerInterface getCallbackInstance
            (RequestUtils.RequestCode code, IRequestCallback callback) {
        return new JSONHttpResponseHandler(code, callback);
    }

}
