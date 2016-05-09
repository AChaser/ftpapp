package org.weiwei.ui.activity;

import java.io.IOException;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.FTPClient;
import org.weiwei.ftp.exception.FTPException;
import org.weiwei.ftp.exception.FTPIllegalReplyException;
import org.weiwei.model.User;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 在该界面登录服务器,如果尚未登录服务器则会跳转到这个界面，否则会显示服务器界面主文件夹
 * 
 * @author weiwei
 * 
 */
public class LoginActivity extends Activity {

	private TopBar topBar;
	
	/**
	 * FTP操作
	 */
	private FTPClient mClient;
	/**
	 * 地址，端口，用户，密码
	 */
	private EditText hostView, portView, userView, passView;
	/**
	 * 登录按钮
	 */
	private Button loginButton;

	/**
	 * 地址，端口，用户，密码的字符
	 */
	// private String adds,host,user,pass;

	/**
	 * application
	 */
	private MyApplication myApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		myApp = (MyApplication) getApplication();
		initView();
	}

	private void initView() {
		hostView = (EditText) findViewById(R.id.id_login_host_edit);
		portView = (EditText) findViewById(R.id.id_login_port_edit);
		userView = (EditText) findViewById(R.id.id_login_user_edit);
		passView = (EditText) findViewById(R.id.id_login_pass_edit);

		loginButton = (Button) findViewById(R.id.id_login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取信息
				User u = new User();
				u.setHost(hostView.getText().toString());
				u.setPort(portView.getText().toString());
				u.setUsername(userView.getText().toString());
				u.setPassword(passView.getText().toString());
				new LoginTask().execute(u);// 执行登录操作
			}
		});
		
		topBar = (TopBar)findViewById(R.id.id_login_topbar);
		topBar.setOnTopBarClickListener(new topbarClickListener() {
			
			@Override
			public void rightClick() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void leftClick() {
				// TODO Auto-generated method stub
				LoginActivity.this.finish();
				
			}
		});
	}

	// 登录线程,开启一个线程来进行登录
	private class LoginTask extends AsyncTask<User, Void, String> {
		private User u ;
		@Override
		protected String doInBackground(User... params) {
			// 进行后台的登录操作
			u = params[0];
			String result = null;
			try {
				mClient = new FTPClient();
				mClient.connect(u.getHost(), Integer.valueOf(u.getPort())); //连接服务器
				mClient.login(u.getUsername(), u.getPassword()); //登录服务器
				u.setListClient(mClient);//设置登录成功的FTPClient作为用户浏览服务器的FTPClient
				result = "SUCCESS";
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				Log.i("login task","NumberFormatException");
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				Log.i("login task","IllegalStateException");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("login task","IOException");
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				Log.i("login task","FTPIllegalReplyException");
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				Log.i("login task","FTPException");
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 登录成功后跳转到登录界面，否则返回登录失败信息
			if("SUCCESS".equals(result)){
				//如果登录成功
				myApp.setUser(u);
				myApp.setLogin(true);
				//跳转到服务器界面
				Intent intent = new Intent(LoginActivity.this,MyServerActivity.class);
				startActivity(intent);				
				finish();
			}else{
				Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_LONG).show();
			}
			
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
}
