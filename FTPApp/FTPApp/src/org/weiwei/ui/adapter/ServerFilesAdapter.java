package org.weiwei.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.weiwei.ftp.FTPFile;
import org.weiwei.ui.activity.R;
import org.weiwei.utils.StringUtils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 服务器文件列表 的适配器
 * @author weiwei
 *
 */
public class ServerFilesAdapter extends BaseAdapter{
	/**
	 * 服务器文件列表数据
	 */
	private FTPFile[] mDatas;
	/**
	 * 记录被选择的条目
	 */
	private List<FTPFile> fileChecked = new ArrayList<FTPFile>();
	/**
	 * 隐藏菜单
	 */
	private LinearLayout hideMenu;
	/**
	 * 隐藏菜单开关标志
	 */
	private boolean isOpen = false;
	/**
	 * 上下文
	 */
	private Context context;
	
	private LayoutInflater mInflater;
	
	public ServerFilesAdapter(Context context,FTPFile[] datas){
		this.context = context;
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}
	
	@Override
	public int getCount() {
		if(mDatas==null){
			return 0;
		}
		return mDatas.length;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ServerFilesViewHolder holder = null;
		if(convertView==null){
			holder = new ServerFilesViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_server_files, parent,false);
			holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_server_file_image);
			holder.filename = (TextView) convertView.findViewById(R.id.id_listitem_server_file_name);
			holder.filesize = (TextView) convertView.findViewById(R.id.id_listitem_server_file_size);
			holder.modifiedDate = (TextView) convertView.findViewById(R.id.id_listitem_server_file_modify_date);
			holder.checkImage = (ImageView) convertView.findViewById(R.id.id_listitem_server_file_check_image);
			convertView.setTag(holder);
		}else{
			holder = (ServerFilesViewHolder) convertView.getTag();
		}

		holder.filename.setText(mDatas[position].getName());
		
		int type = mDatas[position].getType();
		if(type==FTPFile.TYPE_FILE){
			holder.image.setImageResource(R.drawable.ic_easytransfer_file);
			holder.filesize.setText(StringUtils.formatByte(mDatas[position].getSize()));
			holder.modifiedDate.setText(StringUtils.formatDate(mDatas[position].getModifiedDate()));
		}else if(type==FTPFile.TYPE_DIRECTORY){
			holder.image.setImageResource(R.drawable.ic_easytransfer_folder);
			holder.filesize.setText("");
			holder.modifiedDate.setText(StringUtils.formatDate(mDatas[position].getModifiedDate()));
		}
		
		//判断当前条目是否是被选择的
		FTPFile file = mDatas[position];//获取当前选择的
		if(fileChecked.indexOf(file)==-1){
			//为-1说明没有找到，即该被选择项不属于被选项
			holder.checkImage.setImageResource(R.drawable.ic_checkbox_unchecked);

		}else{
			//不为-1说明找到该项，即该项当前属于被选
			holder.checkImage.setImageResource(R.drawable.ic_checkbox_checked);

		}	
		
		
		//监听器
		holder.checkImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//判断当前点击的条目是否是被选择的,没有被选择的则显示被选择，并且加入到队列中
				//否则取消选择，从队列中移除
				ImageView image = (ImageView) v;
				
				FTPFile file = mDatas[position];//获取当前选择的
				if(fileChecked.indexOf(file)==-1){
					//为-1说明没有找到，即该被选择项不属于被选项
					image.setImageResource(R.drawable.ic_checkbox_checked);
					fileChecked.add(file);
					//在这里面展示
					
				}else{
					//不为-1说明找到该项，即该项当前属于被选项
					image.setImageResource(R.drawable.ic_checkbox_unchecked);
					fileChecked.remove(file);
				}
				int num = fileChecked.size();
				if(num>0){
					if(!isOpen){
						openHideMenu();//开启隐藏菜单
					}
				}else{
					closeHideMenu();//关闭隐藏菜单
				}
				
			}
		});
		
		return convertView;
	}
	
	
	/**
	 * 打开隐藏菜单
	 */
	public void openHideMenu(){
		isOpen = true;
		hideMenu.setVisibility(View.VISIBLE);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 0F, 1F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 150F, 0F);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
	}
	
	/**
	 * 关闭隐藏菜单
	 */
	public void closeHideMenu(){
		isOpen = false;
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hideMenu,"alpha", 1F, 0F);
		ObjectAnimator animator = ObjectAnimator.ofFloat(hideMenu,"translationY", 0F, 150f);
		alphaAnimator.setDuration(400);
		animator.setDuration(400);
		alphaAnimator.start();
		animator.start();
		fileChecked.clear();
		notifyDataSetChanged();
	}
	
	
	public FTPFile[] getmDatas() {
		return mDatas;
	}

	public void setmDatas(FTPFile[] mDatas) {
		this.mDatas = mDatas;
	}

	public LayoutInflater getmInflater() {
		return mInflater;
	}

	public void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}
	
	public FTPFile getFTPFile(int position){
		FTPFile file = mDatas[position];
		if(file!=null){
			return file;
		}
		return null;
	}
	/**
	 * 获取当前被选中的选项
	 * @return
	 */
	public List<FTPFile> getFileChecked() {
		return fileChecked;
	}
	/**
	 * 设置
	 * @param fileChecked
	 */
	public void setFileChecked(List<FTPFile> fileChecked) {
		this.fileChecked = fileChecked;
	}
	/**
	 * 获取隐藏菜单
	 * @return
	 */
	public LinearLayout getHideMenu() {
		return hideMenu;
	}
	/**
	 * 设置隐藏菜单
	 * @param hideMenu
	 */
	public void setHideMenu(LinearLayout hideMenu) {
		this.hideMenu = hideMenu;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
	
}
