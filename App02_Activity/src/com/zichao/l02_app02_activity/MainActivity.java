package com.zichao.l02_app02_activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

/*
 * 为View添加事件的三种方法:
 * 1. 直接将内部接口的实现作为参数
 * 2. 新建OnClickListener对象,实现内部接口的方法
 * 3. Activity实现OnClickListener,并实现onClick()方法
 */
public class MainActivity extends Activity {
	
	private EditText et_main_number;
	private EditText et_main_sms;
	private Button btn_main_call;
	private Button btn_main_sms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		et_main_number = (EditText) findViewById(R.id.et_main_number);
		et_main_sms = (EditText) findViewById(R.id.et_main_sms);
		btn_main_call = (Button) findViewById(R.id.btn_main_call);
		btn_main_sms = (Button) findViewById(R.id.btn_main_sms);
		
		btn_main_call.setOnClickListener(onClickListener);
		btn_main_sms.setOnClickListener(onClickListener);
		btn_main_call.setOnLongClickListener(onLongClickListener);
		btn_main_sms.setOnLongClickListener(onLongClickListener);
	}
	
	/*
	 * 点击事件: 将目标号码和内容显示在电话/短信页面, 等待用户执行
	 */
	OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v == btn_main_call){
// 02-14 22:05:29.000: I/ActivityManager(1223): Displayed com.android.dialer/.DialtactsActivity: +521ms
//				String action = "android.intent.action.DIAL";	// 从源码中按照LogCat中的记录DialtactsActivity查找
				
				String action = Intent.ACTION_DIAL;		// 常用的已经有定义: 与上面相同
				Intent intent = new Intent(action);		// 隐式intent:需要传入action的字符串
				
				String number = et_main_number.getText().toString();
				
				// 设置Uri: 参数根据源码AndroidManifest.xml中的规定,以tel开头: <data android:scheme="tel" /> 
				intent.setData(Uri.parse("tel:"+ number));		// 注意:用冒号分隔
				// 启动Activity(无回调)
				startActivity(intent);
			}
			
			else if(v == btn_main_sms){
				String number = et_main_number.getText().toString();
				String sms = et_main_sms.getText().toString();
				// 显示到短信Activity的action名
				String action = Intent.ACTION_SENDTO;
				Intent intent = new Intent(action);
				// smsto: 显示在短信界面中,但需点击发送
				intent.setData(Uri.parse("smsto:" + number));
				// 设置短信内容:不能再使用setData()否则会覆盖上一句
				intent.putExtra("sms_body", sms);
				startActivity(intent);
			}
		}
	};
	/*
	 * 长按事件: 直接打电话/发短信(需要配置权限CALL_PHONE)
	 */
	OnLongClickListener onLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if(v == btn_main_call){
// 02-14 22:33:52.756: I/ActivityManager(1223): Displayed com.android.phone/.PrivilegedOutgoingCallBroadcaster: +400ms
//				String action = "android.intent.action.CALL";
				
				String action = Intent.ACTION_CALL;		
				Intent intent = new Intent(action);
				
				String number = et_main_number.getText().toString();
				intent.setData(Uri.parse("tel:" + number));
				// 注意:需要在AndroidManifest.xml中添加CALL_PHONE权限; 否则会报错
				startActivity(intent);
			}
			
			else if(v == btn_main_sms){
				String number = et_main_number.getText().toString();
				String text = et_main_sms.getText().toString();
				/*
				 * 直接发送短信: SmsManager(需要配置权限SEND_SMS)
				 * sendTextMessage(String destinationAddress, String scAddress, String text,
            						PendingIntent sentIntent, PendingIntent deliveryIntent)
				 * @param destinationAddress: the address to send the message to
				 * @param scAddress: the service center address or null to use
				 * @param sentIntent, deliveryIntent: 短信回执相关,若不需要可以设置为null 
				 */
				SmsManager smsManager = SmsManager.getDefault();	// 创建SmsManager对象
				smsManager.sendTextMessage(number, null, text, null, null);	// 发送消息
			}
			// 注意: 设置为true表示此事件已被消费了,不会重复触发点击事件
			return true;
		}
	};

}
