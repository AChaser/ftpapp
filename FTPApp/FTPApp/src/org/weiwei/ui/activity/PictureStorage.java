package org.weiwei.ui.activity;

import java.util.List;

import org.weiwei.model.Picture;
import org.weiwei.model.PictureSet;
import org.weiwei.service.CoreService;
import org.weiwei.ui.view.TopBar;
import org.weiwei.ui.view.TopBar.topbarClickListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class PictureStorage extends Activity implements topbarClickListener{
	
	private PictureSet picSet;
	private List<Picture> picList; //
	private String dir; //文件夹名称
	private GridView mGridView;
	private TopBar topBar;
	
	/**
	 * 图片加载器
	 */
	private ImageLoader imageLoader;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_storage);
		
		Bundle bundle = this.getIntent().getParcelableExtra("PictureSet");
		picSet = bundle.getParcelable("PictureSet");
		picList = picSet.picList;
		dir = picSet.dir;
		topBar = (TopBar) findViewById(R.id.id_activity_picture_storage_topbar);
		topBar.setTitle(dir);
		topBar.setOnTopBarClickListener(this);
		
		imageLoader = CoreService.imageLoader; //
		
		mGridView = (GridView) findViewById(R.id.id_activity_picture_storage_gridview);
		mGridView.setAdapter(new GvAdapter(this));
//		Toast.makeText(this, dir, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_storage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class GvAdapter extends BaseAdapter{
		
		private Context context;
		private LayoutInflater mInflater;
		
		public GvAdapter(Context context){
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return picList.size();
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
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.griditem_picture_fragment, parent, false);
				holder.imageview = (ImageView) convertView.findViewById(R.id.id_griditem_picture_fragment_image);
				holder.imageChoosed = (ImageView)convertView.findViewById(R.id.id_griditem_picture_fragment_selected);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Picture p = picList.get(position);
			holder.imageview.setBackgroundResource(R.drawable.photo_bg);
			holder.imageChoosed.setVisibility(p.isChoosed()?View.VISIBLE:View.GONE);
//			Log.i("TEST", " position "+position);
			imageLoader.displayImage("file://"+p.getPicUrl(), holder.imageview,CoreService.options);

			return convertView;
		}
		
	}
	
	private final class ViewHolder{
		ImageView imageview;
		ImageView imageChoosed;
	}


	@Override
	public void leftClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void rightClick() {
		// TODO Auto-generated method stub
		
	}
}
