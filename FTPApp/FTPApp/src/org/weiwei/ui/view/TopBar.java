package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBar extends RelativeLayout {

	private LinearLayout leftLayout, rightLayout;
	private TextView titleTv;
	private ImageView line;
	
	private int leftTextColor;
	private Drawable leftBackground;
	private String leftText;

	private int rightTextColor;
	private Drawable rightBackground;
	private String rightText;

	private float titleTextSize;
	private int titleTextColor;
	private String title;

	private float lineHeight;
	private Drawable lineBackgound;
	
	
	private LayoutParams leftParams, rightParams, titleParams,lineParams;
	
	public static final int LEFT_BUTTON = 0;
	public static final int RIGHT_BUTTON = 1;

	private topbarClickListener listener;

	// 接口回调机制
	public interface topbarClickListener {
		public void leftClick();

		public void rightClick();
	}

	public void setOnTopBarClickListener(topbarClickListener listener) {
		this.listener = listener;
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyView);

		leftTextColor = ta.getColor(R.styleable.MyView_leftTextColor, 0);
		leftBackground = ta.getDrawable(R.styleable.MyView_leftBackground);
		leftText = ta.getString(R.styleable.MyView_leftText);

		rightTextColor = ta.getColor(R.styleable.MyView_rightTextColor, 0);
		rightBackground = ta.getDrawable(R.styleable.MyView_rightBackground);
		rightText = ta.getString(R.styleable.MyView_rightText);

		titleTextSize = ta.getDimension(R.styleable.MyView_titleTextSize, 10);
		titleTextColor = ta.getColor(R.styleable.MyView_titleTextColor, 0);
		title = ta.getString(R.styleable.MyView_title);

		lineHeight  = ta.getDimension(R.styleable.MyView_lineHeight, 1);
		lineBackgound = ta.getDrawable(R.styleable.MyView_lineBackground);
		
		ta.recycle();

		leftLayout = new LinearLayout(context);
		rightLayout = new LinearLayout(context);
		titleTv = new TextView(context);
		line = new ImageView(context);

//		leftLayout.setTextColor(leftTextColor);
		leftLayout.setBackground(leftBackground);
//		leftLayout.setText(leftText);

//		rightButton.setTextColor(rightTextColor);
		rightLayout.setBackground(rightBackground);
		
		
//		rightButton.setText(rightText);

		titleTv.setTextSize(titleTextSize);
		titleTv.setTextColor(titleTextColor);
		titleTv.setText(title);
		titleTv.setGravity(Gravity.CENTER);
		
		
		line.setBackground(lineBackgound);
		
		//布局设置
		leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		leftParams.addRule(RelativeLayout.CENTER_VERTICAL,TRUE);
		leftParams.setMargins(20, 0, 0, 0);
		addView(leftLayout, leftParams);

		rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		rightParams.addRule(RelativeLayout.CENTER_VERTICAL,TRUE);
		rightParams.setMargins(0, 0, 10, 0);
		addView(rightLayout, rightParams);

		titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
		addView(titleTv, titleParams);

		lineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) lineHeight);
		lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,TRUE);
		addView(line,lineParams);
		
		leftLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.leftClick(); // 接口回调机制
			}
		});

		rightLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.rightClick(); //
			}
		});
	}

	public void setButtonVisable(boolean flag, int w) {
		if (w == LEFT_BUTTON) {
			if (flag) {
				leftLayout.setVisibility(View.VISIBLE);
			} else {
				leftLayout.setVisibility(View.GONE);
			}
			return;
		}
		if (flag)
			rightLayout.setVisibility(View.VISIBLE);
		else {
			rightLayout.setVisibility(View.GONE);
		}

	}
	
	public void setTitle(String title){
		titleTv.setText(title);
	}
}
