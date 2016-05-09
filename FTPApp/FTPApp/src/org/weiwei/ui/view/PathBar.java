package org.weiwei.ui.view;

import org.weiwei.ui.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 路径条
 * @author weiwei
 *
 */
public class PathBar extends LinearLayout{
	
	private static final int R_WRAP_CONTENT = RelativeLayout.LayoutParams.WRAP_CONTENT;
	private static final int R_MATCH_PARENT = RelativeLayout.LayoutParams.MATCH_PARENT;
	private static final int L_WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
	private static final int L_MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	
	/**
	 * 数据
	 */
	private String[] mDatas=new String[]{};
	/**
	 * 左边的标题栏
	 */
	private String leftTitleText="手机存储";
	
	/**
	 * 横向滚动条,用来显示路径名
	 */
	private HorizontalScrollView mHScrollView;
	
	/**
	 * 滚动条布局
	 */
	private LinearLayout mDirBar;
	/**
	 * 左标题名
	 */
	private TextView leftTitle;
	
	private Context context;
	
	private boolean once = false;
	

	public PathBar(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		this.context = context;
		initView(); //初始化界面的方法不要放在onLayout里面,因为layout会多次调用
	}

	public PathBar(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public PathBar(Context context) {
		this(context,null);

	}

	/**
	 * 初始化界面
	 */
	public void initView(){
		/**
		 * 设置PathBar控件的布局参数
		 */
		LinearLayout.LayoutParams mainLayout = new LinearLayout.LayoutParams(L_MATCH_PARENT, L_WRAP_CONTENT);
		setLayoutParams(mainLayout);
		setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundColor(getResources().getColor(R.color.white));//设置背景
		
		/**
		 * 设置滚动条区域的布局参数
		 */
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(L_WRAP_CONTENT, L_WRAP_CONTENT);
		mDirBar = new LinearLayout(context);
		mDirBar.setLayoutParams(lp);
		mDirBar.setOrientation(LinearLayout.HORIZONTAL);

		/**
		 * 设置路径
		 */
		refreshView(mDatas);
		
		//设置滚动条
		mHScrollView = new HorizontalScrollView(context);
		ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		mHScrollView.setLayoutParams(vl);
		mHScrollView.setHorizontalScrollBarEnabled(false); //隐藏滚动条

		mHScrollView.addView(mDirBar);
		
		leftTitle = new TextView(context);
		leftTitle.setText(leftTitleText);
		leftTitle.setTextColor(getResources().getColor(R.color.black));

		RelativeLayout leftLayout = new RelativeLayout(context);
		leftLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp4.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		lp4.leftMargin = 20;
		leftLayout.addView(leftTitle,lp4);
		
		ImageView image = new ImageView(context);
		image.setLayoutParams(lp);
		image.setBackgroundResource(R.drawable.crumb_bg_normal);

		addView(leftLayout);
		addView(image);
		addView(mHScrollView);
		
	}

	
	/**
	 * 更新界面
	 */
	public void refreshView(String[] mDatas){
		for(int i = 3;i<mDatas.length;i++){
			addView(mDatas[i]);
		}
	}
	
	/**
	 * 添加目录文本和箭头
	 */
	public void addView(String dirName){
		/**
		 * 目录显示区域mDir布局设置
		 */
		LinearLayout mDir = new LinearLayout(context); 
		mDir.setLayoutParams(new LinearLayout.LayoutParams(L_WRAP_CONTENT, L_WRAP_CONTENT));
		mDir.setOrientation(LinearLayout.HORIZONTAL);
		
		/**
		 * 目录名称显示文本
		 */
		TextView t = new TextView(context);
		t.setText(dirName);
		t.setTextColor(getResources().getColor(R.color.black));
		
		/**
		 * 目录文本所在的布局参数
		 */
		RelativeLayout r = new RelativeLayout(context);
		r.setLayoutParams(new RelativeLayout.LayoutParams(R_WRAP_CONTENT, R_MATCH_PARENT));	
		
		/**
		 * 目录文本自身参数
		 */
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(R_WRAP_CONTENT, R_WRAP_CONTENT);
		lp3.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		lp3.leftMargin = 20;
		
		r.addView(t,lp3);  //将文本添加到布局中
		
		/**
		 * 箭头图片
		 */
		ImageView image = new ImageView(context);
		image.setImageResource(R.drawable.crumb_bg_normal);
		
		/**
		 * 将文本和图片添加到目录显示区域
		 */
		mDir.addView(r);
		mDir.addView(image);
		
		/**
		 * 将目录显示区域添加到滚动条显示区域
		 */
		mDirBar.addView(mDir);	
	}

	public String[] getmDatas() {
		return mDatas;
	}

	public void setmDatas(String[] mDatas) {
		this.mDatas = mDatas;
	}

	public String getLeftTitleText() {
		return leftTitleText;
	}

	public void setLeftTitleText(String leftTitleText) {
		this.leftTitleText = leftTitleText;
		leftTitle.setText(leftTitleText);
	}
	
	/**
	 * 更新路径条,当数据更新时利用该方法进行进度条的更新
	 */
	public void notifyDataChanged(){
		mDirBar.removeAllViews();
		refreshView(mDatas);
		/**
		 * 将进度条移动到最右端
		 */
		mHScrollView.post(new Runnable() {
			
			@Override
			public void run() {
				mHScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
				
			}
		});
	}
	
	public void notifyDataChanged(String[] mDatas){
		setmDatas(mDatas);
		notifyDataChanged();
	}
}
