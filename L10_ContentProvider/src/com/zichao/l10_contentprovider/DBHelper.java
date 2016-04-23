package com.zichao.l10_contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "L10_contentProvider.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 建表
		db.execSQL("CREATE TABLE person(_id integer primary key autoincrement, name varchar)");
		Log.e("TAG", "onCreate()");
		// 插入初始数据
		db.execSQL("INSERT INTO person(name) VALUES ('TOM')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
