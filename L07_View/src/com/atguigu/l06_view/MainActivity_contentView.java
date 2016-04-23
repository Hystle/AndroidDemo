package com.atguigu.l06_view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * 测试显示contentView的三种方式
 * 1. setContentView(int layoutId); 
 * 2. setContentView(View view);
 */
public class MainActivity_contentView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 方式1. 
		// 设置id为content的frameLayout的子View
		// setContentView(R.layout.activity_main);
		
		// 方式2.1. 动态加载布局文件得到视图对象
		// View view = View.inflate(this, R.layout.activity_main, null);
		// setContentView(view);
		
		// 方式2.2. 动态创建视图对象(当View对象比较简单时使用)
		TextView view  = new TextView(this);
		view.setBackgroundColor(Color.RED);
		view.setText("atguigu.com");
		setContentView(view);
		
		Window window = getWindow();
		View decorView = window.getDecorView();
		Log.e("TAG", decorView.toString());
		Log.e("TAG", decorView.getClass().getSuperclass().getName());
		
	}
}
