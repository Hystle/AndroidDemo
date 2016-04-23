package com.zichao.app08_endincomingcall;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
/**
 * 点击Button挂断电话
 * @author Zach
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void endCall(View v) throws Exception{
		// 需要使用系统的ServiceManager类,但其被隐藏了: 使用反射
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
}
