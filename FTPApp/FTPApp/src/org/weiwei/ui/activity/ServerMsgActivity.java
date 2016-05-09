package org.weiwei.ui.activity;

import org.weiwei.application.MyApplication;
import org.weiwei.model.User;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServerMsgActivity extends Activity {
	
	private TopBar topBar;
	
	/**
	 * 注销按钮
	 */
	private Button butLogout;
	
	private MyApplication myApp;
	
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_msg);
		myApp = (MyApplication) getApplication();
		user = myApp.getUser();
		initView();
	}
	
	private void initView(){
		butLogout = (Button) findViewById(R.id.id_server_msg_logout);
		butLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logout();
			}
		});
		
		topBar = (TopBar) findViewById(R.id.id_server_msg_topbar);
		topBar.setOnTopBarClickListener(new topbarClickListener() {
			
			@Override
			public void rightClick() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void leftClick() {
				ServerMsgActivity.this.finish();
			}
		});
	}
	
	private void logout(){
		if(user!= null){
			
		}
	}

}
