package com.zichao.l09_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * 静态注册的Receiver: 在manifest中注册
 * 注册的是MyReceiver1这个类,系统需要先创建对象,因此会调用构造器
 * @author Zach
 *
 */
public class MyReceiver1 extends BroadcastReceiver {

	public MyReceiver1() {
		Log.e("TAG", "MyReceiver1's constructor");
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String stringExtra = intent.getStringExtra("action");
		Log.e("TAG", "MyReceiver1's onReceive() Extra: " + stringExtra);
		
	}

}
