package org.weiwei.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.weiwei.ftp.FTPFile;

import android.util.Log;

/**
 * FTP服务器端返回的文件处理
 * @author weiwei
 *
 */
public class FTPFileUtils {

	/**
	 * 对文件夹进行排序,自定义排序算法,文件夹在前,文件在后
	 * @param result
	 */
	public static FTPFile[] sortFile(FTPFile[] result){
		if(result==null) return null;
		Arrays.sort(result, new Comparator<FTPFile>() {

			@Override
			public int compare(FTPFile f0, FTPFile f1) {
				// TODO Auto-generated method stub
				if(f0 instanceof FTPFile&& f1 instanceof FTPFile){
					if(f0.getType()==FTPFile.TYPE_DIRECTORY&&f1.getType()!=FTPFile.TYPE_DIRECTORY){
						return -1; //返回负数,表示f0应该排在f1前面
					}else if(f0.getType()!=FTPFile.TYPE_DIRECTORY&&f1.getType()==FTPFile.TYPE_DIRECTORY){
						return 1; //返回正数表示f0排在f1后面
					}else{
						return f0.getName().compareTo(f1.getName());//如果类别相同表示按字典排序
					}
			}
			
				return 0;
			}
		});
		return result;
	}
	
	/**
	 * 过滤掉隐藏文件
	 * @param files
	 */
	public static FTPFile[] FilterHiddenFile(FTPFile[] files){
		if(files==null) return null;
		List<FTPFile> myfile = new ArrayList<FTPFile>();
		
		for(int i=0;i<files.length;i++){
			if(files[i].isHidden()){

				continue;
			}
			FTPFile file = files[i];
			myfile.add(file);
		}
		
		return  myfile.toArray(new FTPFile[myfile.size()]); //返回过滤后文件列表
	}
	
	/**
	 * 过滤隐藏文件并排序
	 * @param files
	 * @return
	 */
	public static FTPFile[] sortAndFilter(FTPFile[] files){
		if(files!=null){
			FTPFile[] result = FilterHiddenFile(files);
			return sortFile(result);
		}
		
		return null;
	}
	
}
