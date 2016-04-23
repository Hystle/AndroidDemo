package com.zichao.l09_broadcastreceiver;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 动态注册的Receiver
 * @author Zach
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// MyReceiver2对象在Activity死亡之前必须解除注册,否则绑定了无效Activity目标会报错; 静态注册则不需要
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(receiver != null){
			unregisterReceiver(receiver);
			receiver = null;
		}
	}
	
	private MyReceiver2 receiver;
	/*
	 * 注册广播接收器
	 */
	public void registBR(View v){
		if (receiver == null){
			// 1. 创建Receiver对象
			receiver = new MyReceiver2();
			// 2. 创建过滤器对象
			IntentFilter filter = new IntentFilter("com.zichao.l09_broadcast.normalBC.action");
			// 3. 注册Receiver
			registerReceiver(receiver, filter);
			Toast.makeText(this, "注册广播接收器", Toast.LENGTH_SHORT).show();
		}
		else{

			Toast.makeText(this, "已注册广播接收器", Toast.LENGTH_SHORT).show();
		}
	}
	/*
	 * 取消注册Receiver
	 */
	public void unRegistBR(View v){
		if(receiver != null){
			unregisterReceiver(receiver);
			receiver = null;
			Toast.makeText(this, "解除广播接收器", Toast.LENGTH_SHORT).show();
		}
		else{

			Toast.makeText(this, "已解除广播接收器", Toast.LENGTH_SHORT).show();
		}
	}
}
