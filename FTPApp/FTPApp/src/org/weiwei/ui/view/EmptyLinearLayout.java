package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 空界面
 * @author weiwei
 *
 */
public class EmptyLinearLayout extends RelativeLayout{
	private static final int R_WRAP_CONTENT = RelativeLayout.LayoutParams.WRAP_CONTENT;
	
	private static final int R_MATCH_PARENT = RelativeLayout.LayoutParams.MATCH_PARENT;
	
	private static final int L_WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
	
	private static final int L_MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	
	private Context context;
	/**
	 * 存放图片和文字的布局
	 */
	private LinearLayout mLayout;
	/**
	 * 空页面的展示图片
	 */
	private ImageView imageView; 
	/**
	 * 展示图片的底端文字
	 */
	private TextView textView;
	
	private LayoutInflater mInflater;
	
	private View view;
	
	public EmptyLinearLayout(Context context) {
		super(context);

	}

	
	
	public EmptyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}



	private void initView(){
		Log.i("EMPTY","TEST");
		LayoutInflater.from(context).inflate(R.layout.custom_layout_empty, this, false);
		mInflater = LayoutInflater.from(context);
		view = mInflater.inflate(R.layout.custom_layout_empty, this, false);
		addView(view);
		
		imageView = (ImageView) findViewById(R.id.id_custom_layout_empty_image);
		textView = (TextView) findViewById(R.id.id_custom_layout_empty_tx);
//		showEmpty(true);
//		RelativeLayout.LayoutParams mainLayout = new RelativeLayout.LayoutParams(R_MATCH_PARENT, R_MATCH_PARENT);
//		setLayoutParams(mainLayout);
//		
//		mLayout = new LinearLayout(context);
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(R_WRAP_CONTENT, R_WRAP_CONTENT);
//		lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//		mLayout.setLayoutParams(lp);
//		mLayout.setOrientation(LinearLayout.VERTICAL);
//		
//		
//		//设置图片
//		imageView = new ImageView(context);
//		imageView.setImageResource(R.drawable.empty_error);
//		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(L_WRAP_CONTENT, L_WRAP_CONTENT);
//		imageView.setLayoutParams(lp2);
//		mLayout.addView(imageView);
//		
//		//设置文字
//		textView = new TextView(context);
//		textView.setText("目前还没任务哦");
//		textView.setLayoutParams(lp2);
//		mLayout.addView(textView);
		
//		addView(mLayout);

	}
	
	public void setImage(int resId){
		imageView.setImageResource(resId);
	}
	
	public void setAnimImage(int resId){
		imageView.setImageResource(resId);
		AnimationDrawable anim = (AnimationDrawable) imageView.getDrawable();
		anim.start();
	}
	
	public void setText(String text){
		textView.setText(text);
	}
	
	/**
	 * 设置是否展示空页面
	 * @param isShow
	 */
	public void showEmpty(boolean isShow){
		Log.i("empty", "test empty");
		setVisibility(isShow?View.VISIBLE:View.GONE); 
	}
	
	/**
	 * 设置是否展示空页面
	 * @param length 数据长度
	 */
	public void showEmpty(int length){
		if(length<=0){
			setVisibility(View.VISIBLE);
			return;
		}
		setVisibility(View.GONE);
	}

	
	

}
