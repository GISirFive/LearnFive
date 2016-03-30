package com.widget.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/***
 * Fragment抽象类，程序中所有使用Fragment编写的类都应该继承此类
 * @author GISirFive 2014-12-18 13:29:15
 *
 */
public abstract class AbsFragment extends Fragment implements TitleProvider, 
	Callback, IHandleMessage{
	
	/** Fragment的标题 */
	private String mTitle = "";
	
	/** fragment的根布局 */
	private View mRoot = null;

	private Handler mHandler = new Handler(this);
    
	public AbsFragment() {	}
	
	public AbsFragment(String title) {
		this.setTitle(title);
	}
	
	@Override
	public String getFragmentTitle() {
		return mTitle;
	}
	
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRoot = this.createView(inflater, container, savedInstanceState);
        return mRoot;
    }
    
    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize(mRoot);
    }
    
    /**
     * 显示消息提示
     * @param msg 消息内容
     * @param interval 该消息提示在屏幕上停留的时间(毫秒)
     */
    public final void showToast(String msg, int interval){
    	if(TextUtils.isEmpty(msg))
    		msg = msg + "";
    	if(interval < 0)
    		return ;
    	Toast.makeText(getActivity(), msg, interval).show();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
    
    @Override
    public void sendMessage(int what) {
    	mHandler.sendEmptyMessage(what);
    }
    
    @Override
    public void sendMessage(Message message) {
    	mHandler.sendMessage(message);
    }
    
    @Override
    public void sendMessageDelayed(int what, long delayMillis) {
    	mHandler.sendEmptyMessageDelayed(what, delayMillis);
    }
    
    @Override
    public void sendMessageDelayed(Message message, long delayMillis) {
    	mHandler.sendMessageDelayed(message, delayMillis);
    }
    
	/** 设置标题 */
	protected void setTitle(String title) {
		this.mTitle = title;
	}
	
    /** 获取该fragment的根布局 */
    protected View getRootView(){
    	return mRoot;
    }
	
	 /**
     * 为该Fragment绑定View<br>
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中执行<br>
     * 该方法执行后，会立即执行{@link #initialize(View)}方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    
    /**
     * 初始化Fragment相关操作<br>
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中执行<br>
     * 在执行此方法前，程序会先执行{@link #createView(LayoutInflater, ViewGroup, Bundle)}
     * @param root 该Fragment绑定的View
     */
    protected abstract void initialize(View root);

	/** 获取该类的消息接收器 */
    public final Handler getHandler(){
		return this.mHandler;
	}
	
}
