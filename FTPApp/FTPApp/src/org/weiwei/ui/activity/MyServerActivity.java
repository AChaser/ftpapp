package org.weiwei.ui.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.FTPClient;
import org.weiwei.ftp.FTPFile;
import org.weiwei.ftp.FTPHelper;
import org.weiwei.ftp.exception.FTPAbortedException;
import org.weiwei.ftp.exception.FTPDataTransferException;
import org.weiwei.ftp.exception.FTPException;
import org.weiwei.ftp.exception.FTPIllegalReplyException;
import org.weiwei.ftp.exception.FTPListParseException;
import org.weiwei.model.Task;
import org.weiwei.model.User;
import org.weiwei.ui.adapter.ServerFilesAdapter;
import org.weiwei.ui.view.EmptyLinearLayout;
import org.weiwei.ui.view.PathBar;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;
import org.weiwei.utils.FTPFileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 我的服务器 界面,该界面显示远程服务器里的文件
 * 
 * @author weiwei
 * 
 */
public class MyServerActivity extends Activity {
	/**
	 * 顶部bar
	 */
	private TopBar mTopBar;
	/**
	 * 路径条控件
	 */
	private PathBar mPathBar;
	/**
	 * 文件列表
	 */
	private ListView mFileList;
	/**
	 * 文件列表适配器
	 */
	private ServerFilesAdapter mFileAdapter;
	/**
	 * application
	 */
	private MyApplication myApp;
	/**
	 * 当前的用户
	 */
	private User user;
	
