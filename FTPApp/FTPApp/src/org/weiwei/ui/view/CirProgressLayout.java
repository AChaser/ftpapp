package org.weiwei.ui.view;

import org.weiwei.model.Task;
import org.weiwei.ui.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CirProgressLayout extends FrameLayout {

	/**
	 * 该控件的测量值以及测量模式
	 */
	private float width, height, widthMode, heightMode;
	/**
	 * 控件中心坐标
	 */
	private float centerX, centerY;
	/**
	 * 中心各图片
	 */
	private Drawable centerIcon,startIcon,stopIcon,finishIcon;
	/**
	 * 进度条宽度以及圆圈的宽度
	 */
	private float progressWidth,circleWidth;
	/**
	 * 进度条以及进度条背景颜色
	 */
	private int progressColor,progressBackground;
	/**
	 * 中心的图片
	 */
	private ImageView centerImage;
	
	/**
	 * 停止标示符
	 */
	boolean stop = false;

	/**
	 * 进度条控件
	 */
	private CirProgress cirProgress;

	private LayoutParams centerLayout;
	
	private CirProgressClickListener listener;
	
	public interface CirProgressClickListener{
		public void click();
	}
	
	public void setCProListener(CirProgressClickListener l){
		this.listener = l;
	}
	
	public CirProgressLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.CircleProgress);
		//获取自定义属性
		for(int i = 0;i<ta.getIndexCount();i++){
			int attr =  ta.getIndex(i);
			switch(attr){
			case R.styleable.CircleProgress_centerIcon:
				centerIcon = ta.getDrawable(attr);
				break;
			case R.styleable.CircleProgress_circleWidth:
				circleWidth = ta.getDimension(attr, 1);
				break;
			case R.styleable.CircleProgress_finishIcon:
				finishIcon = ta.getDrawable(attr);
				break;
			case R.styleable.CircleProgress_progressBackgourd:
				progressBackground = ta.getColor(attr, 0);
				break;
			case R.styleable.CircleProgress_progressColor:
				progressColor = ta.getColor(attr, 0);
				break;
			case R.styleable.CircleProgress_progressWidth:
				progressWidth = ta.getDimension(attr, 5);
				break;
			case R.styleable.CircleProgress_startIcon:
				startIcon = ta.getDrawable(attr);
				break;
			case R.styleable.CircleProgress_stopIcon:
				stopIcon = ta.getDrawable(attr);
				break;
			}
		}
		
		centerImage = new ImageView(context);
		centerImage.setImageDrawable(centerIcon); // 设置中间图片
		cirProgress = new CirProgress(context); //
		ta.recycle();
		init(); //初始化

		//设置点击监听器
		centerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(stop){
					stop = false;
					centerImage.setImageDrawable(centerIcon);
				}else{
					stop = true;
					centerImage.setImageDrawable(stopIcon); //停止
				}
				listener.click();
				
			}
		});
	}
	
	private void init(){
		centerLayout = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		centerLayout.gravity = Gravity.CENTER; //居中
		//往framelayout里面添加view
		addView(centerImage,centerLayout);
		addView(cirProgress,centerLayout);
	}

	public CirProgressLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CirProgressLayout(Context context) {
		this(context, null);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		View child = getChildAt(0); //获取子View即图片的
		measureChild(child, widthMeasureSpec, heightMeasureSpec);//让子控件去测量自己的宽高
		float childWidth = child.getMeasuredWidth();
		float childHeight = child.getMeasuredHeight();
		
		width = getMeasuredWidth(); // 获取当前view的宽度
		height = getMeasuredHeight(); // 获取当前view的高度

		centerX = width / 2; // 所有的基准点
		centerY = height / 2; // 获取中心位置
		cirProgress.setIconWidthAndHeight(childWidth, childHeight); //
		cirProgress.setIconXY(centerX, centerY); //获取中心点坐标
		cirProgress.setProgressWidth(progressWidth);
		cirProgress.setProgressColor(progressColor);
		cirProgress.invalidate();
		// 如何摆放图片
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	public void setProgress(int progress){
		cirProgress.setProgress(progress);
	}
	
	public void setCenterImage(int state){
		switch(state){
		case Task.TASK_STATE_GOING:
			stop = false;
			centerImage.setImageDrawable(centerIcon);
			break;
		case Task.TASK_STATE_PAUSE:
			stop = true;
			centerImage.setImageDrawable(stopIcon); //停止
			break;
		}
	}
}
