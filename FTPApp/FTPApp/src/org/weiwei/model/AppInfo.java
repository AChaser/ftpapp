package org.weiwei.model;

import android.graphics.drawable.Drawable;

public class AppInfo {
	// 应用包名
	private String packname;
	// 应用版本号
	private String version;
	// 应用名称
	private String appname;
	// 应用图标
	private Drawable appicon;
	// 该应用是否属于用户程序
	private boolean userapp;
	// 应用大小
	private String appsize;
	//应用安装包路径
	private String url;
	//安装包大小
	private long filesize;
	
	private boolean ischaked = false;

	public boolean isUserapp() {
		return userapp;
	}

	public void setUserapp(boolean userapp) {
		this.userapp = userapp;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public Drawable getAppicon() {
		return appicon;
	}

	public void setAppicon(Drawable appicon) {
		this.appicon = appicon;
	}

	public String getAppsize() {
		return appsize;
	}

	public void setAppsize(String appsize) {
		this.appsize = appsize;
	}

	public boolean isIschaked() {
		return ischaked;
	}

	public void setIschaked(boolean ischaked) {
		this.ischaked = ischaked;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	

}
