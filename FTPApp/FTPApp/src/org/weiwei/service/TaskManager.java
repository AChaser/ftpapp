package org.weiwei.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

/**
 * 上传和下载任务管理,利用线程池来并发多个线程
 * 
 * @author weiwei
 * 
 */

public class TaskManager {

	/**
	 * 同步锁
	 */
	private final static Object syncObj = new Object();
	/**
	 * 单例
	 */
	private static TaskManager instance;
	/**
	 * 线程池
	 */
	private ExecutorService executorService;
	/**
	 * 默认的最大任务数
	 */
	private final static int defTheadNumber = 3; //最多进行3个任务
	/**
	 * 任务线程列表
	 */
	private List<BaseThread> mTaskThreadList = new ArrayList<BaseThread>(); //线程列表
	
	private TaskManager(){
		this(defTheadNumber);
	}
	
	private TaskManager(int ThreadNumber) {
		executorService = Executors.newFixedThreadPool(ThreadNumber);
	}
	
	public static TaskManager getInstance(){
		return getInstance(defTheadNumber);
	}
	
	public static TaskManager getInstance(int ThreadNumber) {
		if (instance == null) {
			synchronized (syncObj) {
				instance = new TaskManager(ThreadNumber);
			}
		}
		return instance;
	}
	
	//执行线程
	public void executor(Runnable taskThread){
		if(taskThread!=null){
			mTaskThreadList.add((BaseThread) taskThread); 
			executorService.execute(taskThread);
		}
	}
	
//	/**
//	 * 设置传输列表的handler
//	 * @param handler
//	 */
//	public void setHandler(Handler handler){
//		Iterator<ITaskThread> it = mTaskThreadList.iterator();
//		while(it.hasNext()){
//			ITaskThread taskThread = it.next();
//			taskThread.setHandler(handler);
//		}
//	}
//	
//	//移除handler
//	public void removeHandler(){
//		Iterator<ITaskThread> it = mTaskThreadList.iterator();
//		while(it.hasNext()){
//			ITaskThread taskThread = it.next();
//			taskThread.removeHandler();
//		}
//	}
}
