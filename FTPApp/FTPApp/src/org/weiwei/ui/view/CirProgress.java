package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * 自定义圆形进度条
 * @author weiwei
 *
 */
public class CirProgress extends View {
	/**
	 * 内部图标的宽高
	 */
	private float iconWidth, iconHeight;
	/**
	 * 内部图标的坐标
	 */
	private float iconX, iconY;

	/**
	 * 进度条的宽度
	 */
	private float progressWidth;
	/**
	 * 画笔
	 */
	private Paint circlePaint, progressPaint,progressBgPaint;
	/**
	 * 矩形轮廓
	 */
	private RectF proArcRect;
	/**
	 * 控件中心坐标X
	 */
	private float centerX;
	/**
	 * 控件中心坐标y
	 */
	private float centerY;
	/**
	 * 内外圆以及进度弧半径
	 */
	private float innerRadius, outerRadius, proRadius;
	/**
	 * 进度值
	 */
	private float progress;
	

	public CirProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint(); //初始化画笔
	}

	public CirProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CirProgress(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//内圆
		canvas.drawCircle(centerX, centerY, innerRadius, circlePaint);
		//外圆
		canvas.drawCircle(centerX, centerY, outerRadius, circlePaint);
		//进度条背景弧
		canvas.drawArc(proArcRect, -90f, 360, false, progressBgPaint);
		//进度条弧
		canvas.drawArc(proArcRect, -90f, progress, false, progressPaint);



	}

	/**
	 * 获得图片的宽高
	 * 
	 * @param iconWidth
	 * @param iconHeight
	 */
	public void setIconWidthAndHeight(float iconWidth, float iconHeight) {
		this.iconHeight = iconHeight;
		this.iconWidth = iconWidth;
		init(); // 初始化参数
	}

	/**
	 * 获取图片的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void setIconXY(float x, float y) {
		iconX = x;
		iconY = y;
		centerX = iconX;
		centerY = iconY;
		initRect();
	}

	/**
	 * 初始化各参数
	 */
	public void init() {
		float iconWidthHalf = iconWidth / 2;
		float iconHeightHalf = iconHeight / 2;
		/**
		 * 内圆半径
		 */
		innerRadius = (float) Math.sqrt(iconWidthHalf * iconWidthHalf
				+ iconHeightHalf * iconHeightHalf);
		/**
		 * 外圆半径
		 */
		outerRadius = innerRadius + progressWidth;
		/**
		 * 进度条圆半径
		 */
		proRadius = innerRadius + progressWidth / 2;

	}

	/**
	 * 初始化画笔
	 */
	public void initPaint() {
		circlePaint = new Paint();
		progressPaint = new Paint();
		progressBgPaint = new Paint();

		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(1);
		circlePaint.setColor(getResources().getColor(R.color.gray));
		circlePaint.setAntiAlias(true);

		progressPaint.setStyle(Paint.Style.STROKE);
		progressPaint.setStrokeWidth(progressWidth);
		progressPaint.setColor(getResources().getColor(R.color.green));
		progressPaint.setAntiAlias(true);
		
		progressBgPaint.setStyle(Paint.Style.STROKE);
		progressBgPaint.setStrokeWidth(progressWidth);
		progressBgPaint.setColor(getResources().getColor(R.color.white));
		progressBgPaint.setAntiAlias(true);
	}

	/**
	 * 初始化矩形轮廓
	 */
	public void initRect() {
		float arcLeft = centerX - proRadius;
		float arcTop = centerY - proRadius;
		float arcRight = centerX + proRadius;
		float arcBottom = centerY + proRadius;
		proArcRect = new RectF(arcLeft, arcTop, arcRight, arcBottom);
	}

	/**
	 * progress1-100
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.progress = (float) (3.6 * progress);
		postInvalidate();// 刷新界面
	}

	public float getProgressWidth() {
		return progressWidth;
	}

	public void setProgressWidth(float progressWidth) {
		this.progressWidth = progressWidth;
		progressPaint.setStrokeWidth(progressWidth); //设置圆弧宽度
		progressBgPaint.setStrokeWidth(progressWidth);
	}
	
	public void setProgressColor(int color){
		progressPaint.setColor(color);//设置颜色
	}

}
