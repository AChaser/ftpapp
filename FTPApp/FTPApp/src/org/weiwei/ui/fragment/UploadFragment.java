package org.weiwei.ui.fragment;

import org.weiwei.application.MyApplication;
import org.weiwei.model.User;
import org.weiwei.service.CoreService;
import org.weiwei.ui.activity.R;
import org.weiwei.ui.adapter.TransListAdapter;
import org.weiwei.ui.view.EmptyLinearLayout;
import org.weiwei.ui.view.PinnedHeaderListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 上传界面
 * @author weiwei
 *
 */
public class UploadFragment extends Fragment{
	
	private View view;
	
	private ListView mListView;
	
	private TransListAdapter mAdapter;
	
	private CoreService coreService;
	
	private MyApplication myApp;
	
	private User user;
	
	private EmptyLinearLayout emptyLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_upload, container, false);
		initServer();
		initView();
		return view;
	}
	
	private void initServer(){
		myApp = (MyApplication) getActivity().getApplication();
		coreService = myApp.getCoreService();
		user = myApp.getUser();
	}
	
	public void initView(){
		mListView = (ListView) view.findViewById(R.id.id_fragment_upload_lv);
		mAdapter = new TransListAdapter(getActivity(), myApp, TransListAdapter.TRAN_LIST_UP);
		mAdapter.setListView(mListView);
		mAdapter.setHandler(); //设置handler
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		emptyLayout = (EmptyLinearLayout)view.findViewById(R.id.id_empty_layout);
		emptyLayout.showEmpty(mAdapter.getmDatas().size()+mAdapter.getDoneList().size());
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("upload", "onResume");
		mAdapter.resetList();
	}

	
}
