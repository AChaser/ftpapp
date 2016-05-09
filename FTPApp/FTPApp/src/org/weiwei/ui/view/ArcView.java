package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ArcView extends View{

	
	private int progress; //进度条
	private Context context;
	private ArcViewClickListener listener;
	private boolean isStop = false; 
	private ImageView centerIcon; //中心的图标
	int width;
	int height;
	
	int centerX;
	int centerY;  //中心点的坐标
	
	int arcWidth;
	int arcHeight;
	
	public interface ArcViewClickListener{
		public void onClick();
	}
	
	public void setListener(ArcViewClickListener listener){
		this.listener = listener;
	}
	
	
	
	public boolean isStop() {
		return isStop;
	}



	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}



	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		if(isStop) return;
		this.progress = progress;
		postInvalidate();
	}

	public ArcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	private void init() {
		
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick();
			}
		});
	}

	public ArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ArcView(Context context) {
		super(context);
		this.context= context;
		
	}

	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
	
		
//		RectF r = new RectF(getLeft(), getTop(), getRight(), getBottom());
		Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
		paint.setColor(getResources().getColor(R.color.my_green));
//		canvas.drawArc(r, -90, 90, false, paint);
		width = getWidth();
		height = getHeight();
		
		centerX = width/2;
		centerY = height/2;  //中心点的坐标
		
		arcWidth = width/2-90;
		arcHeight = height/2-90;
		
		
		RectF r = new RectF(0,0,width,height);
		RectF arcarea = new RectF(arcWidth,arcHeight,arcWidth+90*2,arcHeight+90*2); //绘制矩形区域
		canvas.drawRect(r, paint);
//		canvas.drawRect(arcarea, paint);
		canvas.drawCircle(width/2,height/2, 100, paint); //使得在View的中心画圆
		canvas.drawCircle(width/2,height/2, 80, paint);
//		canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
		Paint paint2 = new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(20);
        paint2.setAntiAlias(true);
		paint2.setColor(getResources().getColor(R.color.gray));
		
		canvas.drawArc(arcarea, -90, progress, false, paint2);
//		canvas.drawBitmap(bitmap, left, top, paint2);
		
		Path path = new Path();
		path.moveTo(centerX+40, centerY);
		path.lineTo(centerX-20, centerY+30);
		path.lineTo(centerX-20, centerY-30);
		path.close();
		canvas.drawPath(path, paint);
		

		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		init();
		
	}

	
	
	
}
