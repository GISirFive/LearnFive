package com.telchina.pub.construction;

import com.telchina.pub.construction.base.IControllerCenter;
import com.telchina.pub.construction.base.IModelCallback;
import com.telchina.pub.utils.RequestUtils;

/**
 * Created by GISirFive on 2016-3-18.
 */
public abstract class AbsBasePresenter implements IModelCallback {

    private IControllerCenter mCenterController = null;

    public AbsBasePresenter(IControllerCenter controller) {
        this.mCenterController = controller;
    }

    /**
     * 获取视图控制中心的接口
     * @return
     */
    public IControllerCenter getController(){
        return mCenterController;
    }

    /***
     * 开始请求
     *
     * @param code
     */
    @Override
    public void onStart(RequestUtils.RequestCode code) {}

}
