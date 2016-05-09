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

public class PictureFragment extends Fragment implements OnClickListener {
	private Fragment pPhotoFragment, pPictureFragment;
	private LinearLayout photoLayout, pictureLayout; // 相册和图库
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture, container, false);
		initView();
		setDefaultFragment();
		return view;
	}

	public void initView() {
		photoLayout = (LinearLayout) view
				.findViewById(R.id.id_fragment_picture_photo);
		pictureLayout = (LinearLayout) view
				.findViewById(R.id.id_fragment_picture_picture);
		photoLayout.setOnClickListener(this);
		pictureLayout.setOnClickListener(this);
	}

	private void setDefaultFragment() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		pPhotoFragment = new PphotoFragment(getActivity());
		pPictureFragment = new PpictureFragment(getActivity());
		transaction.replace(R.id.id_fragment_picture_frame, pPhotoFragment);
		transaction.add(R.id.id_fragment_picture_frame, pPictureFragment);
		transaction.hide(pPictureFragment);
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		switch (v.getId()) {
		case R.id.id_fragment_picture_photo:
//			if (pPhotoFragment == null) {
//				pPhotoFragment = new PphotoFragment(getActivity());
//			}
//			if(pPhotoFragment.isVisible()){
//				return;
//			}
			transaction.hide(pPictureFragment);
			transaction.addToBackStack(null);
			transaction.show(pPhotoFragment);

//			transaction.replace(R.id.id_fragment_picture_frame, pPhotoFragment);
			break;

		case R.id.id_fragment_picture_picture:
//			if (pPictureFragment == null) {
//				pPictureFragment = new PpictureFragment(getActivity());
//			}
//			if(pPictureFragment.isVisible()){
//				return;
//			}
			transaction.hide(pPhotoFragment);
			transaction.addToBackStack(null);
			transaction.show(pPictureFragment);

//			transaction.replace(R.id.id_fragment_picture_frame, pPictureFragment);
			break;
		}

		transaction.commit();

	}
}
