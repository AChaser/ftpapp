package org.weiwei.ui.activity;

import java.util.List;

import org.weiwei.ftp.FTPClient;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.adapter.ServerListAdapter;
import org.weiwei.ui.dialog.LoginDialog;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;
import org.weiwei.utils.TimeUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 服务器列表,浏览当前登录的服务器,点击进入服务器可以浏览服务器文件内容
 * 多服务器登录,现在决定先取消这个功能,做个简单的,只实现单服务器登录功能
 * @author weiwei
 * 
 */
public class ServerListActivity extends Activity implements OnClickListener,OnItemClickListener {

	private LinearLayout mLoginLayout, mLogoutLayout;
	
	private ListView servers;
	
	private ServerListAdapter mAdapter;
	
	private LoginDialog mLoginDialog;
	
	private TopBar topBar;
	
	private AlertDialog dialog;

	private List<User> mServerList = CoreService.mServerList;
	
	private SQLiteDatabase sqlDatabase = CoreService.sqldatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_list);
		initView(); //初始化界面
	}
	
	/**
	 * 初始化界面
	 */
	private void initView() {
		mLoginLayout = (LinearLayout) findViewById(R.id.id_service_list_ll_login);
		mLogoutLayout = (LinearLayout) findViewById(R.id.id_service_list_ll_trans_list);
		mLoginLayout.setOnClickListener(this);
		mLogoutLayout.setOnClickListener(this);
		servers = (ListView) findViewById(R.id.id_service_list_listview);
		mAdapter = new ServerListAdapter(this,mServerList);
		servers.setAdapter(mAdapter);
		servers.setOnItemClickListener(this);
		servers.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				return false;
			}
		});
		mLoginDialog = new LoginDialog(this);
		topBar = (TopBar)findViewById(R.id.id_server_list_topbar);
		topBar.setOnTopBarClickListener(new topbarClickListener() {
			
			@Override
			public void rightClick() {
				
			}
			
			@Override
			public void leftClick() {
				ServerListActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_service_list_ll_login:
			mLoginDialog.show();
			break;
		case R.id.id_service_list_ll_trans_list:
			Intent intent = new Intent(this,TransListActivity.class);
			startActivity(intent); //打开activity
			break;
		case R.id.id_dialog_login_bt_yes:{
			//登录的每个服务器代表一个User,在服务器列表创建对应的界面
			User u = new User();
			u.setServerName("我的服务器");
			u.setState(User.NOT_CONNECT);
			u.setHost(mLoginDialog.getHost());
			u.setPort(mLoginDialog.getPort());
			u.setUsername(mLoginDialog.getUsername());
			u.setPassword(mLoginDialog.getPassword());
			u.setLastConnectTime(TimeUtils.getCurrentTime());
			u.setExist(User.NOT_EXIST);
			new LoginTask().execute(u);
			mLoginDialog.dismiss();
			break;
		}
		case R.id.id_dialog_login_bt_cancel:
			mLoginDialog.dismiss();
			break;
		default:
			break;
		}

	}

	// 登录成功后添加一个
	private class LoginTask extends AsyncTask<User, Void, String> {
		private FTPClient ListClient;
		private User u;
		@Override
		protected String doInBackground(User... params) {
			u = params[0];
//			ListClient = new FTPClient(); //最初的连接
//			ListClient.connect(u.getHost(), Integer.valueOf(u.getPort())); //连接
//			String result = ListClient.login(u.getUsername(), u.getPassword());  //登录
//			return result;
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result.equals("SUCCESS")){
				if(u.getExist()==User.IS_EXIST){
					//当前登录已经是存在过的
					u.setState(User.IS_CONNECT);
					mAdapter.notifyDataSetChanged();
				}else{
					u.setListClient(ListClient);
					u.setState(User.IS_CONNECT);
					u.setExist(User.IS_EXIST);
					mServerList.add(u); //将任务加入到列表中去
					mAdapter.notifyDataSetChanged();//更新数据
					ContentValues values = new ContentValues(); //数据库操作
					values.put("host", u.getHost());
					values.put("port", u.getPort());
					values.put("user", u.getUsername());
					values.put("pass", u.getPassword());
					values.put("state", User.IS_CONNECT);
					values.put("lastconnecttime",u.getLastConnectTime());
					values.put("exist", User.IS_EXIST);
					sqlDatabase.insert("user", null, values);
				}
			}else{
				Toast.makeText(ServerListActivity.this, "ERROR", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {

			super.onProgressUpdate(values);
		}

	}

	//ListView  Item被点击时进入到下一个页面即
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
		if(mServerList.get(position).getState()==User.NOT_CONNECT){
			//尚未登录,首先进行登录
//			Toast.makeText(ServerListActivity.this, "登录",Toast.LENGTH_LONG).show();
			buildDialog(mServerList.get(position));
			dialog.show();
		}
		else{
			//进入下一个页面之前要传递参数过去,已经登录
			Intent intent = new Intent(ServerListActivity.this,ServerFilesActivity.class);
			intent.putExtra("currentServerPosition", position);
			startActivity(intent);
		}
	}

	@Override
	protected void onDestroy() {
		//把当前的serverList写入数据库
		super.onDestroy();
	}
	
	/**
	 * 构建对话框
	 * @param u
	 */
	private void buildDialog(final User u){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage("登录当前服务器?")  
		       .setCancelable(false)  
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
		           public void onClick(DialogInterface dialog, int id) {  
		        	   new LoginTask().execute(u);
		           }  
		       })  
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {  
		           public void onClick(DialogInterface dialog, int id) {  
		                dialog.cancel();  
		           }  
		       });  
		dialog = builder.create(); 
	}
}
