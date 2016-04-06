/**
 * @since 2015-12-11
 */
package com.telchina.pub.construction.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.telchina.pub.R;
import com.utils.SystemBarTintManager;

/**
 * 沉浸式状态栏
 * @author GISirFive
 * @since 2015-12-11 下午3:55:45
 */
public class TranslucentHelp {

    private Activity mActivity;
    private ITranslucentControl mTranslucentControl;

    /** */
    public TranslucentHelp(ITranslucentControl control) {
        if (control == null)
            return;
        if (!(control instanceof Activity))
            return;

        this.mTranslucentControl = control;
        this.mActivity = (Activity) control;

        translucent();
    }

    /**
     * 设置沉浸式状态栏
     * @author GISirFive
     */
    public void translucent() {
        //版本校验
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
        tintManager.setStatusBarTintEnabled(true);

        int colorRes = mTranslucentControl.getTranslucentColorResource();
        if (colorRes == 0)
            colorRes = R.color.app_theme;
        //设置沉浸的颜色
        tintManager.setStatusBarTintResource(colorRes);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 沉浸式状态栏
     * @author GISirFive
     * @since 2015-12-11 下午4:35:57
     */
    public interface ITranslucentControl {

        /**
         * 是否使用沉浸式状态栏</br>
         * @return false-不使用沉浸式状态栏
         * @author GISirFive
         */
        boolean translucent();

        /**
         * 获取沉浸式状态栏的背景色
         * @return
         * @author GISirFive
         */
        int getTranslucentColorResource();
    }
}
