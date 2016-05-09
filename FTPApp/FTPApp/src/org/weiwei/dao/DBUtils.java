package org.weiwei.dao;

import org.weiwei.model.Task;
import org.weiwei.model.User;

import android.database.Cursor;

public class DBUtils {

	/**
	 * 获取数据库中User对象
	 * @param c
	 * @return
	 */
	public static User getUser(Cursor c){
		User u = new User();
		u.setId(c.getInt(c.getColumnIndex("id")));
		u.setHost(c.getString(c.getColumnIndex("host")));
		u.setPort(c.getString(c.getColumnIndex("port")));
		u.setServerName("我的服务器");
		u.setUsername(c.getString(c.getColumnIndex("user")));
		u.setPassword(c.getString(c.getColumnIndex("pass")));
		u.setLastConnectTime(c.getString(c.getColumnIndex("lastconnecttime")));
		u.setExist(c.getInt(c.getColumnIndex("exist")));
		return u;
	}
	
	/**
	 * 获取数据库中的task
	 * @param c
	 * @return
	 */
	public static Task getTask(Cursor c){
		Task task = new Task();
		task.setTaskID(c.getInt(c.getColumnIndex("id")));
		task.setTaskType(c.getInt(c.getColumnIndex("type")));
		task.setTaskName(c.getString(c.getColumnIndex("taskname")));
		task.setRemoteFileName(c.getString(c.getColumnIndex("remote")));
		task.setLocalFilename(c.getString(c.getColumnIndex("local")));
		task.setFilesize(c.getInt(c.getColumnIndex("filesize")));
		task.setTaskState(c.getInt(c.getColumnIndex("taskstate")));
		task.setDone(c.getLong(c.getColumnIndex("restartat")));
		task.setProgressValue(c.getInt(c.getColumnIndex("progressvalue")));
		return task;
	}
	
	public static boolean insertUser(User u){
		return false;
	}
	
	public static boolean insertTask(Task task){
		return false;
	}
}
