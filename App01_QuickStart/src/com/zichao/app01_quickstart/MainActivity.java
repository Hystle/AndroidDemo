package com.zichao.app01_quickstart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 定义成员变量
	private Button btn_main_download;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 1. 加载布局,并生成对应的视图对象
		setContentView(R.layout.activity_main);
		// 2. 从视图对象中加载Button对象:findViewById返回类型是View,而Button,text等都是View的子类
		btn_main_download = (Button) findViewById(R.id.btn_main_download);
		// 3. 对Button设置点击监听 ->内部接口
		btn_main_download.setOnClickListener(new View.OnClickListener() {
			
			// 在内部接口中设置回调方法
			@Override
			public void onClick(View v) {
				// 1) 显示提示下载的Toast文本
				// makeText()需要第一个参数为Context类型,Activity是Context这个抽象类的子类
				// 因此用MainActivity.this传入MainActivity这个外部类的对象
				Toast.makeText(MainActivity.this, "Download Starts", Toast.LENGTH_SHORT).show();
				// 2) 更改Button的文本
				btn_main_download.setText("Downloading...");
			}
		});
		
		
	}
}
