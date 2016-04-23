package com.zichao.l04_datastorage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SpActivity extends Activity {
	
	private EditText et_sp_key;
	private EditText et_sp_value;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sp);
		
		et_sp_key = (EditText) findViewById(R.id.et_sp_key);
		et_sp_value = (EditText) findViewById(R.id.et_sp_value);
		
		// 1. 创建SharedPerferences对象
		// getSharedPreferences(String name, int mode): name为文件名,省略.xml;mode一般都设置为private
		sp = getSharedPreferences("ZICHAO", Context.MODE_PRIVATE);
	}
	
	public void save(View v){
		String key = et_sp_key.getText().toString();
		String value = et_sp_value.getText().toString();
		// 2. 只能使用Editor编辑SharedPerferences文件
		Editor editor = sp.edit();
		// 3. 放入键值对,并提交修改
		editor.putString(key, value).commit();
		Toast.makeText(this, "已保存", 0).show();
	}
	
	public void read(View v){
		String key = et_sp_key.getText().toString();
		// sharedPreferences的getXxx方法
		// getString(String key, String defValue): defValue指定若没有找到时的返回值
		String value = sp.getString(key, null);
		if(value == null){
			Toast.makeText(this, "未找到相应值", Toast.LENGTH_SHORT).show();
		}
		et_sp_value.setText(value);
	}
}
