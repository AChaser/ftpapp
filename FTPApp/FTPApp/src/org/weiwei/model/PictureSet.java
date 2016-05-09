package org.weiwei.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片集合
 * @author weiwei
 *
 */
public class PictureSet implements Parcelable{

	public String dir;
	public List<Picture> picList; //图片集合
	
	public PictureSet(){
		picList = new ArrayList<Picture>();
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<Picture> getPicList() {
		return picList;
	}

	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}

	@Override
	public int describeContents() {
		//内容描述接口
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// 写入接口函数，打包
		dest.writeString(dir);
		dest.writeList(picList);
		
	}
	
	public static final Parcelable.Creator<PictureSet> CREATOR = new Parcelable.Creator<PictureSet>() {

		@SuppressWarnings("unchecked")
		@Override
		public PictureSet createFromParcel(Parcel source) {
			PictureSet picSet = new PictureSet();
			picSet.dir = source.readString();
			picSet.picList = source.readArrayList(Picture.class.getClassLoader());
			return picSet;
		}

		@Override
		public PictureSet[] newArray(int size) {
			
			return new PictureSet[size];
		}
	};
	

}
