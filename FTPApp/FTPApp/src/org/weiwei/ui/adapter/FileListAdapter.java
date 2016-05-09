package org.weiwei.ui.adapter;

import java.io.File;
import java.util.List;

import org.weiwei.ui.activity.R;
import org.weiwei.utils.MediaUtils;
import org.weiwei.utils.PhoneMsgUtils;
import org.weiwei.utils.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件列表适配器
 * @author weiwei
 *
 */
public class FileListAdapter extends BaseAdapter{

	private LayoutInflater mInflater;

	private File file;
	
	private File[] mDatas;
	
	private Context context;
	
	public FileListAdapter(Context context){
		this.context = context;
		mInflater = LayoutInflater.from(context);
		file = MediaUtils.getSDCardFile();
		fileFilter(file.listFiles());
	}
	
	public void fileFilter(File[] result){
		mDatas = MediaUtils.sortAndFilter(result);//处理数据
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mDatas==null)return 0;
		return mDatas.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_file_list, parent,false);
			holder.image = (ImageView) convertView.findViewById(R.id.id_listitem_file_list_image);
			holder.filename = (TextView) convertView.findViewById(R.id.id_listitem_file_list_name);
			holder.filesize = (TextView) convertView.findViewById(R.id.id_listitem_file_list_size);
			holder.modifyDate = (TextView)convertView.findViewById(R.id.id_listitem_file_list_modify_date);
			holder.sentBtn = (Button) convertView.findViewById(R.id.id_listitem_sent_button);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		File file = mDatas[position];
		
		holder.filename.setText(file.getName());

		if(file.isFile()){
			holder.image.setImageResource(R.drawable.ic_easytransfer_file);
			holder.filesize.setText(StringUtils.formatByte(file.length()));
			holder.modifyDate.setText(StringUtils.formatDate(file.lastModified()));
		}else if(file.isDirectory()){
			holder.image.setImageResource(R.drawable.ic_easytransfer_folder);
			holder.filesize.setText("");
			holder.modifyDate.setText(StringUtils.formatDate(file.lastModified()));
		}
		
		holder.sentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMessage("hello");
			}
		});
		
		return convertView;
	
	}
	
	protected final class ViewHolder{
		public ImageView image;
		public TextView filename;
		public TextView filesize;
		public TextView modifyDate;
		public Button sentBtn;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		fileFilter(file.listFiles());//处理数据
	}

	public File[] getmDatas() {
		return mDatas;
	}

	public void setmDatas(File[] mDatas) {
		this.mDatas = mDatas;
	}

	private void showMessage(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	
}
