package org.weiwei.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.weiwei.ftp.FTPClient;
import org.weiwei.ftp.FTPDataTransferListener;
import org.weiwei.ftp.exception.FTPAbortedException;
import org.weiwei.ftp.exception.FTPDataTransferException;
import org.weiwei.ftp.exception.FTPException;
import org.weiwei.ftp.exception.FTPIllegalReplyException;
import org.weiwei.model.Task;
import org.weiwei.model.User;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 下载线程,执行下载任务,处理下载逻辑,线程不应该与界面绑定在一起
 * 
 * @author weiwei
 * 
 */
public class DLThread implements Runnable,BaseThread, FTPDataTransferListener {
	/**
	 * Task记录的是本次任务的信息,包括需要返回给界面的数据
	 */
	private Task task;
	/**
	 * User记录的是当前交互的服务器信息
	 */
	private User user;
	/**
	 * 与服务器进行交互的工具
	 */
	private FTPClient mClient;
	/**
	 * 更新客户端界面的工具
	 */
	private Handler taskHandler;
	/**
	 * 记录已传输的数据大小
	 */
	private long done = 0;
	/**
	 * 传输的文件
	 */
	private File file; 

	/**
	 * 根据任务和任务的发起人创建了这个线程
	 * 
	 * @param task 本次任务的信息
	 * @param user 服务器信息
	 */
	public DLThread(Task task, User user) {
		this.task = task;
		this.user = user;
	}

	/**
	 * 线程执行
	 */
	@Override
	public void run() {
		if (user != null) {
			try {
				mClient = task.getClient();//获取任务的连接
				if(!mClient.isConnected()){
					mClient.connect(user.getHost(), Integer.valueOf(user.getPort()));// 连接服务器
				}
				if(!mClient.isAuthenticated()){
					mClient.login(user.getUsername(), user.getPassword());// 登录服务器
				}
				// 如果登录成功就进行下载,根据task获取本地存储位置
				file= new File(Task.DRF_LOCAL_PATH + task.getLocalFilename());
				if(file.exists()){
					done = file.length(); //获取了本地文件大小的信息
					Log.i("file size", "文件大小  "+file.length()+"预计下载 "+task.getFilesize());
					if(file.length()>=task.getFilesize()){
						done = 0;  //如果文件存在且下载完成,则重新下载
					}
				}
				// 执行下载
				String fileName = Task.DEF_REMOTE_PATH+task.getRemoteFileName();
				Log.i("file size", "文件大小(进入流)  "+file.length());
				mClient.download(fileName, file, done, this);
				// 将该耗时任务添加到user的任务列表中
//				user.getTaskList().add(task);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				e.printStackTrace();
			} catch (FTPException e) {
				e.printStackTrace();
			} catch (FTPDataTransferException e) {
				e.printStackTrace();
			} catch (FTPAbortedException e) {
				//断点续传的关键
				//会先执行Listener的aborted方法,然后再跳转到该方法来
				e.printStackTrace();
			}finally{
				
			}
		}
	}

	@Override
	public void started() {
		task.setTaskState(Task.TASK_STATE_GOING);
	}

	/**
	 * 传输过程中更新task的信息
	 */
	@Override
	public void transferred(int length) {
		done = done + length;
//		Log.i("download done", done+"");
//		Log.i("download done local",file.length()+"");
		//设置task任务进度
		task.setProgressValue((int) ((done / (float) task.getFilesize()) * 100)); 
		task.setDone(done);
//		Log.i("task progress",task.getProgressValue()+"");
		taskHandler = task.getHandler(); // 每次都先获取task的Handler查看是否存在
		if (taskHandler != null) {
			// 只有在有UI界面的时候才发送消息
			Message msg = Message.obtain();
			msg.what = 1;
			msg.obj = task;
			taskHandler.sendMessage(msg);// 向handler发送消息
		}

	}

	@Override
	public void completed() {
		close();
	}

	@Override
	public void aborted() {
		// 任务终止后,关闭连接
//		close();
	}

	@Override
	public void failed() {
		// 任务失败后,关闭连接
		close();
	}
	
	/**
	 * 关闭当前的连接
	 */
	public void close(){
		//传输完成后注销
		try {
			mClient.logout();
			mClient.disconnect(true);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
