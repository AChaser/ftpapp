package org.weiwei.ui.fragment;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.weiwei.application.MyApplication;
import org.weiwei.model.Audio;
import org.weiwei.model.Task;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.activity.OperationActivity;
import org.weiwei.ui.activity.R;
import org.weiwei.utils.MediaUtils;
import org.weiwei.utils.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VmusicFragment extends Fragment {

	private MediaUtils mUtils;
	/**
	 * 记录音乐的信息,包括音乐文件的路径
	 */
	private List<Audio> mAudios; 
	/**
	 * 上下文
	 */
	private Context context;
	
	private View v;
	/**
	 * listview显示音乐list
	 */
	private ListView listView;
	
	private CoreService coreService;
	
	private MyApplication myApp;
	
//	private User user;
	
	private Semaphore s = new Semaphore(1);

	public VmusicFragment(Context context) {
		this.context = context;
		mUtils = new MediaUtils(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("TAST", "onCreateView"+"---------VmusicFragment");
		v = inflater.inflate(R.layout.fragment_video_music, container,false);

		return v;
	}
	
	private void initServer(){
		myApp = (MyApplication) getActivity().getApplication();
		coreService = myApp.getCoreService();
//		user = myApp.getUser();
	}
	
	private void initView() {

		listView = (ListView) v.findViewById(R.id.id_fragment_video_music_lv);
		mAudios = mUtils.getAudio(mHandler);

//		Log.i("TAST",mAudios.size()+"  audio");
		
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			initServer();
			listView.setAdapter(new MusicAdapter(context));
			//设置ListView点击监听器
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(getActivity(),OperationActivity.class);
					startActivityForResult(intent, 2);
					
				}
			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
		
	};
	

	private class MusicAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MusicAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mAudios.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
//			Log.i("TAST",position+" position  audio");
			ViewHolder holder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.listitem_fragment_video_music, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_fragment_video_music_image);
				holder.musicName=(TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_name);
				holder.musicSize = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_size);
				holder.musicDur = (TextView) convertView.findViewById(R.id.id_listitem_fragment_video_music_duration);
				holder.sentBtn = (Button) convertView.findViewById(R.id.id_listitem_sent_button);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Audio audio = mAudios.get(position);
			holder.image.setImageResource(R.drawable.ic_easytransfer_music);
			String title = audio.getTitle();
			if(title.length()>20){
				title = title.substring(0, 20)+"..";
			}
			holder.musicName.setText(title);
			holder.musicSize.setText(StringUtils.formatByte(audio.getSize()));
			holder.musicDur.setText(StringUtils.getTime(audio.getDuration()));
			holder.sentBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//在这里执行任务操作
					if(myApp.getUser()!=null){
						Audio a = mAudios.get(position);
						Task task = new Task();
						task.setTaskType(Task.TASK_UPLOAD);
						task.setLocalFilename(a.getDataurl());
						task.setRemoteFileName(Task.DEF_REMOTE_PATH);
						task.setFilesize(a.getSize());
						task.setTaskName(a.getTitle());
						task.setDone(0);
						coreService.addTask(task, myApp.getUser());
						showMessage("已加入任务队列");
					}else{
						showMessage("未登录服务器");
					}

					
				}
			});
			
			return convertView;
		}

	}

	private final class ViewHolder {
		ImageView image;
		TextView musicName;
		TextView musicSize;
		TextView musicDur;
		Button sentBtn;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
//		Log.i("TAST", "onActivityCreated"+"---------VmusicFragment");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
//		Log.i("TAST", "onAttach"+"---------VmusicFragment");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		Log.i("TAST", "onCreate"+"---------VmusicFragment");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
//		Log.i("TAST", "onDestroyView"+"---------VmusicFragment");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
//		Log.i("TAST", "onDetach"+"---------VmusicFragment");
		super.onDetach();
	}

	@Override
	public void onPause() {
//		Log.i("TAST", "onPause"+"---------VmusicFragment");
		super.onPause();
	}

	@Override
	public void onStart() {
//		Log.i("TAST", "onStart"+"---------VmusicFragment");
		initView();
		super.onStart();
	}

	@Override
	public void onStop() {
//		Log.i("TAST", "onStop"+"---------VmusicFragment");
		super.onStop();
	}
	private void showMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
	
}
