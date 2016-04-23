package com.zichao.l09_broadcasttransmitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// 发送一般广播
	public void sendNormalBC(View v){
		Intent intent = new Intent("com.zichao.l09_broadcast.normalBC.action");
		intent.putExtra("action", "Extra Info for NormalBC");
		sendBroadcast(intent);
		Toast.makeText(this, "发送一般广播", Toast.LENGTH_SHORT).show();
	}
	
	// 发送有序广播
	public void sendOrderBC(View v){
		Intent intent = new Intent("com.zichao.l09_broadcast.orderBC.action");
		intent.putExtra("action", "Extra Info for OrderBC");
		sendOrderedBroadcast(intent, null);
		Toast.makeText(this, "发送有序广播", Toast.LENGTH_SHORT);
	}
}
