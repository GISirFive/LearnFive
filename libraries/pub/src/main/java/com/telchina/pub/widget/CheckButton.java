package com.telchina.pub.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.telchina.pub.R;

/*
<RelativeLayout
    android:layout_width="168dp"
    android:layout_height="28dp"
    android:background="@drawable/bg_layout_2" >
    
    <Button
        android:layout_width="64dp"
        android:layout_height="match_parent"
        android:layout_alignParentLeft="true"
        android:background="@drawable/style_bt_check"
        android:textColor="@color/text_1"
        android:textSize="@dimen/text_14"
        android:text="山地" />
    
    <Button
        android:enabled="false"
        android:layout_width="64dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        android:background="@drawable/style_bt_check"
        android:textColor="@color/text_4"
        android:textSize="@dimen/text_14"
        android:text="公路" />
    
</RelativeLayout>
*/
/**
 * 自定义类似CheckBox的单选按钮处理类
 * @author GISirFive
 */
public class CheckButton extends FrameLayout implements OnClickListener{

	private Button btnLeft;
	
	private Button btnRight;
	
	private int normalColor;
	
	private int checkedColor;
	
	private Animation anim1, anim2;
	
	private OnCheckedChangeListener listener;
	
	public CheckButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		View root = LayoutInflater.from(context).inflate(R.layout.pbl_checkbutton, this);
		if(!isInEditMode()){
		}
		
		init(attrs);
	}
	
	private void init(AttributeSet attrs){
		int type = Animation.RELATIVE_TO_SELF;
		
		anim1 = new ScaleAnimation
				(1.0f, 0.4f, 1.0f, 0.4f, type, 0.5f, type, 0.5f);
		anim1.setDuration(200);
		anim1.setRepeatMode(Animation.REVERSE);
		anim1.setRepeatCount(1);
		anim1.setFillEnabled(true);
		anim1.setFillAfter(true);
		anim1.setInterpolator(new DecelerateInterpolator(1f));
		
		anim2 = new ScaleAnimation
				(1.0f, 0.8f, 1.0f, 0.8f, type, 0.5f, type, 0.5f);
		anim2.setDuration(300);
		anim2.setFillEnabled(true);
		anim2.setFillAfter(true);
		anim2.setInterpolator(new DecelerateInterpolator(1f));
		
		btnLeft = (Button)findViewById(R.id.checkbutton_left);
		btnLeft.setOnClickListener(this);
		btnRight = (Button)findViewById(R.id.checkbutton_right);
		btnRight.setOnClickListener(this);
		
		normalColor = getResources().getColor(R.color.app_text_3);
		checkedColor = getResources().getColor(android.R.color.white);
	}
	
	/**
	 * 选中某一个按钮
	 * @param b true-左边按钮， false-右边按钮
	 */
	public void check(boolean b){
		
		btnLeft.setEnabled(!b);
		btnLeft.setTextColor(!b ? normalColor : checkedColor);
		
		btnRight.setEnabled(b);
		btnRight.setTextColor(b ? normalColor : checkedColor);
		
		if(b)
			startAnimation(btnLeft, btnRight);
		else
			startAnimation(btnRight, btnLeft);
		
		
		if(listener != null)
			listener.onCheckedChanged(b ? btnLeft : btnRight, b);
	}
	
	/**
	 * 设置左侧按钮文字
	 * @param text
	 */
	public void setLeftText(String text){
		btnLeft.setText(text);
	}
	
	/**
	 * 设置右侧按钮文字
	 * @param text
	 */
	public void setRightText(String text){
		btnRight.setText(text);
	}
	
	/**
	 * 获取选中状态
	 * @return true-左侧被选中， false-右侧被选中
	 */
	public boolean getChecked(){
		return btnRight.isEnabled();
	}
	
	/**
	 * 设置选择状态变化监听
	 * @param listener
	 */
	public void setOnCheckChangeListener(OnCheckedChangeListener listener){
		this.listener = listener;
	}
	
	private void startAnimation(View v1, View v2){
		v1.startAnimation(anim1);
		v2.startAnimation(anim2);
	}
	
	/**
	 * 选择变化监听器
	 * @author GISirFive
	 *
	 */
	public interface OnCheckedChangeListener{
		/**
		 * 选择发生变化
		 * @param v 被选中的View
		 * @param b true-左侧被选中， false-右侧被选中
		 */
		void onCheckedChanged(View v, boolean b);
	}

	@Override
	public void onClick(View v) {
		if(v == btnLeft)
			check(true);
		else if(v == btnRight)
			check(false);
	}
}
