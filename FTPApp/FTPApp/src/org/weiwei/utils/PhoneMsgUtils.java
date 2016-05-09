package org.weiwei.utils;

import java.io.File;

import android.os.Environment;

/**
 * 手机信息状态检查
 * @author weiwei
 *
 */
public class PhoneMsgUtils {
	
	public static final String SDCARD_UNMOUNT = "sdcard is unmount";

	public PhoneMsgUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取本地SD卡的路径
	 * @return
	 */
	public static String getSDCardPath(){
		//获取SDCard的路径
		if(isSDCardExist()){
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return SDCARD_UNMOUNT;
	}
	
	/**
	 * SD卡是否存在
	 * @return
	 */
	public static boolean isSDCardExist(){
		//判断当前SD卡是否存在
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}
	
	public static File getSDCardDirectory(){
		if(isSDCardExist()){
			return Environment.getExternalStorageDirectory();
		}
		return null;
	}
	
}
