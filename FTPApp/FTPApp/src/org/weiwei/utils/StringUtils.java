package org.weiwei.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * 字符串处理
 * @author weiwei
 *
 */

public class StringUtils {
	private static final long BSize = 1024;
	private static final long KBSize = 1048576;
	private static final long MBSize = 1073741824;
//	private static final long GBSize = 1073741824;
	private static DecimalFormat df = new DecimalFormat(".00");
	
	/**
	 * 
	 */
	private static final String REGEX = "/";

	public StringUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String formatByte(long size) {

		if (size < BSize)
			return size + " B";
		if (1024 <= size && size < 1024 * 1024) {
			float result = size / (float) BSize;

			return df.format(result) + " KB";
		}
		if (size >= KBSize && size < MBSize) {
			float result = size / (float) KBSize;

			return df.format(result) + " MB";
		}
		if (size >= MBSize) {
			float result = size / (float) MBSize;
			return df.format(result) + " GB";
		}
//		if (size >= GBSize) {
//			Log.i("data 1111111111111", " /"+GBSize);
//			float result = size / (float) GBSize;
//			Log.i("data 1111111111111", result+"  /"+size+" /"+GBSize);
//			return df.format(result) + " TB";
//		}
		return "";

	}

	public static String getTime(int ms) {
		String time = "";
		String second = "";
		String minute = "";
		String hours = "";

		int total_sec = ms / 1000; // 总的秒数
		int total_min = total_sec / 60; // 总的分钟
		int total_hour = total_min / 60; // 总的小时
		int sec = total_sec % 60; // 秒
		int min = total_min % 60; // 分
		int hour = total_hour % 24;// 小时

		if (sec < 10) {
			second = "0" + sec;
		} else {
			second = sec + "";
		}

		if (min < 10) {
			minute = "0" + min;
		} else {
			minute = min + "";
		}

		time = minute + ":" + second;
		if (hour > 0) {
			time = hours + ":" + minute + ":" + second;
		}
		return time;
	}
	
	//获取图片的路径(解析)
	public static String[] getImageDir(String path){
		String[] datas = path.split(REGEX);//根据给定的正则表达式来拆分此字符串
//		int length = datas.length;//获取数组长度
//		if(length>=2){
//			return datas[length-2];
//		}else{
//			return datas[0];
//		}
		
		return datas;
	}
	
	//获取文件的父文件夹名
	public static String getParentDir(String path){
		File file = new File(path);
		return file.getParentFile().getName();//获取父文件夹的名称
	}
	
	public static String formatDate(long time){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		java.util.Date dt = new Date(time);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
		return sDateTime;
		
	}
	
	public static String formatDate(Date date){

		if(date!=null){
			return formatDate(date.getTime());
		}
		
		return "";
	}
}
