package org.weiwei.ftp;

import java.util.Iterator;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.model.Task;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;

/**
 * 实现一些复杂的操作,例如批量上传
 * @author weiwei
 *
 */
public class FTPHelper {
	
	private CoreService coreService;
	
	private MyApplication myApp;
	
	public FTPHelper(MyApplication myApp){
		this.myApp = myApp;
		coreService = myApp.getCoreService();// 获取核心服务
	}
	
	/**
	 * 批量上传
	 * @param taskList 在界面上要有一个封装的操作,封装成列表
	 * 
	 * @param user
	 */
	public void uploadTasks(List<Task> taskList,User user){
		//做循环,往coreService里添加任务,剩下的就交给线程池去处理
		Iterator<Task> it = taskList.iterator();
		List<Task> userList = user.getTaskList();
		while(it.hasNext()){
			Task task = it.next();
			//当前task里的FTPClient为未登录服务器状态，具体登录服务器的操作在DLThread线程里实现
			userList.add(task);//将任务的引用添加到user的tasklist里
			coreService.addTask(task, user);//将任务交给服务底层
			
		}
		
		
	}
	
	/**
	 * 批量下载
	 * @param taskList
	 * @param user
	 */
	public void downloadTasks(List<Task> taskList,User user){
		//做循环,往coreService里添加任务,剩下的就交给线程池去处理
		Iterator<Task> it = taskList.iterator();
		List<Task> userList = user.getTaskList();
		while(it.hasNext()){
			Task task = it.next();
			//当前task里的FTPClient为未登录服务器状态，具体登录服务器的操作在DLThread线程里实现
			userList.add(task);//将任务的引用添加到user的tasklist里
			coreService.addTask(task, user);//将任务交给服务底层
			
		}	
	}

}
