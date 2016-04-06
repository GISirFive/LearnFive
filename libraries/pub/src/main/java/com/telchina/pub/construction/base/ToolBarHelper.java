package com.telchina.pub.construction.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telchina.pub.R;

/**
 * Activity和Toolbar的关联类
 *
 * @author GISirFive
 */
public class ToolBarHelper {

    /**
     * 上下文，创建view的时候需要用到
     */
    private Context mContext;

    /**
     * base view
     */
    private FrameLayout mContentView;

    /**
     * 用户定义的view
     */
    private View mUserView;

    /**
     * toolbar
     */
    private Toolbar mToolBar;
    /**
     * toolbar的布局文件
     */
    private View mToolbarGroup;

    /* 视图构造器 */
    private LayoutInflater mInflater;

    private IToolBarController mController;

    private ToolbarParam mToolbarParam;

    /**
     * 两个属性 1、toolbar是否悬浮在窗口之上 2、toolbar的高度获取
     */
    private static int[] ATTRS = {R.attr.windowActionBarOverlay,
            R.attr.actionBarSize};

    public ToolBarHelper(Context context, int layoutId, IToolBarController controller) {
        this.mContext = context;
        this.mController = controller;
        mInflater = LayoutInflater.from(mContext);

        initToolbarParam();
        /* 初始化整个内容 */
        initContentView();

        mToolbarGroup = mInflater.inflate(R.layout.toolbar, null);
        Toolbar toolbar = (Toolbar) mToolbarGroup.findViewById(R.id.id_tool_bar);
        if (mController != null) {
            boolean b = mController.onToolbarConfiguration(toolbar, mToolbarParam);
            if (b)
                mToolBar = toolbar;
        }

		/* 初始化用户定义的布局 */
        initUserView(layoutId);
		/* 初始化toolbar */
        initToolBar();
    }

    private void initToolbarParam() {
        mToolbarParam = new ToolbarParam();
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(
                ATTRS);
		/* 获取主题中定义的悬浮标志 */
        mToolbarParam.overlay = typedArray.getBoolean(0, false);
		/* 获取主题中定义的toolbar的高度 */
        mToolbarParam.toolBarSize = (int) typedArray.getDimension(
                1,
                (int) mContext.getResources().getDimension(
                        R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
    }

    private void initContentView() {
		/* 直接创建一个帧布局，作为视图容器的父容器 */
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        // 适应沉浸式状态栏，若不设置，应用的UI会顶上去，顶进system UI
        mContentView.setFitsSystemWindows(true);
        mContentView.setLayoutParams(params);
    }

    private void initToolBar() {
        if (mToolBar == null)
            return;
        mContentView.addView(mToolbarGroup);
        if (mToolbarParam.colorResId != 0)
            mToolBar.setBackgroundResource(mToolbarParam.colorResId);
//        	mToolbarParam.colorResId = R.color.app_theme;
    }

    private void initUserView(int id) {
        mUserView = mInflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
		/* 如果是悬浮状态，则不需要设置间距 */
        params.topMargin = (mToolBar == null || mToolbarParam.overlay) ? 0
                : mToolbarParam.toolBarSize;
        mContentView.addView(mUserView, params);

    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    /**
     * toolbar操作接口
     *
     * @author GISirFive
     * @since 2016-1-21 上午8:55:53
     */
    public interface IToolBarController {

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
        public boolean onToolbarConfiguration(Toolbar toolbar,
                                              ToolbarParam param);

    }

}
