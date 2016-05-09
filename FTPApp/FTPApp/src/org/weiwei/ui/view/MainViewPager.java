package org.weiwei.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MainViewPager extends ViewPager{

	public MainViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MainViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
//		Log.i("TouchEvent", "dispatchTouchEvent  MainViewPager");
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
//		Log.i("TouchEvent", "onTouchEvent  MainViewPager");
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

//		int i = getCurrentItem();
//		Log.i("TouchEvent", "onInterceptTouchEvent  MainViewPager"+" "+i);
//		if(i==3){
//		return false;
//		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * ViewPager里嵌套HorizontalScrollView的焦点冲突解决方法
	 */
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		// TODO Auto-generated method stub
		if(v instanceof HorizontalScrollView){
			return true;
		}
		return super.canScroll(v, checkV, dx, x, y);
	}

	
	
}
