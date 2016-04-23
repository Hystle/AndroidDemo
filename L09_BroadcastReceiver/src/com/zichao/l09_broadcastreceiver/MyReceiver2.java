package com.zichao.l09_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * 动态注册的Receiver
 * 1. 注册的是对象,不需要创建MyReceiver2对象,因此没有调用构造器
 * 2. MyReceiver2对象在Activity死亡之前必须解除注册,否则绑定了无效Activity目标会报错; 静态注册则不需要
 * @author Zach
 *
 */
public class MyReceiver2 extends BroadcastReceiver {

	public MyReceiver2() {
		Log.e("TAG", "MyReceiver2's constructor");
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String stringExtra = intent.getStringExtra("action");
		Log.e("TAG", "MyReceiver2's onReceive() Extra: " + stringExtra);
		
	}

}
