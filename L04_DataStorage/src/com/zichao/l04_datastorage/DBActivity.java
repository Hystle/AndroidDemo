package com.zichao.l04_datastorage;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DBActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
	}

	/*
	 * 创建数据库
	 */
	public void testCreateDB(View v) {
		DBHelper dbHelper = new DBHelper(this, 1);
		// 获取数据库连接时才会创建数据库
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Toast.makeText(this, "创建数据库", Toast.LENGTH_SHORT).show();
	}

	/*
	 * 更新数据库
	 */
	public void testUpdateDB(View v) {
		DBHelper dbHelper = new DBHelper(this, 2);
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Toast.makeText(this, "更新数据库", Toast.LENGTH_SHORT).show();
	}

	/*
	 * 添加记录
	 */
	public void testInsert(View v) {
		// 1. 得到连接
		DBHelper dbHelper = new DBHelper(this, 2);
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		// 2. 执行INSERT
		// 1) 准备数据: ContentValues内部为HashMap
		ContentValues values = new ContentValues();
		values.put("name", "P3");
		values.put("age", "13");
		// 2) 执行插入: 返回值为id
		long id = database.insert("person", null, values);
		// 4. 关闭连接
		database.close();
		// 3. 提示
		Toast.makeText(this, "添加记录 id=" + id, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 更新
	 */
	public void testUpdate(View v) {
		DBHelper helper = new DBHelper(this, 2);
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", "P4");
		values.put("age", 14);
		/*
		 * 执行更新
		 * int update(String table, ContentValues values, String whereClause, String[] whereArgs)
		 * whereClause是带占位符的WHERE语句; whereArgs是字符串数组为其赋值
		 * 返回值为修改的记录条数
		 */
		int count = db.update("person", values, "_id=?", new String[]{"4"});
		db.close();
		Toast.makeText(this, "更新记录 条数=" + count, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 删除
	 */
	public void testDelete(View v) {
		DBHelper helper = new DBHelper(this, 2);
		SQLiteDatabase db = helper.getReadableDatabase();
		// 执行删除: 也可以不使用占位符
		int count = db.delete("person", "_id=4", null);
		db.close();
		Toast.makeText(this, "删除记录 条数=" + count, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 查询
	 */
	public void testQuery(View v) {
		DBHelper helper = new DBHelper(this, 2);
		SQLiteDatabase db = helper.getReadableDatabase();
		// 执行查询: SELECT * FROM person: 查询条件为*时,后面参数全为null
//		Cursor cursor = db.query("person", null, null, null, null, null, null);
		Cursor cursor = db.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
		// 得到查询结果cursor中的记录数
		int count = cursor.getCount();
		// 取出cursor中所有的数据
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			Log.e("TAG", id + "--" + name + "--" + age);
		}
		// 关闭cursor
		cursor.close();
		db.close();
		Toast.makeText(this, "查询记录 条数=" + count, Toast.LENGTH_SHORT).show();
	}

	/*
	 * 测试事务处理:
	 * 1. 开启事务(获取连接后)
	 * 2. 设置事务成功(在全部正常执行完后)
	 * 3. 结束事务(finally中)
	 */
	public void testTransaction(View v) {
		SQLiteDatabase db = null;
		try {
			DBHelper helper = new DBHelper(this, 2);
			db = helper.getReadableDatabase();
			// 1. 开启事务
			db.beginTransaction();
			ContentValues values = new ContentValues();
			// 2. 操作1
			values.put("age", 14);
			int count1 = db.update("person", values, "_id=?", new String[]{"1"});
			Log.e("TAG", "count1=" + count1);
			// 3. 出现异常
			boolean flag = true;
			if(flag){
				throw new RuntimeException("出异常了");
			}
			// 4. 操作2
			values.put("age", 16);
			int count2 = db.update("person", values, "_id=?", new String[]{"5"});
			Log.e("TAG", "count2=" + count2);
			// 5. 设置事务成功
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "出异常了", Toast.LENGTH_SHORT).show();
		}
		finally{
			if(db!=null){
				db.endTransaction();
				db.close();
			}
		}
	}

}
