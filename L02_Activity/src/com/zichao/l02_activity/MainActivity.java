package com.zichao.l02_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	private EditText et_main_msg;
	private Button btn_main_start1;
	private Button btn_main_start2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 1. 指定layout
		setContentView(R.layout.activity_main);
		// 2. 初始化视图对象
		et_main_msg = (EditText) findViewById(R.id.et_main_msg);
		btn_main_start1 = (Button) findViewById(R.id.btn_main_start1);
		btn_main_start2 = (Button) findViewById(R.id.btn_main_start2);
		// 3. 为多个View添加监听(方法3)
		btn_main_start1.setOnClickListener(this);
		btn_main_start2.setOnClickListener(this);
	}

	// 根据调用这个函数的视图设置响应
	@Override
	public void onClick(View v) {
		if(v==btn_main_start1){
			// 得到EditText的內容. 注意:需要toString()
			String text = et_main_msg.getText().toString();
			// 显式explicit intent: Intent(Context packageContext, Class<?> cls)
			Intent intent = new Intent(this, SecondActivity.class);
			// 加数据: HashMap
			intent.putExtra("MSG", text);
			// 不带回调的启动
			startActivity(intent);
			
		}else if(v==btn_main_start2){
			String text = et_main_msg.getText().toString();
			Intent intent = new Intent(this, SecondActivity.class);
			intent.putExtra("MSG", text);
			// 带回调的启动: startActivityForResult(Intent intent, int requestCode)
			startActivityForResult(intent, 2);
		}
	}
	
	/*
	 * 对于回调在原Activity的响应
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 根据requestCode + resultCode来区分不同的回调响应
		if(requestCode == 2 && resultCode == 3){
			// 得到intent中的内容: getXxxExtra() <--> putExtra(String name, Xxx text)
			String stringExtra = data.getStringExtra("RESULT");
			et_main_msg.setText(stringExtra);
		}
	
	}
}