	/**
	 * 隐藏菜单
	 */
	private LinearLayout hideMenu;
	/**
	 * 隐藏菜单按钮
	 */
	private RelativeLayout downLayout;
	/**
	 * 空页面展示
	 */
	private EmptyLinearLayout emptyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_server);
		myApp = (MyApplication) getApplication();
		user = myApp.getUser();
		initView(); // 初始化界面
		initListView(); // 初始化listview
		initDatas();

	}

	/**
	 * 初始化界面
	 */
	private void initView() {

		mTopBar = (TopBar) findViewById(R.id.id_my_server_topbar);
		mTopBar.setOnTopBarClickListener(new topbarClickListener() {
			@Override
			public void rightClick() {
				Intent intent = new Intent(MyServerActivity.this,ServerMsgActivity.class);
				startActivity(intent);
			}

			@Override
			public void leftClick() {
				MyServerActivity.this.finish();
			}
		});

		// 路径条,要有方法能够获取当前路径
		mPathBar = (PathBar) findViewById(R.id.id_my_server_path_bar);
		mPathBar.setLeftTitleText("远程存储");
		initHideMenu();
		emptyLayout = (EmptyLinearLayout) findViewById(R.id.id_empty_layout);
		emptyLayout.setVisibility(View.GONE);
		
	}

	/**
	 * 初始化隐藏菜单
	 */
	private void initHideMenu(){
		hideMenu = (LinearLayout) findViewById(R.id.id_activity_my_server_hide_menu);
		hideMenu.setVisibility(View.GONE);
		downLayout = (RelativeLayout) findViewById(R.id.id_bottom_hide_menu_down);
		downLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<FTPFile> files = mFileAdapter.getFileChecked();//获取当前被选择的文件
				//下面进行批量文件下载
				List<Task> taskList = new ArrayList<Task>();
				Iterator<FTPFile> it = files.iterator();
				while(it.hasNext()){
					FTPFile file = it.next();
					Task task = new Task();
					task.setTaskType(Task.TASK_DOWNLOAD);
//					Log.i("download name", file.getName());
					task.setRemoteFileName(file.getName());
					task.setLocalFilename(file.getName());
					task.setFilesize(file.getSize());
					task.setTaskName(file.getName());
					task.setTaskState(Task.TASK_STATE_READY);
					taskList.add(task);
				}
						
				new FTPHelper(myApp).downloadTasks(taskList, myApp.getUser());
				showMessage("已加入任务队列");
				mFileAdapter.closeHideMenu();
			}
		});
	}
	
	private void initListView() {
		// 文件列表
		mFileList = (ListView) findViewById(R.id.id_activity_my_server_lv);
		mFileAdapter = new ServerFilesAdapter(this, null);
		mFileAdapter.setHideMenu(hideMenu);//设置隐藏菜单
		
		mFileList.setAdapter(mFileAdapter);
		
		// 点击切换进入下一个文件夹
		mFileList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击后进入下一个文件夹,这里要进行一次网络访问
				FTPFile file = mFileAdapter.getFTPFile(position);
				// 点击的是文件夹,则进行文件夹切换操作（网络请求）
				if (file != null) {
					if(file.getType() == FTPFile.TYPE_DIRECTORY){
						new ListTask().execute(file.getName());
					}else if(file.getType()==FTPFile.TYPE_FILE){
						Intent intent = new Intent(MyServerActivity.this,OperationActivity.class);
						startActivity(intent);
					}
				}
			}
		});

		// 长按下载
		mFileList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				return false;
			}
		});
	}

	/**
	 * 初始化数据,获取服务器数据
	 */
	private void initDatas() {
		new ListTask().execute("currentDir");
	}
	
	// 按返回键返回上一个文件夹
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 按返回键返回上一个文件夹
			if(mFileAdapter.isOpen()){
				mFileAdapter.closeHideMenu();//如果隐藏菜单打开则进行关闭隐藏菜单选项
			}else{
				new ListTask().execute("parentDir");
			}

			return true;
		}
		return false;
	}

	/**
	 * ListTask主要处理浏览remote文件操作
	 * 
	 * @author weiwei
	 * 
	 */
	private class ListTask extends AsyncTask<String, Void, FTPFile[]> {
		String currentPath = null;
		String resultMessage = null; //返回的结果
		
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			emptyLayout.setAnimImage(R.anim.loding_page);
			emptyLayout.setText("正在加载中...");
			emptyLayout.setVisibility(View.VISIBLE);
			mFileList.setVisibility(View.GONE);
		}

		@Override
		protected FTPFile[] doInBackground(String... params) {
			FTPFile[] files = null;// 远程文件
			try {
				if (user != null) {
					FTPClient mClient = user.getListClient();
					if (mClient != null) {
						if (params[0].equals("currentDir")) {
							// 当前目录
							files = mClient.list(); // 获取服务器列表
						} else if (params[0].equals("parentDir")) {
							mClient.changeDirectoryUp();
							files = mClient.list();
						} else {
							// 改变当前的文件路径
							mClient.changeDirectory(params[0]);
							files = mClient.list();
						}
						currentPath = mClient.currentDirectory();// 获取当前路径
						Log.i("root dir", currentPath);
					}
				}
			} catch (IllegalStateException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (FTPException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (FTPDataTransferException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			} catch (FTPListParseException e) {
				resultMessage = e.getMessage();
				e.printStackTrace();
			}
			return files;
		}

		@Override
		protected void onPostExecute(FTPFile[] result) {
			super.onPostExecute(result);
			if (result != null) {
				// result在这里进行处理
				mFileAdapter.setmDatas(FTPFileUtils.sortAndFilter(result));// 返回的数据用于更新
				mFileAdapter.getFileChecked().clear();//清空
				if(mFileAdapter.isOpen()){
					mFileAdapter.closeHideMenu();
				}
				mFileAdapter.notifyDataSetChanged();
				// 这里要更新路径条
				mPathBar.notifyDataChanged(currentPath.split("/"));
				if(mFileAdapter.getmDatas().length<=0){
					emptyLayout.setImage(R.drawable.empty_file);
					emptyLayout.setText("该文件夹为空");
					emptyLayout.setVisibility(View.VISIBLE);
					return;
				}
				//切换文件夹以后
			} else {
				showMessage(resultMessage); 
			}
			emptyLayout.setVisibility(View.GONE);
			mFileList.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_server, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
