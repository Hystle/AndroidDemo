package com.zichao.l02_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends Activity {

	private EditText edit_second_msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 处理前一个Activity传来的数据
		setContentView(R.layout.activity_second);
		edit_second_msg = (EditText) findViewById(R.id.et_second_msg);
		String msg = getIntent().getStringExtra("MSG");
		edit_second_msg.setText(msg);
	}

	/*
	 *  处理组件响应的两种方法:
	 *  1. 在activity_second.xml为组件添加id,使用findViewById(R.id.组件id); 再为其setOnClickListener()
	 *  2. 没有定义id,而是添加了onclick属性,添加public void name属性(View v){}的方法; 直接在方法里处理响应
	 */
	public void back1(View v){
		// 结束当前Activity
		finish();
	}
	
	public void back2(View v){
		Intent intent = new Intent();
		String data = edit_second_msg.getText().toString();
		intent.putExtra("RESULT", data);
		// 设置回调参数: setResult(int resultCode, Intent data)
		setResult(3, intent);
		// 结束当前Activity
		finish();
	}
}
