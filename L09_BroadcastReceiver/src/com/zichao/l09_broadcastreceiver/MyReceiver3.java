package com.zichao.l09_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * 测试接收有序广播1: 使用静态注册的Receiver 
 * 在manifest中,可以 1.指定优先级; 2.可拦截
 * @author Zach
 *
 */
public class MyReceiver3 extends BroadcastReceiver {

	public MyReceiver3() {
		Log.e("TAG", "MyReceiver3's constructor");
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String stringExtra = intent.getStringExtra("action");
		Log.e("TAG", "MyReceiver3's onReceive() Extra: " + stringExtra);
		
	}

}
