package com.zichao.l04_app04_blacklist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, "Zichao1.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("TAG", "DBHelper onCreate()");
		String sql = "CREATE TABLE blacklist(_id integer primary key autoincrement, number varchar)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
