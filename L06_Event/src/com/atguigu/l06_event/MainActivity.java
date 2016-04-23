package com.atguigu.l06_event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 点击监听
		findViewById(R.id.btn_main_test1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, MotionEventTestActivity.class));
			}
		} );

		// 长按监听
		findViewById(R.id.btn_main_test2).setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(MainActivity.this, KeyEventTestActivity.class));
				return true;
			}
		});

	}

	/**
	 * 监听back按键, 改变其默认行为
	 * 功能1. 点击back键时弹出对话框
	 */
	//		@Override
	//		public boolean onKeyUp(int keyCode, KeyEvent event) {
	//			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
	//				new AlertDialog.Builder(this)
	//				.setMessage("确认退出码?")
	//				.setPositiveButton("退出", new OnClickListener() {
	//					@Override
	//					public void onClick(DialogInterface dialog, int which) {
	//						finish();
	//					}
	//				})
	//				.setNegativeButton("取消", null)
	//				.show();
	//			}
	//			return true;	// return true:使其消费这次事件, 即直接不会退出了
	//		}

	/**
	 * 监听back按键, 改变其默认行为
	 * 功能2. 两秒内点击两下back键才退出
	 */
	private boolean exit = false;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				exit = false;
			}
		};
	};

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(!exit){
				exit = true;
				Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
				// 控制两秒后取消退出操作: 发送延迟消息
				handler.sendEmptyMessageDelayed(1, 2000);
				return true;	// 不退出
			}
		}
		return super.onKeyUp(keyCode, event);
	}

}
