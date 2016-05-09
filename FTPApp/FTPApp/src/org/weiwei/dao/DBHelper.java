package org.weiwei.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库类
 * @author weiwei
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	/**
	 * 当前数据库版本
	 */
	private static final int VERSION = 1;
	/**
	 * 数据库名称
	 */
	private static final String DATABASE_NAME = "ftpapp";
	/**
	 * 创建用户表
	 */
	private static final String CREATE_USER = "create table user(" +
			"id integer not null primary key autoincrement," +
			"host varchar(50) not null," +
			"port varchar(10) not null," +
			"user varchar(50) not null," +
			"pass varchar(50) not null," +
			"servername varchar(50)," +
			"lastconnecttime varchar(100) not null," +
			"state integer not null," +
			"exist integer not null)";
	/**
	 * 创建任务表
	 */
	private static final String CREATE_TASK = "create table task(" +
			"id integer not null primary key autoincrement," +
			"type varchar(10) not null," +
			"taskname varchar(50) not null," +
			"remote varchar(100) not null," +
			"local varchar(100) not null," +
			"progressvalue integer not null," +
			"taskstate bool not null," +
			"filesize integer not null," +
			"restartat integer not null"+
			"userid integer not null references user(id))";
	

	

	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context){
		super(context,DATABASE_NAME,null,VERSION);
	}
	
	//当数据库被创建的时候
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);		
		db.execSQL(CREATE_TASK);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
