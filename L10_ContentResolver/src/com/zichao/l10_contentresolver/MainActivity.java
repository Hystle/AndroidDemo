package com.zichao.l10_contentresolver;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/*
	 * 通过ContentResolver调用ContentProvider查询所有记录
	 */
	public void query(View v) {
		// 1. 得到contentResolver对象
		ContentResolver resolver = getContentResolver();
		// 2. 调用其query, 得到cursor
		Uri uri = Uri.parse("content://com.zichao.l10_contentprovider.personprovider/person");
		Cursor cursor = resolver.query(uri, null, null, null, null);	// projection: 查询字段; selection: 指定部分行
		// 3. 取出cursor中的数据
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			Toast.makeText(this, id+": "+name, Toast.LENGTH_SHORT).show();
		}
		cursor.close();
	}

	/*
	 * 通过ContentResolver调用ContentProvider插入一条记录
	 */
	public void insert(View v) {
		ContentResolver resolver = getContentResolver();
		// 准备Uri
		Uri uri = Uri.parse("content://com.zichao.l10_contentprovider.personprovider/person/");
		// 准备参数
		ContentValues values = new ContentValues();
		values.put("name", "JACK");
		// 执行insert,得到带id的Uri
		uri = resolver.insert(uri, values);
		Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
	}

	/*
	 * 通过ContentResolver调用ContentProvider更新一条记录
	 */
	public void update(View v) {
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://com.zichao.l10_contentprovider.personprovider/person/3");
		ContentValues values = new ContentValues();
		values.put("name", "ZACK");
		// 根据id更新不需要其他参数
		int count = resolver.update(uri, values, null, null);
		Toast.makeText(this, "Count: " + count, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 通过ContentResolver调用ContentProvider删除一条记录
	 */
	public void delete(View v) {
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://com.zichao.l10_contentprovider.personprovider/person/3");
		// 根据id删除
		int count = resolver.delete(uri, null, null);
		Toast.makeText(this, "Count: " + count, Toast.LENGTH_SHORT).show();
	}

                                                               
}
