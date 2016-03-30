package com.telchina.pub.construction;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

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
public abstract class AbsBaseActivity extends AppCompatActivity implements IControllerCenter, Handler.Callback {

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
            mLifeCycler = new ILifeRecyclerImp(this);
        return mLifeCycler;
    }

    @Override
    public ILoadingController getLoading() {
        if (mViewController == null)
            mViewController = new ILoadingControllerImp(this);
        return mViewController;
    }

    @Override
    public IMessageController getMessager() {
        if(mMessageController == null)
            mMessageController = new IMessageControllerImp(this);
        return mMessageController;
    }

    @Override
    protected void onResume() {
        if (mLifeCycler != null)
            mLifeCycler.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mLifeCycler != null)
            mLifeCycler.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLifeCycler != null)
            mLifeCycler.onDestroy();
        super.onDestroy();
    }
}
