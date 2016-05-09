package org.weiwei.ui.fragment;

import org.weiwei.ui.activity.R;
import org.weiwei.utils.MediaUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FileFragment extends Fragment implements OnClickListener{

	private ListView mFileList;//
	
	private LinearLayout mDirBar; //路径条
	
	private MediaUtils  mUtils;
	
	private Fragment mTotalFragment,mTypeFragment; //子fragment
	
	private LinearLayout leftLayout,rightLayout;
	
	/**
	 * fragment管理
	 */
	private FragmentManager fm;
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_file, container, false);
		mUtils = new MediaUtils(getActivity());
		setDefaultFragment();//
		initView();
		return view;
	}
	
	/**
	 * 设置默认的Fragment
	 */
	private void setDefaultFragment(){
		fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		mTotalFragment = new FTotalFragment(getActivity());
		mTypeFragment = new FTypeFragment(getActivity());
		transaction.replace(R.id.id_fragment_file_frame, mTotalFragment);
		transaction.add(R.id.id_fragment_file_frame, mTypeFragment);
		transaction.hide(mTypeFragment);
		transaction.commit();
	}

	public void initView(){
		leftLayout = (LinearLayout) view.findViewById(R.id.id_sub_bar_left);
		rightLayout = (LinearLayout) view.findViewById(R.id.id_sub_bar_right);
		
		leftLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fm.beginTransaction();
		switch(v.getId()){
		case R.id.id_sub_bar_left:{
			transaction.hide(mTotalFragment);
			transaction.addToBackStack(null);
			transaction.show(mTypeFragment);
			break;
		}

		case R.id.id_sub_bar_right:{
			transaction.hide(mTypeFragment);
			transaction.addToBackStack(null);
			transaction.show(mTotalFragment);
			break;
		}

		}
		
		transaction.commit();
	}

	public Fragment getmTotalFragment() {
		return mTotalFragment;
	}

	public void setmTotalFragment(Fragment mTotalFragment) {
		this.mTotalFragment = mTotalFragment;
	}

	public Fragment getmTypeFragment() {
		return mTypeFragment;
	}

	public void setmTypeFragment(Fragment mTypeFragment) {
		this.mTypeFragment = mTypeFragment;
	}
	
	
	
}
