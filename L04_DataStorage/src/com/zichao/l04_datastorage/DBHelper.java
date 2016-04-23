package com.zichao.l04_datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作的帮助类
 * 什么情况下会创建数据库?
 * 1) 数据库文件不存在
 * 2) 连接数据库
 * @author Zach
 *
 */
public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context, int version) {
		super(context, "zichao.db", null, version);
	}
	
	/**
	 * 1. 什么时候调用?
	 * 当数据库文件创建时调用(1次)
	 * 2. 在此方法中做什么?
	 * 1) 建表
	 * 2) 插入一些初始化数据
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建数据库
		String sql = "CREATE TABLE person (_id integer primary key autoincrement, name varchar, age int)";
		// 执行sql语句
		db.execSQL(sql);
		// 插入一些初始数据
		db.execSQL("INSERT INTO person (name, age) VALUES ('P1',11)");
		db.execSQL("INSERT INTO person (name, age) VALUES ('P2',12)");
		db.execSQL("INSERT INTO person (name, age) VALUES ('P3',13)");
	}
	
	/**
	 * 数据库版本更新时调用(当传入的版本号大于当前的版本号)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("TAG", "DBHelper onUpgrade()");
	}

}
