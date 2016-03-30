package com.telchina.pub.construction.base;

import android.app.Activity;

import com.orhanobut.logger.Logger;

/**
 * Created by GISirFive on 2016-3-21.
 */
public class ILifeRecyclerImp implements ILifeRecycler {

    private Activity mActivity;

    public ILifeRecyclerImp(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResume() {
        Logger.i(getClass().getSimpleName() + ": onResume");
    }

    @Override
    public void onPause() {
        Logger.i(getClass().getSimpleName() + ": onPause");
    }

    @Override
    public void onDestroy() {
        Logger.i(getClass().getSimpleName() + ": onDestroy");
    }
}
