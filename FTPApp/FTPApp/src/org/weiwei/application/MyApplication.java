package org.weiwei.application;

import org.weiwei.model.User;
import org.weiwei.service.CoreService;

import android.app.Application;

public class MyApplication extends Application{

	private CoreService coreService;
	/**
	 * 标示当前是否登录服务器
	 */
	private boolean isLogin = false;//标示当前是否登录服务器
	/**
	 * 当前的用户,即当前登录的服务器
	 */
	private User user;
	
	public MyApplication() {

	}

	public CoreService getCoreService() {
		return coreService;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	
}
