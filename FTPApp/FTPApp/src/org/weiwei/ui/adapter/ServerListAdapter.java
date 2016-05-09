package org.weiwei.ui.adapter;

import java.util.List;

import org.weiwei.model.User;
import org.weiwei.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServerListAdapter extends BaseAdapter{

	private List<User> mDatas;  //获取数据
	private LayoutInflater mInflater;
	private Context context;
	
	public ServerListAdapter(Context context,List<User> datas){
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ServerListViewHolder holder = null;
		if(convertView==null){
			holder = new ServerListViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_server_list, null);
			holder.serverName = (TextView) convertView.findViewById(R.id.id_listitem_server_list_servername);
			holder.lastConnectTime = (TextView) convertView.findViewById(R.id.id_listitem_server_list_lasttime);
			holder.state = (TextView) convertView.findViewById(R.id.id_listitem_server_list_state);
			holder.hortPort =(TextView) convertView.findViewById(R.id.id_listitem_server_list_host);
			convertView.setTag(holder);
		}else{
			holder = (ServerListViewHolder) convertView.getTag();
		}
		User u = mDatas.get(position);
		holder.serverName.setText(u.getServerName());
		holder.lastConnectTime.setText(u.getLastConnectTime());
		holder.hortPort.setText(u.getHost()+":"+u.getPort());
		holder.state.setText(u.getState()==User.IS_CONNECT?"已连接":"未连接");
		return convertView;
	}

}
