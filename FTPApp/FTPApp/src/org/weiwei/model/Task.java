package org.weiwei.model;

import org.weiwei.ftp.FTPClient;

import android.graphics.drawable.Drawable;
import android.os.Handler;

/**
 * 上传或下载任务
 * @author weiwei
 *
 */
public class Task implements TaskState{
	/**
	 * 任务ID
	 */
	private int taskID; // 用来获取任务在progressBar中的位置
	/**
	 * 任务类型
	 */
	private int taskType; // 任务类型
	/**
	 * 任务名称
	 */
	private String taskName; // 任务名称
	/**
	 * 远程存储位置
	 */
	private String remoteFileName; // 远程文件名（含路径）
	/**
	 * 本地存储位置
	 */
	private String localFilename; // 本地存储位置 (含路径)
	/**
	 * 传输进度
	 */
	private int progressValue;// 传输进度
	/**
	 * 任务状态
	 */
	private int taskState;// 任务状态
	/**
	 * 预计传输的文件大小
	 */
	private long filesize;
	/**
	 * 当前已经传输的文件大小
	 */
	private long done = 0;
	/**
	 * 任务图标
	 */
	private Drawable image;
	
	/**
	 * 用于执行任务的连接,任务的执行,暂停,取消都在这里执行
	 */
	private FTPClient client = new FTPClient();
	/**
	 * 用于更新界面的handler
	 */
	private Handler handler;

	public Task(){
		client = new FTPClient();
	}
	
	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public String getLocalFilename() {
		return localFilename;
	}

	public void setLocalFilename(String localFilename) {
		this.localFilename = localFilename;
	}

	public int getProgressValue() {
		return progressValue;
	}

	public void setProgressValue(int progressValue) {
		this.progressValue = progressValue;
	}

	public int getTaskState() {
		return taskState;
	}

	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}

	public FTPClient getClient() {
		return client;
	}

	public void setClient(FTPClient client) {
		this.client = client;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public long getDone() {
		return done;
	}

	public void setDone(long done) {
		this.done = done;
	}
	
	

}
