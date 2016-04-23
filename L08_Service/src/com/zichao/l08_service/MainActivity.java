package com.zichao.l08_service;

import com.zichao.l08_service.local.MyService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Service的生命周期方法测试
 * @author Zach
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/*
	 * 在Activity退出前要解绑service否则会报错
	 */
	@Override
	protected void onDestroy() {
		if(conn != null){
			unbindService(conn);
			conn = null;
		}
		super.onDestroy();
	}
	/*
	 * 启动/停止服务
	 */
	public void startMyService(View v){
		Intent intent = new Intent(this, MyService.class);
		startService(intent);
		Toast.makeText(this, "StratService", Toast.LENGTH_SHORT).show();
	}

	public void stopMyService(View v){
		Intent intent = new Intent(this, MyService.class);
		stopService(intent);
		Toast.makeText(this, "StopService", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 启动服务, 并绑定Activity与Service的连接
	 */
	private ServiceConnection conn; 
	public void bindMyService(View v){
		Intent intent = new Intent(this, MyService.class);
		if (conn == null){
			conn = new ServiceConnection() {
				@Override
				public void onServiceDisconnected(ComponentName name) {
					Log.e("TAG", "onServiceDisconnected()");
				}
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					Log.e("TAG", "onServiceConnected()");
				}
			};
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
			Toast.makeText(this, "bindMyService", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "已经bindMyService", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 停止服务, 并解除Activity与Service的连接
	 */
	public void unbindMyService(View v){
		if(conn != null){
			unbindService(conn);
			conn = null;
			Toast.makeText(this, "unbindMyService", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "还未bindMyService", Toast.LENGTH_SHORT).show();
		}
	}
}
