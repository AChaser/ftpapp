package org.weiwei.ui.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.ftp.FTPHelper;
import org.weiwei.model.AppInfo;
import org.weiwei.model.Photo;
import org.weiwei.model.Task;
import org.weiwei.service.CoreService;
import org.weiwei.ui.activity.OperationActivity;
import org.weiwei.ui.activity.R;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

public class PphotoFragment extends Fragment{

	private GridView mGridView; //
	
	private ImageAdapter imageAdapter;
	
	private View view;

	private List<Photo> photos = new ArrayList<Photo>();//图片信息
	
	private List<Photo> photoChecked = new ArrayList<Photo>();//被选择的图片
	
	private List<View> viewChecked = new ArrayList<View>();//被选择的view
	
	private static ImageLoader imageLoader;  //图片加载器

	private Context context;
	
	private MyApplication myApp;
	
	/**
	 * 隐藏菜单
	 */
	private LinearLayout hideMenu;
	private RelativeLayout sendLayout,cancelLayout;
	private TextView sendText;
	private boolean isOpen = false;
	
	public PphotoFragment() {
		this(null);
	}
	
	public PphotoFragment(Context context){
		
		this.context=context;
		imageLoader = CoreService.imageLoader; //得到图片加载器
		getPhotoImage();  //获取图片路径
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_photo, container,false);
		return view;
	}


	private void initView(){
		myApp = (MyApplication) getActivity().getApplication();
		imageAdapter = new ImageAdapter();
		mGridView = (GridView) view.findViewById(R.id.id_fragment_picture_gridview);
		mGridView.setAdapter(imageAdapter);
		initHideMenu();
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				imageAdapter.updateChecked(position,view);
				int num = photoChecked.size();
				if(num>0){
					if(!isOpen){
						openHideMenu();
						isOpen = true;
					}
				}else{
					closeHideMenu();
				}
				sendText.setText("发送("+num+")");
				sendText.setTextColor(getResources().getColor(R.color.my_green));

			}
		});
		
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),OperationActivity.class);
				startActivityForResult(intent, 0);
				return false;
			}
		});

	}
	
	private void initHideMenu(){
		//hidemenu
		hideMenu = (LinearLayout) view.findViewById(R.id.id_hide_menu);
		hideMenu.setVisibility(View.GONE);
		closeHideMenu();
		sendText = (TextView) view.findViewById(R.id.id_hide_menu_send_text);
		sendLayout = (RelativeLayout)view.findViewById(R.id.id_hide_menu_send);
		cancelLayout = (RelativeLayout)view.findViewById(R.id.id_hide_menu_cancel);
		sendLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTask();//开启任务
			}
		});
		cancelLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeHideMenu();
				
			}
		});
	}
	

	/**
	 * 开始传输任务
	 */
	private void startTask(){
		//在这里要进行发送操作
		List<Photo> photoList = photoChecked;//获取被选择的photo
		List<Task> taskList = new ArrayList<Task>();
		Iterator<Photo> it = photoList.iterator();
		while(it.hasNext()){
			Photo p = it.next();
			Task task = new Task();
			String url = p.getUrl();//获取
			task.setTaskType(Task.TASK_UPLOAD);
			task.setRemoteFileName("G:/FTP/");//远程路径
			task.setLocalFilename(url); 
			task.setTaskName(p.getName());
			task.setFilesize(p.getFilesize());
			task.setTaskState(Task.TASK_STATE_READY);
			taskList.add(task);
		}
		if(myApp.getUser()!=null){
			new FTPHelper(myApp).uploadTasks(taskList, myApp.getUser());
			closeHideMenu(); //关闭隐藏菜单
			showMessage("已加入任务队列");
		}else{
			showMessage("未登录服务器");
		}
	}
	
	
	/**
	 * 打开隐藏菜单
	 */
	private void openHideMenu(){
		hideMenu.setVisibility(View.VISIBLE);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 0F, 1F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 165F, 0F);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
	}
	
	/**
	 * 关闭隐藏菜单
	 */
	private void closeHideMenu(){
		isOpen = false;
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 1F, 0F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 0F, 165f);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
		imageAdapter.clearChecked();
	}
	
	
	/**
	 * 图片适配器
	 * @author weiwei
	 *
	 */
	private class ImageAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		public ImageAdapter(){
			mInflater = LayoutInflater.from(getActivity());
		}
		@Override
		public int getCount() {
			return photos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.griditem_picture_fragment, parent, false);
				holder.imageview = (ImageView) convertView.findViewById(R.id.id_griditem_picture_fragment_image);
				holder.imageChoosed = (ImageView)convertView.findViewById(R.id.id_griditem_picture_fragment_selected);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.imageview.setBackgroundResource(R.drawable.photo_bg);
			holder.imageChoosed.setVisibility(photos.get(position).isChoosed()?View.VISIBLE:View.GONE);
			//通过这个进行加载图片,调用nodify操作会导致图片重新加载
			imageLoader.displayImage(photos.get(position).getPath(), holder.imageview,CoreService.options);

			return convertView;
		}
		

		/**
		 * 清空被选中的选项
		 */
		public void clearChecked(){
			Iterator<Photo> it = photoChecked.iterator();
			while(it.hasNext()){
				Photo ph = it.next();
				ph.setChoosed(false);
				int position = photoChecked.indexOf(ph);
				View v = viewChecked.get(position);
				ImageView image = (ImageView) v.findViewById(R.id.id_griditem_picture_fragment_selected);
				image.setVisibility(View.GONE);
			}
			photoChecked.clear();
			viewChecked.clear();
//			notifyDataSetChanged();
		}
		
		public void updateChecked(int position,View view){
			Photo p = photos.get(position);
			ImageView image = (ImageView) view.findViewById(R.id.id_griditem_picture_fragment_selected);
			if(p.isChoosed()){
				photoChecked.remove(p);
				viewChecked.remove(view);
				p.setChoosed(false);
				image.setVisibility(View.GONE);
			}else{
				photoChecked.add(p);
				viewChecked.add(view);
				p.setChoosed(true);
				image.setVisibility(View.VISIBLE);
			}	
		}
	}
	
	private final class ViewHolder{
		ImageView imageview;
		ImageView imageChoosed;
	}

	//获取相册中的图片
	private void getPhotoImage(){

		final String photoDir = "/storage/sdcard0/DCIM/Camera/"; //相册路径
		
		new Thread(new Runnable(){

			@Override
			public void run() {	
				Log.i("path", Environment.getExternalStorageDirectory().getAbsolutePath());
				Log.i("path",""+Environment.DIRECTORY_DCIM);
				Log.i("path",""+Environment.DIRECTORY_PICTURES);
				
				File file = new File(photoDir);
				String[] paths = file.list();
				for(int i = 0;i<paths.length;i++){
					if(paths[i].endsWith(".mp4")){
						continue;
					}
					Photo photo = new Photo();
					photo.setPath("file:///storage/sdcard0/DCIM/Camera/"+paths[i]);
					photo.setUrl(photoDir+paths[i]);
					photo.setChoosed(false);
					photo.setName(paths[i]);
					photo.setFilesize(new File(photoDir+paths[i]).length());
					photos.add(photo);
//					Log.i("path", paths[i]);
				}

				mHandler.sendEmptyMessage(0);
			}
			
		}).start();
	
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			initView();
		}
		
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		imageLoader.clearDiscCache(); //清理缓存
		imageLoader.clearMemoryCache();
	}

	private void showMessage(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
}
