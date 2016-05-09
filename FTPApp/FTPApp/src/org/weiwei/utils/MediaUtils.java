package org.weiwei.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.weiwei.model.Audio;
import org.weiwei.model.Picture;
import org.weiwei.model.PictureSet;
import org.weiwei.model.Video;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

public class MediaUtils {
	
	private Context context;
	private ContentResolver mContentResolver;

	public MediaUtils(Context context) {
		this.context = context;
		mContentResolver = context.getContentResolver();
	}

	/**
	 * 获取手机中的图片信息
	 * @param handler
	 * @return
	 */
	public List<PictureSet> getPicture(final Handler handler){
		final List<PictureSet> result = new ArrayList<PictureSet>(); //图片集合
		new Thread(new Runnable(){

			@Override
			public void run() {
				Uri pictureUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				Cursor cursor = mContentResolver.query(pictureUri, null, null, null, MediaStore.Images.Media.DATE_MODIFIED);
				while(cursor.moveToNext()){
					//获取图片的名称
					String pTitle = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
					//获取图片的路径
					String picUrl = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));  

					Picture pic = new Picture();
					pic.setpTitle(pTitle);
					pic.setPicUrl(picUrl);
					
					addPicture(pic,result);	//往图片集合中添加元素
				}
				Message msg = Message.obtain();
				msg.obj = result;
				handler.sendMessage(msg);
			}
			
		}).start();
		
		
		return result;
		
	}
	
	/**
	 * 获取手机中的歌曲信息
	 * @param handler
	 * @return
	 */
	public List<Audio> getAudio(final Handler handler){
		final List<Audio> result = new ArrayList<Audio>();
		new Thread(new Runnable(){
			@Override
			public void run() {

				Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				Cursor cursor = mContentResolver.query(audioUri, null, null, null, MediaStore.Audio.Media.DATE_MODIFIED);
				while(cursor.moveToNext()){
					Audio audio = new Audio();
//			           歌曲的名称 ：MediaStore.Audio.Media.TITLE
			        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));  
//			           歌曲的专辑名：MediaStore.Audio.Media.ALBUM 
			        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));  
//			           歌曲的歌手名： MediaStore.Audio.Media.ARTIST 
			        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));  
//			           歌曲文件的路径 ：MediaStore.Audio.Media.DATA 
			        String dataurl = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));  
//			          歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
			        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));  
//			          歌曲文件的大小 ：MediaStore.Audio.Media.SIZE 
			        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)); 
			        audio.setAlbum(album);
			        audio.setArtist(artist);
			        audio.setDataurl(dataurl);
			        audio.setDuration(duration);
			        audio.setSize(size);
			        audio.setTitle(title);
			        result.add(audio);
				}
				handler.sendEmptyMessage(0);
			}

		}).start();

		return result;
	}
	
	/**
	 * 获取手机中的影音信息
	 * @param handler
	 * @return
	 */
	
	public List<Video> getVideo(final Handler handler){
		final List<Video> result = new ArrayList<Video>();;
		new Thread(new Runnable(){
			@Override
			public void run() {

				Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				Cursor cursor = mContentResolver.query(videoUri, null, null, null, MediaStore.Video.Media.DATE_MODIFIED);
				while(cursor.moveToNext()){
					//视频路径
					String dataurl = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
					//视频名称
					String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
//			          视频的总播放时长 ：MediaStore.Audio.Media.DURATION
			        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));  
//			         视频文件的大小 ：MediaStore.Audio.Media.SIZE 
			        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); 
//			           视频作者名： MediaStore.Audio.Media.ARTIST 
			        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); 
			       
			        Video video = new Video();
			        video.setArtist(artist);
			        video.setDataurl(dataurl);
			        video.setDuration(duration);
			        video.setSize(size);
			        video.setTitle(title);
			        result.add(video);
				}
				handler.sendEmptyMessage(0);
			}
		}).start();

		return result;
	}
	
	
	public static File getSDCardFile(){
		//获取SD卡路径
		File sdRoot = null;
		
		//检测SD卡是否存在
		String sdPath = PhoneMsgUtils.getSDCardPath();
		if(!sdPath.equals(PhoneMsgUtils.SDCARD_UNMOUNT)){
			sdRoot  = PhoneMsgUtils.getSDCardDirectory();
		}
		
		return 	sdRoot;
	}
	
	/**
	 * 对文件夹进行排序,自定义排序算法,文件夹在前,文件在后
	 * @param result
	 */
	public static File[] sortFile(File[] result){
		if(result==null) return null;
		Arrays.sort(result, new Comparator<File>() {

			@Override
			public int compare(File f0, File f1) {
				// TODO Auto-generated method stub
				if(f0 instanceof File&& f1 instanceof File){
					if(f0.isDirectory()&&!f1.isDirectory()){
						return -1; //返回负数,表示f0应该排在f1前面
					}else if(!f0.isDirectory()&&f1.isDirectory()){
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
	public static File[] FilterHiddenFile(File[] files){
		if(files==null) return null;
		List<File> myfile = new ArrayList<File>();
		
		for(int i=0;i<files.length;i++){
			if(files[i].isHidden()){
//				Log.i("TEST",rootList[i].getName());
				continue;
			}
			File file = files[i];
			myfile.add(file);
		}
		
		return  myfile.toArray(new File[myfile.size()]); //返回过滤后文件列表
	}
	
	/**
	 * 过滤隐藏文件并排序
	 * @param files
	 * @return
	 */
	public static File[] sortAndFilter(File[] files){
		if(files!=null){
			File[] result = FilterHiddenFile(files);
			return sortFile(result);
		}
		
		return null;
	}
	
	
	/**
	 * 该方法获取图片所在文件夹，加入到集合中去
	 * @param pic 要加入的picture元素
	 * @param result 
	 */	
	private void addPicture(Picture pic,List<PictureSet> result){
		Picture p = pic;
//		String dir = StringUtils.getImageDir(p.getPicUrl());//获取图片路径所在的父文件夹
		String dir = StringUtils.getParentDir(p.getPicUrl());
		Iterator<PictureSet> it = result.iterator();
		while(it.hasNext()){
			PictureSet picSet = it.next();
			if(picSet.dir.equals(dir)){
				//如果集合中存在则加入
				picSet.getPicList().add(p);
				return;
			}
		}
		
		//集合中不存在
		PictureSet picSet = new PictureSet();
		picSet.setDir(dir);
		picSet.getPicList().add(p);	
		result.add(picSet);
	}
}
