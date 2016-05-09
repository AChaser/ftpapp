package org.weiwei.ui.adapter;


import org.weiwei.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OperationAdapter extends BaseAdapter{

//	private List<String> OpNames; //获取操作的数据
	
	private String[] OpNames =new String[]{"发送","打开","删除"};
	private LayoutInflater mInflater;
	
	public OperationAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return OpNames.length;
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
			convertView = mInflater.inflate(R.layout.listitem_operation, parent, false);
			holder.tv = (TextView) convertView.findViewById(R.id.id_listitem_operation_text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(OpNames[position]);
		return convertView;
	}
	
	
	private final class ViewHolder{
		TextView tv;
	}

}
