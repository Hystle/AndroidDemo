package com.zichao.app09_autoservice;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 监听来电状态,若来电为110则自动挂断
 * @author Zach
 *
 */
public class ListenCallService extends Service{

	private TelephonyManager tm;
	private PhoneStateListener listener = new PhoneStateListener(){
		
		// 当通话状态改变时调用
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:	// 挂断之前/未来电之前
				Log.e("TAG", "IDLE");
				break;
			case TelephonyManager.CALL_STATE_RINGING:	// 响铃
				Log.e("TAG", "RINGING");
				if("110".equals(incomingNumber)){
					try {
						endCall();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:	// 接通
				Log.e("TAG", "OFFHOOK");
				break;
			default:
				break;
			}
			
		}		
	};
	
	private void endCall() throws Exception {
		// 得到类的对象
		Class clazz = Class.forName("android.os.ServiceManager");
		// 得到getService方法, 参数为String类型
		Method method = clazz.getMethod("getService", String.class);
		// arg1 调用者: 此方法为static, 因此为null; arg2 参数: 电话服务的系统常数
		IBinder binder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
		// 得到接口对象
		ITelephony tele = ITelephony.Stub.asInterface(binder);
		// 挂断电话
		tele.endCall();
	}
	
	@Override
	public void onCreate() {
		Log.e("TAG", "Service's onCreate()");
		super.onCreate();
		// 得到TelephonyManager
		tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		// 监听电话的状态
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public void onDestroy() {
		Log.e("TAG", "Service's onDestroy()");
		super.onDestroy();
		// 停止电话监听
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
