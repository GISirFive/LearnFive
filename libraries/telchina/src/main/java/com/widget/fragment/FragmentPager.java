package com.widget.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 自定义FragmentPager类，继承自ViewPager</br>
 * 用来配合Fragment完成多屏滑动效果
 * @author GISirFive
 *
 */
public class FragmentPager extends ViewPager{

	public FragmentPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
	}
	
}
