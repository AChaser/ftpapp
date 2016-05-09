package org.weiwei.ui.adapter;


import org.weiwei.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlidingMenuAdapter extends BaseAdapter{

	private Context context;
	
	private LayoutInflater mInflater;
	
	private int[] imageId = new int[]{R.drawable.ic_left_invitation,R.drawable.ic_left_pc,R.drawable.ic_left_setting,R.drawable.ic_left_about,
			R.drawable.ic_left_likeus};
	
	private String[] texts = new String[]{"个人信息","连接电脑","设置","关于我们","给我点赞"};
	
	public SlidingMenuAdapter(Context context){
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageId.length;
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
			convertView = mInflater.inflate(R.layout.listitem_sliding_menu, parent, false);
			holder  = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_sliding_menu_image);
			holder.text = (TextView) convertView.findViewById(R.id.id_listitem_sliding_menu_tx);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.image.setImageResource(imageId[position]);
		holder.text.setText(texts[position]);
		return convertView;
	}
	
	private final class ViewHolder{
		ImageView image;
		TextView text;
	}

}
