package com.zichao.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 测试使用Log类
		Log.i("TAG_i", "使用i()方法...");
		Log.e("TAG_e", "使用e()方法...");
	}
}
