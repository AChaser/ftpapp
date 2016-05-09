package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubBar extends LinearLayout{

	private TextView leftTv,rightTv;
	private LinearLayout lLinearLayout,rLinearLayout;
	
	private LayoutParams leftParams,rightParam; //布局属性
	
	private int leftTextSize;
	private Drawable leftBackground;
	private String leftText;

	private int rightTextSize;
	private Drawable rightBackground;
	private String rightText;
	
	public SubBar(Context context) {
		super(context);
	}

	public SubBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SubBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);

		leftTextSize = (int) ta.getDimension(R.styleable.MyView_leftTextSize, 15);
		leftBackground = ta.getDrawable(R.styleable.MyView_leftBackground);
		leftText = ta.getString(R.styleable.MyView_leftText);

		rightTextSize = (int) ta.getDimension(R.styleable.MyView_rightTextSize, 15);
		rightBackground = ta.getDrawable(R.styleable.MyView_rightBackground);
		rightText = ta.getString(R.styleable.MyView_rightText);
		
		ta.recycle();
		
		leftTv = new TextView(context);
		leftTv.setText(leftText);
		leftTv.setTextSize(leftTextSize);
		leftTv.setBackground(leftBackground);
		
		rightTv = new TextView(context);
		rightTv.setText(rightText);
		rightTv.setTextSize(rightTextSize);
		rightTv.setBackground(rightBackground);
		
		
	}


		
	

}
