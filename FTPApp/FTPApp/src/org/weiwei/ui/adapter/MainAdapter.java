package org.weiwei.ui.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter{

	private List<Fragment> mDatas;
	
	public MainAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	
	public MainAdapter(FragmentManager fm,List<Fragment> datas){
		this(fm);
		mDatas = datas;
		
	}
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

}
