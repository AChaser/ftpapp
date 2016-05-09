package org.weiwei.ui.dialog;


import org.weiwei.ui.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginDialog extends Dialog{

	private String host,port, username, password;
	private EditText mHost, mPort, mUser, mPass;
	private Button mYes, mCan;
	private Context mContext;

	public LoginDialog(Context context) {
		super(context);
		mContext = context;
	}

	public LoginDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public LoginDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_login);
		mHost = (EditText) findViewById(R.id.id_dialog_login_host);
		mPort = (EditText) findViewById(R.id.id_dialog_login_port);
		mUser = (EditText) findViewById(R.id.id_dialog_login_username);
		mPass = (EditText) findViewById(R.id.id_dialog_login_password);
		mYes = (Button) findViewById(R.id.id_dialog_login_bt_yes);
		mCan = (Button) findViewById(R.id.id_dialog_login_bt_cancel);
		mYes.setOnClickListener((android.view.View.OnClickListener) mContext);
		mCan.setOnClickListener((android.view.View.OnClickListener) mContext);
	}

	public String getHost() {
		host = mHost.getText().toString();
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		username = mUser.getText().toString();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		password = mPass.getText().toString();
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getPort() {
		port = mPort.getText().toString();
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
