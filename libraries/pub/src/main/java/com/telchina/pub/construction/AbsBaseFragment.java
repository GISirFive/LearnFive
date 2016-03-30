package com.telchina.pub.construction;

import android.os.Handler;
import android.support.v4.app.Fragment;

import com.telchina.pub.construction.base.IControllerCenter;
import com.telchina.pub.construction.base.ILifeRecycler;
import com.telchina.pub.construction.base.ILifeRecyclerImp;
import com.telchina.pub.construction.base.ILoadingController;
import com.telchina.pub.construction.base.ILoadingControllerImp;
import com.telchina.pub.construction.base.IMessageController;
import com.telchina.pub.construction.base.IMessageControllerImp;


/**
 * Created by GISirFive on 2016-3-21.
 */
public abstract class AbsBaseFragment extends Fragment implements IControllerCenter, Handler.Callback {

    private ILifeRecycler mLifeCycler = null;
    private ILoadingController mViewController = null;
    private IMessageController mMessageController = null;

    private Handler mHandler = new Handler(this);

    /**
     * 获取该类的消息接收器
     */
    public final Handler getHandler() {
        return this.mHandler;
    }

    @Override
    public ILifeRecycler getLifeRecycler() {
        if (mLifeCycler == null)
            mLifeCycler = new ILifeRecyclerImp(getActivity());
        return mLifeCycler;
    }

    @Override
    public ILoadingController getLoading() {
        if (mViewController == null)
            mViewController = new ILoadingControllerImp(getActivity());
        return mViewController;
    }

    @Override
    public IMessageController getMessager() {
        if (mMessageController == null)
            mMessageController = new IMessageControllerImp(getActivity());
        return mMessageController;
    }

    @Override
    public void onResume() {
        if (mLifeCycler != null)
            mLifeCycler.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mLifeCycler != null)
            mLifeCycler.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mLifeCycler != null)
            mLifeCycler.onDestroy();
        super.onDestroy();
    }
}
