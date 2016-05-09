package org.weiwei.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable{

	private String pTitle;
	private String picUrl;
	private boolean choosed=false;
	
	public boolean isChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	public String getpTitle() {
		return pTitle;
	}
	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(pTitle);
		dest.writeString(picUrl);
	}
	
	public static final Parcelable.Creator<Picture> CREATOR = new Creator<Picture>() {
		
		@Override
		public Picture[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Picture[size];
		}
		
		@Override
		public Picture createFromParcel(Parcel source) {
			Picture p = new Picture();
			p.pTitle = source.readString();
			p.picUrl = source.readString();
			return p;
		}
	};
	
}
