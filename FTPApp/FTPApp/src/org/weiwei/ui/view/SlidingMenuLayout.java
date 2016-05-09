package org.weiwei.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 自定义View,左侧菜单栏
 * 
 * @author weiwei
 * 
 */
public class SlidingMenuLayout extends HorizontalScrollView {
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * 
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单宽度
	 */
	private int mMenuWidth;
	/**
     * 
     */
	private int mHalfMenuWidth;

	private boolean isOpen;

	public SlidingMenuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SlidingMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SlidingMenuLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		LinearLayout wrapper = (LinearLayout) getChildAt(0);
		ViewGroup menu = (ViewGroup) wrapper.getChildAt(0); // 菜单
		ViewGroup content = (ViewGroup) wrapper.getChildAt(1); // 内容
		mScreenWidth = getScreenWidth();
		mMenuWidth = mScreenWidth - mMenuRightPadding;
		mHalfMenuWidth = mMenuWidth / 2;
		menu.getLayoutParams().width = mMenuWidth;
		content.getLayoutParams().width = mScreenWidth;
		mMenuRightPadding = mScreenWidth/3;

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}

	}

	private int getScreenWidth() {
		DisplayMetrics metrics = getContext().getResources()
				.getDisplayMetrics();
		return metrics.widthPixels;
	}

	// 监听事件,如果当前的侧滑菜单栏处于没有打开的状态,则放弃焦点,交给子焦点,否则响应
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.i("TouchEvent", "onTouchEvent SlidingMenuLayout");
		int action = ev.getAction();
		int scrollX = getScrollX();
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏

		case MotionEvent.ACTION_UP:
			Log.i("TouchEvent", scrollX+" "+mHalfMenuWidth+" "+isOpen+" "+" "+mMenuWidth);
			if (scrollX > mHalfMenuWidth&&isOpen) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
				return super.onTouchEvent(ev);
			}
		}
		return false;
	}
	
	public void openMenu(){
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	
	@Override
	public void scrollTo(int x, int y) {
//		// TODO Auto-generated method stub
		super.scrollTo(x, y);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 子视图优先于父视图处理消息,只有当子视图消耗该消息时,父视图才有机会处理
		// 那么实现逻辑就如下了
		Log.i("TouchEvent", "onInterceptTouchEvent SlidingMenuLayout");
		if (isOpen){
			Log.i("TouchEvent", "is open");
			return super.onInterceptTouchEvent(ev); // 这个super是指调用父方法来消耗掉该方法
//			return true;
		}

//			return true;
		else{
			Log.i("TouchEvent", "is close");
			return false; //返回false表示不消费该事件(即不会调用onTouchEvent)
		}

	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.i("TouchEvent", "dispatchTouchEvent SlidingMenuLayout");
		return super.dispatchTouchEvent(ev);
	}

}
