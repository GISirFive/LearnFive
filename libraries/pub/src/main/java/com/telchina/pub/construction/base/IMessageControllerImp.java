package com.telchina.pub.construction.base;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created by GISirFive on 2016-3-22.
 */
public class IMessageControllerImp implements IMessageController{

    private Context mContext;

    public IMessageControllerImp(Context context) {
        this.mContext = context;
    }

    @Override
    public void showToast(String content, int duration) {
        Toast.makeText(mContext, content, duration).show();
        Logger.i("showToast--->" + content);
    }

    @Override
    public void showMessageDialog(String content) {
        Logger.i("showMessageDialog--->" + content);
    }
}
