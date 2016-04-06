package com.telchina.pub.construction;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.telchina.pub.R;
import com.telchina.pub.construction.base.IControllerCenter;
import com.telchina.pub.construction.base.ILifeRecycler;
import com.telchina.pub.construction.base.ILifeRecyclerImp;
import com.telchina.pub.construction.base.ILoadingController;
import com.telchina.pub.construction.base.ILoadingControllerImp;
import com.telchina.pub.construction.base.IMessageController;
import com.telchina.pub.construction.base.IMessageControllerImp;
import com.telchina.pub.construction.base.ToolBarHelper;
import com.telchina.pub.construction.base.ToolbarParam;
import com.telchina.pub.construction.base.TranslucentHelp;


/**
 * Created by GISirFive on 2016-3-21.
 */
public abstract class AbsBaseActivity extends AppCompatActivity implements IControllerCenter, Handler.Callback,
        TranslucentHelp.ITranslucentControl, ToolBarHelper.IToolBarController{

    private ILifeRecycler mLifeCycler = null;
    private ILoadingController mViewController = null;
    private IMessageController mMessageController = null;

    private ToolBarHelper mToolBarHelper;

    protected Toolbar mToolbar;

    private Handler mHandler = new Handler(this);

    /**
     * 获取该类的消息接收器
     */
    public final Handler getHandler() {
        return this.mHandler;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(translucent())
            new TranslucentHelp(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this, layoutResID, this);
        setContentView(mToolBarHelper.getContentView());
        mToolbar = mToolBarHelper.getToolBar();
        // 把toolbar设置到Activity中
        if(mToolbar != null)
            setSupportActionBar(mToolbar);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    /**
     * 自定义Toolbar相关的操作<br>
     * <b>自定义Toolbar中的布局:</b><br>
     * getLayoutInflater().inflate(R.layout.pub_toobar_1, toolbar);
     *
     * @param toolbar Toolbar的实例
     * @param param   设置Toolbar的一些属性
     * @return <b>true</b>-使用Toolbar &nbsp <b>false</b>-不使用Toolbar(默认)
     * @author GISirFive
     */
    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParam param) {
        return false;
    }

    /**
     * 是否使用沉浸式状态栏</br>
     *
     * @return false-不使用沉浸式状态栏
     * @author GISirFive
     */
    @Override
    public boolean translucent() {
        return true;
    }

    /**
     * 获取沉浸式状态栏的背景色
     *
     * @return
     * @author GISirFive
     */
    @Override
    public int getTranslucentColorResource() {
        return R.color.app_colorPrimaryDark;
    }
}
