package org.weiwei.utils;

import java.util.Calendar;

/**
 * 时间工具类
 * @author weiwei
 *
 */
public class TimeUtils {

	
	public TimeUtils() {
		// TODO Auto-generated constructor stub
	}

	//获取当前时间
	public static String getCurrentTime(){
//		String time = "";
		Calendar c = Calendar.getInstance();
		return formatDate(c.getTimeInMillis());
//		int hour = c.get(Calendar.HOUR_OF_DAY);
//		int min = c.get(Calendar.MINUTE);
//		time = hour+":"+min;
//		return time;
	}
	
	public static String formatDate(long time){
		return StringUtils.formatDate(time);
	}
}
