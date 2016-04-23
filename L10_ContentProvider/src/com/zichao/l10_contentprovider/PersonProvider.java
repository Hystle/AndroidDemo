package com.zichao.l10_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class PersonProvider extends ContentProvider{
	//用于存放所有合法的Uri容器
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	/*
	 *  保存一些合法的Uri
	 *  1. content://com.zichao.l10_contentprovider.personprovider/person
	 *  2. content://com.zichao.l10_contentprovider.personprovider/person/3, 其中参数id可选
	 *  3. addURI(String authority, String path, int code):
	 *  	authority: 与manifest文件中配置的相同
	 *  	path: 数据库表名+可选id,注意使用#代表匹配任意数字
	 *  	code: 标识号
	 */
	static{
		matcher.addURI("com.zichao.l10_contentprovider.personprovider", "/person", 1);
		matcher.addURI("com.zichao.l10_contentprovider.personprovider", "/person/#", 2);
	}
	
	public PersonProvider() {
		Log.e("TAG", "PersonProvider's constructor");
	}
	
	private DBHelper helper;
	@Override
	public boolean onCreate() {
		Log.e("TAG", "PersonProvider's onCreate()");
		// 需要context对象:一般方法1)this是不是Context 2)传入 3)getContext
		helper = new DBHelper(getContext());
		return false;
	}
	
	/*
	 * 查询操作:有无id两种情况
	 * 数据库连接不能关闭,否则resolver无法得到cursor
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.e("TAG", "PersonProvider's query()");
		// 得到连接对象
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor;
		// 根据Uri容器中的Uri与实际传入的进行匹配, 返回code
		int code = matcher.match(uri);
		// 根据code判断Uri是否合法
		if(code == 1){	// 不含id查询
			cursor = db.query("person", projection, selection, selectionArgs, null, null, null);
			return cursor;
		}
		else if (code == 2){	// 带id查询
			// 从uri中取出id
			long id = ContentUris.parseId(uri);
			// 查询
			cursor = db.query("person", projection, "_id=?", new String[]{id+""}, null, null, null);
		}
		else{
			throw new RuntimeException("查询URI不合法");
		}
		return cursor;
	}

	/*
	 * 插入:只有不带id一种情况
	 * 返回带id的Uri
	 * 数据库连接可以关闭
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.e("TAG", "PersonProvider's insert()");
		SQLiteDatabase db = helper.getReadableDatabase();
		int code = matcher.match(uri);
		if(code == 1){
			// 根据插入的数据得到id
			long id = db.insert("person", null, values);
			// 将id添加到URI中
			uri = ContentUris.withAppendedId(uri, id);
			db.close();
		}
		else{
			db.close();
			throw new RuntimeException("查询URI不合法");
		}
		return uri;
	}
	
	/*
	 * 删除: 
	 * 1) 根据id删除 -> 带id
	 * 2) 根据其他条件删除(如name) -> 不带id
	 * 数据库可以关闭连接
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.e("TAG", "PersonProvider's delete()");
		SQLiteDatabase db = helper.getReadableDatabase();
		int code = matcher.match(uri);
		int count = -1;
		if(code == 1){
			// 不带id删除时, 需要参考传入的参数
			count = db.delete("person", selection, selectionArgs);
		}
		else if(code == 2){
			long id = ContentUris.parseId(uri);
			count = db.delete("person", "_id=" + id, null);
		}
		else{
			db.close();
			throw new RuntimeException("查询URI不合法");
		}
		db.close();
		return count;
	}

	/*
	 * 更新: 
	 * 1) 根据id更新 -> 带id
	 * 2) 根据其他条件更新(如修改所有name都为Jerry) -> 不带id
	 * 数据库可以关闭连接
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.e("TAG", "PersonProvider's update()");
		SQLiteDatabase db = helper.getReadableDatabase();
		int code = matcher.match(uri);
		int count = -1;
		if(code == 1){
			// 不带id的更新
			count = db.update("person", values, selection, selectionArgs);
		}
		else if(code == 2){
			// 带id更新
			long id = ContentUris.parseId(uri);
			count = db.update("person", values, "_id=" + id, null);
		}
		else{
			db.close();
			throw new RuntimeException("查询URI不合法");
		}
		db.close();
		return count;
	}

	@Override
	public String getType(Uri uri) {
		Log.e("TAG", "PersonProvider's getType()");
		return null;
	}
}
