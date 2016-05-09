package org.weiwei.ui.fragment;

import java.io.File;

import org.weiwei.application.MyApplication;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.activity.R;
import org.weiwei.ui.adapter.FileListAdapter;
import org.weiwei.ui.view.EmptyLinearLayout;
import org.weiwei.ui.view.PathBar;
import org.weiwei.utils.MediaUtils;
import org.weiwei.utils.StringUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FTotalFragment extends Fragment {

	private String SDCardRoot; // SDCard根目录

	private ListView mFileList;

	private View view;

	private FileListAdapter mFileAdapter;

	private PathBar pathBar;
	
	private CoreService coreService;
	
	private MyApplication myApp;
	
	private EmptyLinearLayout emptyLayout;
	
	private User user;
	/**
	 * 当前文件夹中的文件
	 */
	private File[] currentFileList;
	/**
	 * 
	 * 当前文件夹
	 */
	private File currentFile;
	
	public FTotalFragment(Context context) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_file_list, container, false);
		initServer();
		initView();
		return view;
	}
	
	public void initServer(){
		myApp = (MyApplication) getActivity().getApplication();
		coreService = myApp.getCoreService();
		user = myApp.getUser();
	}

	public void initView() {
		//初始化空页面
		emptyLayout = (EmptyLinearLayout) view.findViewById(R.id.id_empty_layout);
		emptyLayout.setImage(R.drawable.empty_file);
		emptyLayout.setText("此文件夹为空");
		emptyLayout.setVisibility(View.GONE);
		
		
		pathBar = (PathBar) view.findViewById(R.id.id_fragment_file_list_path_bar);
		mFileList = (ListView) view.findViewById(R.id.id_fragment_file_list_lv);
		mFileAdapter = new FileListAdapter(getActivity());
		mFileList.setAdapter(mFileAdapter);
		mFileList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentFileList = mFileAdapter.getmDatas(); // 获取当前的文件夹经处理后的元素
				currentFile = mFileAdapter.getFile();//当前文件夹
				if (currentFileList[position].isDirectory()) {
					// 如果当前所选是文件夹,则切换目录
//					mFileAdapter.setmDatas(MediaUtils.sortAndFilter(currentFileList[position].listFiles()));
					mFileAdapter.setFile(currentFileList[position]);
					mFileAdapter.notifyDataSetChanged(); // 更新数据
					String path = currentFileList[position].getAbsolutePath();// 获取绝对路径
					refreshPathBar(path);
					emptyLayout.showEmpty(mFileAdapter.getmDatas().length);//根据数据数展示空页面
				}
				
			}
		});

	}

	/**
	 * 返回到上一个目录
	 */
	public void backParentDir(){
		if(currentFile==null)return;
		currentFile = mFileAdapter.getFile();//获取当前文件夹
		if(isRoot(currentFile)) return; //如果当前文件夹是根目录，则返回
		File parent = currentFile.getParentFile();//当前文件夹的父文件夹
		
		mFileAdapter.setFile(parent);//更改当前目录为原文件的父目录
		mFileAdapter.notifyDataSetChanged();
		emptyLayout.showEmpty(mFileAdapter.getmDatas().length); //
		refreshPathBar(parent.getAbsolutePath());

	}
	
	/**
	 * 判断当前文件夹是否是SDCard 根目录
	 * @return
	 */
	public boolean isRoot(File file){
		if(file.getAbsolutePath().equals(MediaUtils.getSDCardFile().getAbsolutePath())){
			return true;
		}
		return false;
	}
	
	public void refreshPathBar(String path){
		pathBar.setmDatas(StringUtils.getImageDir(path));
		pathBar.notifyDataChanged();
	}
		
}
