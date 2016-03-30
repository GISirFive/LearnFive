package com.telchina.pub.http;


import com.telchina.pub.utils.RequestUtils;

public interface IRequest {
    /***
     * POST方式从服务器获取数据/向服务器提交数据
     *
     * @param code
     * @param params
     */
    public void post(RequestUtils.RequestCode code, IRequestParams params);

    /***
     * GET方式从服务器获取数据/向服务器提交数据
     *
     * @param code
     * @param params
     */
    public void get(RequestUtils.RequestCode code, IRequestParams params);
}
