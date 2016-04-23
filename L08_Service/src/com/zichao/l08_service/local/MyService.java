package com.zichao.l08_service.local;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 测试Local Service和Service的生命周期
 * 
 * 1. 注意需要在manifest中注册此Service
 * <service android:name="com.zichao.l08_service.local.MyService"/>  (不要穿插加注释)
 * 2. 生命周期
 * 1) startService(intent)
 * 第一次调用: -->构造方法()-->onCreate()-->onStartCommand()
 * 再次调用 :                          -->onStartCommand()
 * 2) stopService()
 * -->onDestory()
 * 3) bindService(intent, serviceConnection)
 * -->构造方法()-->onCreate()-->onBind()-->onServiceConnected()
 * 4) unbindService():若只有一个当前的Activity与Service连接才会销毁
 * -->onUnbind()-->onDestroy()
 * @author Zach
 */
public class MyService extends Service {

	public MyService(){
		Log.e("TAG", "My Service()'s Constructor");
	}

	@Override
	public void onCreate() {
		// Service在主线程执行;若执行长时间的任务需要启动分线程
//		SystemClock.sleep(5000);
		
		Log.e("TAG", "My Service()'s onCreate()");
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		Log.e("TAG", "My Service()'s onDestroy()");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("TAG", "My Service()'s onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		Log.e("TAG", "My Service()'s bindService()");
		return super.bindService(service, conn, flags);
	}
	
	@Override
	public void unbindService(ServiceConnection conn) {
		Log.e("TAG", "My Service()'s unbindService()");
		super.unbindService(conn);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("TAG", "My Service()'s onBind()");
		return new Binder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("TAG", "My Service()'s onUnbind()");
		return super.onUnbind(intent);
	}
	
}
