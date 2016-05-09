package org.weiwei.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.adapter.MainAdapter;
import org.weiwei.ui.fragment.HistoryFragment;
import org.weiwei.ui.fragment.DownloadFragment;
import org.weiwei.ui.fragment.UploadFragment;
import org.weiwei.ui.view.MainViewPager;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 传输列表,打开传输列表时,首先去数据库查找未处理的任务信息
 * 
 * @author weiwei
 * 
 */

public class TransListActivity extends FragmentActivity {

	/**
	 * TopBar
	 */
	private TopBar mTopBar;
	/**
	 * application
	 */
	private MyApplication myApp;
	/**
	 * 核心服务
	 */
	private CoreService coreService;
	/**
	 * 当前访问的User
	 */
	private User user;

	/**
	 * ViewPager
	 */
	private MainViewPager mViewPager;
	/**
	 * view adapter
	 */
	private MainAdapter mAdapter;
	/**
	 * fragment list
	 */
	private List<Fragment> mDatas;

	private TextView mUploadView, mDownloadView,mDoneView;

	private ImageView mTabLine;

	private int mScreen1_3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_list);
		myApp = (MyApplication) getApplication();
		coreService = myApp.getCoreService();
		initServer();// 初始化服务
		initTabLine();
		initView();// 初始化界面
	}

	// 初始化服务
	public void initServer() {
		user = myApp.getUser();// 获取当前登录的用户
	}

	public void initView() {

		mDownloadView = (TextView) findViewById(R.id.id_title_tv_download);
		mUploadView = (TextView) findViewById(R.id.id_title_tv_upload);
		mDoneView = (TextView)findViewById(R.id.id_title_tv_done);

		setDefaultTextColor();
		
		// viewpager
		mViewPager = (MainViewPager) findViewById(R.id.id_activity_trans_list_viewpager);
		
		DownloadFragment downFrag = new DownloadFragment();
		UploadFragment upFrag = new UploadFragment();
		HistoryFragment doneFrag = new HistoryFragment();

		mDatas = new ArrayList<Fragment>();
		mDatas.add(downFrag);
		mDatas.add(upFrag);
		mDatas.add(doneFrag);
		mAdapter = new MainAdapter(getSupportFragmentManager(), mDatas);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setOnPageChangeListener(new TabListener());

		// listview
		// mListView = (ListView) findViewById(R.id.id_activity_trans_list_lv);
		// mTransListAdapter = new TransListAdapter(this,
		// coreService.getmTasks(),coreService,user);
		// mTransListAdapter.setListView(mListView);
		// mTransListAdapter.setHandler(); //set handler
		// mListView.setAdapter(mTransListAdapter);
		// mListView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// //点击暂停
		// }
		// });

		// topbar
		mTopBar = (TopBar) findViewById(R.id.id_activity_trans_list_topbar);
		mTopBar.setButtonVisable(false, TopBar.RIGHT_BUTTON);
		mTopBar.setOnTopBarClickListener(new topbarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				TransListActivity.this.finish();
			}
		});
	}

	/**
	 * 初始化tabline
	 */
	private void initTabLine() {
		mTabLine = (ImageView) findViewById(R.id.id_tabline);
		// 获取当前屏幕宽度
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		// 将tabline宽度设为屏幕宽度的1/2
		LayoutParams lp = (LayoutParams) mTabLine.getLayoutParams();
		lp.width = mScreen1_3;
		mTabLine.setLayoutParams(lp);
	}

	/**
	 * tab指示器,下标指示器
	 * 
	 * @author weiwei
	 * 
	 */
	protected class TabListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPx) {
			LayoutParams lp = (LayoutParams) mTabLine.getLayoutParams();
			// 不断设置Tabline与左侧的距离
			lp.leftMargin = (int) (position * mScreen1_3 + positionOffset
					* mScreen1_3);
			mTabLine.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			resetTextColor();
			switch (position) {
			case 0:
				mDownloadView.setTextColor(getResources().getColor(
						R.color.my_green));
				return;
			case 1:
				mUploadView.setTextColor(getResources().getColor(
						R.color.my_green));
				break;
			case 2:
				mDoneView.setTextColor(getResources().getColor(
						R.color.my_green));
				break;
			}
		}

	}

	/**
	 * 重新设置页面颜色
	 */
	public void resetTextColor() {
		mUploadView.setTextColor(Color.BLACK);
		mDownloadView.setTextColor(Color.BLACK);
		mDoneView.setTextColor(Color.BLACK);
	}
	
	/**
	 * 设置默认的页面颜色
	 */
	public void setDefaultTextColor(){
		mUploadView.setTextColor(Color.BLACK);
		mDoneView.setTextColor(Color.BLACK);
		mDownloadView.setTextColor(getResources().getColor(R.color.my_green));
		
	}
		

}
