package org.weiwei.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ViewGroup
 * @author weiwei
 *
 */

public class OperateView extends ViewGroup{

	
	private int mDataCount;
	

	public OperateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OperateView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OperateView(Context context) {
		super(context);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//获取大小
		
		
		
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//计算子控件高度
		
		for(int i = 0;i<getChildCount();i++){
			View child = getChildAt(i);	

		}
		
		
		
		
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		// TODO Auto-generated method stub
		return new MarginLayoutParams(p);
	}
	
	

}
