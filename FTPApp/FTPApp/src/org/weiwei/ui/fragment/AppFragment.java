package org.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.FTPHelper;
import org.weiwei.model.AppInfo;
import org.weiwei.model.Task;
import org.weiwei.service.AppInfoProvider;
import org.weiwei.ui.activity.OperationActivity;
import org.weiwei.ui.activity.R;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppFragment extends Fragment {

	private GridView mGridView;
	private AppFragmentAdapter mAppAdapter;
	private AppInfoProvider provider;
	private View view;

	/**
	 * 隐藏菜单
	 */
	private LinearLayout hideMenu;
	private RelativeLayout sendLayout,cancelLayout;
	private TextView sendText;
	private boolean isOpen = false;
	
	private MyApplication myApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_app, container, false);
		myApp = (MyApplication) getActivity().getApplication();
		initView();
		return view;
	}

	private void initView() {

		mGridView = (GridView) view.findViewById(R.id.id_fragment_app_gridview);		
		provider = new AppInfoProvider(getActivity());
		mAppAdapter = new AppFragmentAdapter(getActivity(),
				provider.getInstalledApps());
		initHideMenu();//初始化隐藏菜单
		mGridView.setAdapter(mAppAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAppAdapter.updateChecked(position, view);
				int num = mAppAdapter.getAppChecked().size();
				if(num>0){
					if(!isOpen){
						openHideMenu();
						isOpen = true;
					}
				}else{
					closeHideMenu();
				}
				sendText.setText("发送("+num+")");
				sendText.setTextColor(getResources().getColor(R.color.my_green));
			}
		});
		
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),OperationActivity.class);
				startActivityForResult(intent, 2);
				return false;
			}
		});
	}
	
	private void initHideMenu(){
		//hidemenu
		hideMenu = (LinearLayout) view.findViewById(R.id.id_hide_menu);
		hideMenu.setVisibility(View.GONE);
		closeHideMenu();
		sendText = (TextView) view.findViewById(R.id.id_hide_menu_send_text);
		sendLayout = (RelativeLayout)view.findViewById(R.id.id_hide_menu_send);
		cancelLayout = (RelativeLayout)view.findViewById(R.id.id_hide_menu_cancel);
		sendLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//执行上传任务
				List<AppInfo> appChecked = mAppAdapter.getAppChecked();//获取被选择的应用
				List<Task> taskList = new ArrayList<Task>();
				Iterator<AppInfo> it = appChecked.iterator();
				while(it.hasNext()){
					AppInfo app = it.next();
					Task task = new Task();
					task.setTaskType(Task.TASK_UPLOAD);
					task.setLocalFilename(app.getUrl());
					task.setRemoteFileName(Task.DEF_REMOTE_PATH);
					task.setTaskName(app.getAppname());
					task.setImage(app.getAppicon());
					taskList.add(task);
				}
				
				if(myApp.getUser()!=null){
					new FTPHelper(myApp).uploadTasks(taskList, myApp.getUser());
					closeHideMenu(); //关闭隐藏菜单
					showMessage("已加入任务队列");
				}else{
					showMessage("未登录服务器");
				}
				
			}
		});
		cancelLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeHideMenu();
				
			}
		});
	}

	/**
	 * 打开隐藏菜单
	 */
	private void openHideMenu(){
		hideMenu.setVisibility(View.VISIBLE);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 0F, 1F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 165F, 0F);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
	}
	
	/**
	 * 关闭隐藏菜单
	 */
	private void closeHideMenu(){
		isOpen = false;
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 1F, 0F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 0F, 165f);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
		mAppAdapter.clearChecked(); //清空数据
	}
	
	private void showMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
}
