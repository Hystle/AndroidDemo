package com.zichao.l04_app04_blacklist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BlackListDAO {
	private DBHelper helper;
	
	public BlackListDAO(Context context){
		helper = new DBHelper(context);
	}
	/*
	 * Insert
	 */
	public void add(BlackListItem blackListItem){
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", blackListItem.getNumber());
		long id = db.insert("blackList", null, values);
		Log.i("TAG", "id: "+ id);
		// 为新添加的item设置id
		blackListItem.setId((int)id);
		db.close();
	}
	/*
	 * Update
	 */
	public void update(BlackListItem blackListItem){
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", blackListItem.getNumber());
		int count = db.update("blackList", values, "_id="+blackListItem.getId(), null);
		Log.i("TAG", "count: " + count);
		db.close();
	}
	/*
	 * Delete
	 */
	public void deleteById(int id){
		SQLiteDatabase db = helper.getReadableDatabase();
		int count = db.delete("blackList", "_id=?", new String[]{id+""});
		Log.i("TAG", "count: "+count);
		db.close();
	}
	/*
	 * Retrieve
	 */
	public List<BlackListItem> getAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		// 按id倒序排列:使列表中顶部为新添加的条目
		Cursor cursor = db.query("blackList", null, null, null, null, null, "_id DESC");
		List<BlackListItem> list = new ArrayList<BlackListItem>();
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String number = cursor.getString(1);
			list.add(new BlackListItem(id, number));
		}
		cursor.close();
		db.close();
		return list;
	}
}
