package org.weiwei.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.weiwei.ftp.FTPClient;
import org.weiwei.ftp.FTPDataTransferListener;
import org.weiwei.ftp.exception.FTPAbortedException;
import org.weiwei.ftp.exception.FTPDataTransferException;
import org.weiwei.ftp.exception.FTPException;
import org.weiwei.ftp.exception.FTPIllegalReplyException;
import org.weiwei.ftp.exception.FTPListParseException;
import org.weiwei.model.Task;
import org.weiwei.model.User;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 上传任务
 * @author weiwei
 *
 */
public class ULThread implements Runnable,FTPDataTransferListener,BaseThread{
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
	
//	private String path = "/storage/sdcard0/FTPApp/download";
	/**
	 * 记录已传输的数据大小
	 */
	private long done = 0;

	
	public ULThread() {
	}

	public ULThread(Task task,User user){
		this.task = task;
		this.user = user;
	}
		
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
				//获取本地需要上传的文件
				File file = new File(task.getLocalFilename());
				// 执行上传
				/**
				 * upload的5个参数：1.需要将该文件存储在远程服务器的路径,该路径时绝对路径？
				 * 				2.文件输入流,即要上传的文件来源,要注意文件的本地路径
				 * 				3.开始的位置,断点续传要用到的参数,该参数是先检测服务器端文件的大小与
				 * 					本地文件的大小,若服务器端文件比本地小,则从服务器端文件大小处开始
				 * 				4.跳过n个字节传输
				 * 				5.传输监听器
				 */
				int i = mClient.list(Task.DEF_REMOTE_PATH+file.getName()).length;
				Log.i("查询服务器端上是否存在文件：", task.getTaskName()+"     "+i);
				
				if(i>0){
					//问题来了,查询不到就一直等着？？要有个方法去查询远程服务器是否存在文件
					done = mClient.fileSize(Task.DEF_REMOTE_PATH+file.getName());//获取服务器端文件大小
					if(done>=task.getFilesize()){
						done = 0;//重新上传
					}
				}
				mClient.upload(Task.DEF_REMOTE_PATH+file.getName(), new FileInputStream(file), done, done, this);
				
				// 将该耗时任务添加到user的任务列表中
//				user.getTaskList().add(task);

			} catch (IllegalStateException e) {

			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			} catch (FTPAbortedException e) {
				//断点续传的关键
				Log.i("test aborted", "任务中断");
				//按暂停后会跳转到这个位置
			} catch (FTPIllegalReplyException e) {

			} catch (FTPException e) {

			} catch (FTPDataTransferException e) {

			}catch (FTPListParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void started() {
		task.setTaskState(Task.TASK_STATE_GOING);
	}

	
	@Override
	public void transferred(int length) {
		done = done + length;
		//设置task任务进度
		task.setProgressValue((int) ((done / (float) task.getFilesize()) * 100));
		task.setDone(done);
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
//		close();
		Log.i("aborted", "任务中断,已传输:"+done+"b");
		Log.i("aborted", "往数据库里存储信息");
	}

	@Override
	public void failed() {
		close();
	}
	
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
