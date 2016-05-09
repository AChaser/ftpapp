package org.weiwei.ui.fragment;

import java.util.List;

import org.weiwei.model.PictureSet;
import org.weiwei.ui.activity.PictureStorage;
import org.weiwei.ui.activity.R;
import org.weiwei.utils.MediaUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PpictureFragment extends Fragment{

	private MediaUtils mMedia;
	private  List<PictureSet> picSet;
	private View view;
	private ListView mDirListView; //
	private Context context;
//	private Semaphore s = new Semaphore(1);
	
	public PpictureFragment() {
	}
	
	public PpictureFragment(Context context){
		this.context = context;
		mMedia = new MediaUtils(context);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_picture, container,false);
		return view;
	}

	
	
	@Override
	public void onStart() {
		mMedia.getPicture(mHandler); //获取图片集合
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();

		
	}



	private Handler mHandler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			picSet = (List<PictureSet>) msg.obj;
			Log.i("PIC", picSet.size()+"");
			initView();
		}
		
	};
	
	
	private void initView(){
		mDirListView = (ListView) view.findViewById(R.id.id_fragment_picture_picture_lv);
		mDirListView.setAdapter(new PicAdapter(context));
		mDirListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,PictureStorage.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("PictureSet", picSet.get(position));
				intent.putExtra("PictureSet", bundle);
				startActivity(intent);
			}
		});
		
		

	}
	
	private class PicAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		
		public PicAdapter(Context context){
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return picSet.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.listitem_fragment_picture_picture, parent, false);
				holder = new ViewHolder();
				holder.dir =(TextView) convertView.findViewById(R.id.id_listitem_fragment_p_p_dir);
				holder.count = (TextView)convertView.findViewById(R.id.id_listitem_fragment_p_p_pcount);
				holder.rightIcon = (ImageView) convertView.findViewById(R.id.id_listitem_fragment_p_p_image);
				convertView.setTag(holder);
			
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			PictureSet ps = picSet.get(position);
			holder.dir.setText(ps.getDir());
			holder.count.setText("("+ps.getPicList().size()+")");
			holder.rightIcon.setBackgroundResource(R.drawable.ic_right_arrow);
			
			return convertView;
		}
		
	}
	
	private final class ViewHolder{
		TextView dir;
		TextView count;
		ImageView rightIcon;
		
	}
}
