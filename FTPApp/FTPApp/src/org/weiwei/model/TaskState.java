package org.weiwei.model;

/**
 * 任务状态类型
 * 
 * @author weiwei
 * 
 */
public interface TaskState {
	/**
	 * 默认本地存储路径
	 */
	public static final String DRF_LOCAL_PATH = "/storage/sdcard0/FTPApp/download/";
	/**
	 * 默认远程存储路径
	 */
	public static final String DEF_REMOTE_PATH = "G:/FTP/";
	/**
	 * 任务类型 下载
	 */
	public static final int TASK_DOWNLOAD = 0;
	/**
	 * 任务类型 上传
	 */
	public static final int TASK_UPLOAD = 1;
	/**
	 * 任务类型 删除
	 */
	public static final int TASK_DELETE = 2;
	/**
	 * 任务类型 重命名
	 */
	public static final int TASK_RENAME = 4;
	/**
	 * 任务类型 新建
	 */
	public static final int TASK_CREATE = 5;
	/**
	 * 任务状态 准备
	 */
	public static final int TASK_STATE_READY = 0;
	/**
	 * 任务状态 暂停
	 */
	public static final int TASK_STATE_PAUSE = 1;
	/**
	 * 任务状态 取消
	 */
	public static final int TASK_STATE_STOP = 2;
	/**
	 * 任务状态 完成
	 */
	public static final int TASK_STATE_FINISH = 3;
	/**
	 * 任务状态 进行中
	 */
	public static final int TASK_STATE_GOING = 4;

}
