package com.widget.circularprogressbutton;

import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;

public class CPButtonUtils implements Callback{

	private Handler handler = new Handler(this);
	
	private static CPButtonUtils buttonUtils;
	
	private CPButtonUtils() {	}
	
	private static CPButtonUtils getInstance(){
		if(buttonUtils == null)
			buttonUtils = new CPButtonUtils();
		return buttonUtils;
	}
	
	/**
	 * 设置提交按钮的状态
	 * @param CircularProgressButton
	 * @param status
	 *            0-正常, 1-正在加载, 2-加载失败
	 */
	public static void changeStatus(CircularProgressButton button, int status) {
		if (button == null)
			return;
		int progress = 0;
		switch (status) {
		case 0:
			button.setClickable(true);
			break;
		case 1:
			button.setClickable(false);
			progress = 50;
			break;
		case 2:
			button.setClickable(false);
			progress = -1;
			Message msg = new Message();
			msg.obj = button;
			msg.arg1 = 0;
			getInstance().handler.sendMessageDelayed(msg, 800);
			break;
		}
		button.setProgress(progress);
	}

	@Override
	public boolean handleMessage(Message msg) {
		changeStatus((CircularProgressButton)msg.obj, msg.arg1);
		return false;
	}
}
