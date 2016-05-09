package org.weiwei.ui.fragment;

import org.weiwei.ui.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VideoFragment extends Fragment{
	
	private TextView leftTextView,rightTextView;
	private LinearLayout leftLayout,rightLayout;
	private View view;
	private Fragment musicFragment,movieFragment;
	
	public VideoFragment(){

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_video, container, false);
		initView();
		setDefaultFragment();
		return view;
	}
	
	
	public void initView(){
		leftTextView = (TextView) view.findViewById(R.id.id_sub_bar_left_tv);
		leftTextView.setText(R.string.music);
		rightTextView = (TextView) view.findViewById(R.id.id_sub_bar_right_tv);
		rightTextView.setText(R.string.mp4);
		
		leftLayout = (LinearLayout) view.findViewById(R.id.id_sub_bar_left);
		leftLayout.setOnClickListener(new layoutClick());
		rightLayout = (LinearLayout)view.findViewById(R.id.id_sub_bar_right);
		rightLayout.setOnClickListener(new layoutClick());
	}
	
	private void setDefaultFragment() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		musicFragment = new VmusicFragment(getActivity());
		movieFragment = new VmovieFragment(getActivity());
		transaction.replace(R.id.id_fragment_video_frame, musicFragment);
		transaction.add(R.id.id_fragment_video_frame, movieFragment);
		transaction.hide(movieFragment);
		transaction.commit();
	}
	
	
	
	private class layoutClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			switch (v.getId()) {
			case R.id.id_sub_bar_left:
				if (musicFragment == null) {
					musicFragment = new VmusicFragment(getActivity());
				}
//				if(musicFragment.isVisible()){
//					return;
//				}
//				transaction.replace(R.id.id_fragment_video_frame, musicFragment);
				transaction.hide(movieFragment);
				transaction.addToBackStack(null);
				transaction.show(musicFragment);

//				transaction.replace(R.id.id_fragment_video_frame, musicFragment);
				break;

			case R.id.id_sub_bar_right:
				if (movieFragment == null) {
					movieFragment = new VmovieFragment(getActivity());

				}
//				if(movieFragment.isVisible()){
//					return;
//				}
//				transaction.replace(R.id.id_fragment_video_frame, movieFragment);
				transaction.hide(musicFragment);
				transaction.addToBackStack(null);
				transaction.show(movieFragment);
				
				break;
			}
			transaction.commit();

		}
		
	}
}
