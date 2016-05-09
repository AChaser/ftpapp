package org.weiwei.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.weiwei.dao.DBHelper;
import org.weiwei.dao.DBUtils;
import org.weiwei.model.Task;
import org.weiwei.model.User;
import org.weiwei.ui.activity.R;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 运行在UI线程,将任务交给TaskManager(线程池)去管理
 * @author weiwei
 *
 */
public class CoreService extends Service {

	protected static final int TASK_LIST = 0;  //浏览服务器文件
	protected static final int TASK_UPLOAD = 1;  //上传本地文件
	protected static final int TASK_DONWLOAD = 2; //下载服务器文件
	
	//图片加载框架配置
	public static ImageLoaderConfiguration config;
	public static DisplayImageOptions options;
	public static ImageLoader imageLoader;  //图片加载器

	//任务队列
	private List<Task> upDoneList = new ArrayList<Task>(); //上传完成队列
	
	private List<Task> downDoneList = new ArrayList<Task>();//下载完成队列
	
	private List<Task> upList = new ArrayList<Task>();  //上传列表
	
	private List<Task> downList = new ArrayList<Task>(); //下载列表
		
	/**
	 * 线程池
	 */
	private TaskManager mTaskManager;
	
	/**
	 * 数据库操作
	 */
	private DBHelper dbHelper;
	/**
	 * 数据库操作对象
	 */
	public static SQLiteDatabase sqldatabase;
	
	/**
	 * 维护历史连接过的服务器,这些信息保存在数据库
	 */
	public static List<User> mServerList = new ArrayList<User>(); // 服务器列表

	private MyBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		public CoreService getService() {
			return CoreService.this; //获取当前Service的引用
		}
	}

	/**
	 * 绑定时会调用这个方法，返回一个Binder类，通过Binder类可以获取Service对象
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//创建线程池
		 mTaskManager = TaskManager.getInstance();
		 //初始化图片加载器
		 initImageLoader();
		 //初始化数据库
		 initDatabase();
		Log.i("TAG","service is create");
		
	}
	
	/**
	 * 添加费时任务
	 * @param task 任务信息
	 * @param user 服务器信息
	 */
	public void addTask(Task task,User user){
		switch (task.getTaskType()) {
		case Task.TASK_DOWNLOAD:
			if(downList.indexOf(task)==-1){
				downList.add(task);//加入到下载队列
			}
			mTaskManager.executor(new DLThread(task,user));  //执行下载任务
			break;
		case Task.TASK_UPLOAD:
			if(upList.indexOf(task)==-1){
				upList.add(task);//加入到上传队列
			}
			Log.i("upload", "执行上传任务");
			mTaskManager.executor(new ULThread(task,user));  //执行上传任务
			break;
		}
	}
	
	//更新完成队列
	public void updateDoneList(Task task){
		
	}
	
	public List<Task> getUpList() {
		return upList;
	}

	public void setUpList(List<Task> upList) {
		this.upList = upList;
	}

	public List<Task> getDownList() {
		return downList;
	}

	public void setDownList(List<Task> downList) {
		this.downList = downList;
	}
	

	public List<Task> getUpDoneList() {
		return upDoneList;
	}

	public void setUpDoneList(List<Task> upDoneList) {
		this.upDoneList = upDoneList;
	}

	public List<Task> getDownDoneList() {
		return downDoneList;
	}

	public void setDownDoneList(List<Task> downDoneList) {
		this.downDoneList = downDoneList;
	}
	
	
	
//	//要更新的handler不止这个页面
//	public void setHandler(Handler handler){
//		mTaskManager.setHandler(handler);
//	}
//	
//	public void removeHandler(){
//		mTaskManager.removeHandler();
//		
//	}

	/**************************图片浏览框架************************************************/


	/**
	 * 初始化配置ImageLoader图片加载框架
	 */
	public void initImageLoader(){
		File cacheDir = StorageUtils.getOwnCacheDirectory(this, "FTPApp/cache");//设置缓存
		//imageloader config
		config = new ImageLoaderConfiguration.Builder(this)
		.memoryCache(new WeakMemoryCache())
//		.memoryCacheSize(15*1024*1024)
		.threadPriority(Thread.NORM_PRIORITY-2)  //设置线程优先级
		.threadPoolSize(2)
		.denyCacheImageMultipleSizesInMemory() //
		.discCacheFileNameGenerator(new Md5FileNameGenerator()) //设置缓存文件的名字
		.discCacheFileCount(100)  //缓存文件最大个数
		.discCache(new UnlimitedDiscCache(cacheDir)) //自定义缓存路径
		.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		
		//options
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_blank_picture_icon)
		.showImageForEmptyUri(R.drawable.ic_blank_picture_icon)
		.showImageOnFail(R.drawable.ic_blank_picture_icon)
		.bitmapConfig(Bitmap.Config.RGB_565) //这个很关键，否则容易出现OOM,并且会出现卡顿
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new SimpleBitmapDisplayer()).build();
		
	}
	
	
	/***************************数据库部分*****************************************/
	
	
	/**
	 * 初始化数据库操作对象
	 */
	private void initDatabase() {
		dbHelper = new DBHelper(this);
		sqldatabase = dbHelper.getWritableDatabase(); //获得一个可写的数据库
		initServerList();//更新历史浏览过的服务器
	}
	
	/**
	 * 从数据库加载历史登录过的服务器,以及之前的任务和未完成的任务
	 */
	private void initServerList(){
		Cursor c = sqldatabase.query("user", null, null, null, null, null, null);
		Log.i("dao", "更新当前的列表");
		Log.i("dao", c.toString());
		
		while(c.moveToNext()){
			User u = DBUtils.getUser(c);
			u.setState(User.NOT_CONNECT);
			//查找该user的task列表
			Cursor cursor = sqldatabase.query("task", null, "userid ="+u.getId(), null, null, null, null);
			while(cursor.moveToNext()){
				Task task = DBUtils.getTask(cursor);
				u.getTaskList().add(task);//将该task加入
			}			
			mServerList.add(u);
		}
	}
}
