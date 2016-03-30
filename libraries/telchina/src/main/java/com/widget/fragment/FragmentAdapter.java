package com.widget.fragment;

import java.util.ArrayList;
import java.util.List;

import com.orhanobut.logger.Logger;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{

	private List<Fragment> fragmentList;
	
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		if(fm == null)
			throw new NullPointerException("The FragmentManager is null!");
		if(fragmentList == null)
			fragmentList = new ArrayList<Fragment>();
	}
	
	public FragmentAdapter(FragmentManager fm, List<Fragment> viewList) {
		super(fm);
		if(fm == null)
			throw new NullPointerException("The FragmentManager is null!");
		if(viewList == null)
			throw new NullPointerException("The List of Fragment is null or empty!");
		this.fragmentList = viewList;
	}

	public FragmentAdapter(FragmentManager fm, Fragment... view) {
		super(fm);
		if(fm == null)
			throw new NullPointerException("The FragmentManager is null!");
		if(fragmentList == null)
			fragmentList = new ArrayList<Fragment>();
		for (Fragment absFragment : view) {
			addView(absFragment);
		}
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = fragmentList.get(position);
		Logger.i("FragmentStatePagerAdapter: "+fragment.getTag());
		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Fragment fragment = fragmentList.get(position);
		String title = "";
		if(fragment instanceof TitleProvider){
			title = ((TitleProvider) fragment).getFragmentTitle() + "";
		}
		else{
			title = fragment.getTag() + "";
		}
		return title;
	}
	
	@Override
	public int getCount() {
		return fragmentList.size();
	}
	
	/**
	 * 将一个页面添加到Adapter中
	 * @param AbsFragment - view
	 * @return 添加成功- true
	 */
	public boolean addView(Fragment view){
		if(fragmentList.contains(view))
			return false;
		fragmentList.add(view);
		notifyDataSetChanged();
		return true;
	}
	
	/**
	 * 将多个页面添加到Adapter中
	 * @param AbsFragment - view
	 * @return 添加成功- true
	 */
	public boolean addView(Fragment... views){
		for (Fragment fragment : views) {
			if(fragmentList.contains(fragment))
				return false;
			fragmentList.add(fragment);
		}
		notifyDataSetChanged();
		return true;
	}

}
