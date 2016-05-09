package org.weiwei.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.weiwei.ftp.FTPClient;

import android.os.Handler;

/**
 * 一个User包含了登录某个服务器的各种信息
 * 
 * @author weiwei
 * 
 */
public class User {

	public static final int IS_CONNECT = 0; //已经连接
	
	public static final int NOT_CONNECT = 1; //未连接
	
	public static final int IS_EXIST = 0; //已经存在
	
	public static final int NOT_EXIST = 1; //未存在
	
	/**
	 * id
	 */
	private int id;
	/**
	 * 服务器名称
	 */
	private String serverName; // 自定义的服务器名称
	/**
	 * ip地址
	 */
	private String host;
	/**
	 * 端口
	 */
	private String port;
	/**
	 * 账户
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 最近一次连接的时间
	 */
	private String lastConnectTime; // 最近登录时间
	/**
	 * 当前连接状态(断开或连接)
	 */
	private int state; // 当前的连接状态
	/**
	 * 是否是历史登录过
	 */
	private int exist = 0;
	/**
	 * 每开启一个任务,利用User信息去登录服务器,防止下载和上传命令阻塞用户浏览服务器 连接在任务启动的时候连接，任务完成后注销
	 */
	private FTPClient ListClient; // 用于浏览服务器文件
	/**
	 * 对应的任务链表,持有当前user的任务引用,有完成的,未完成的
	 */
	private List<Task> taskList = new ArrayList<Task>(); // 上传和下载与服务器所建立的连接数
	
	public User() {

	}

	public User(FTPClient client) {
		ListClient = client;
	}

	/**
	 * 给当前的task设置handler用于更新UI
	 * 
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		Iterator<Task> it = taskList.iterator();
		while (it.hasNext()) {
			it.next().setHandler(handler);
		}
	}

	/**
	 * 将task中的handler移除
	 */
	public void removeHandler() {
		Iterator<Task> it = taskList.iterator();
		while (it.hasNext()) {
			it.next().setHandler(null);
		}
	}

	/********************* set and get *********************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public FTPClient getListClient() {
		return ListClient;
	}

	public void setListClient(FTPClient mClient) {
		this.ListClient = mClient;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(String lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

}
