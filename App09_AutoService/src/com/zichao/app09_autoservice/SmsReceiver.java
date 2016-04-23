package com.zichao.app09_autoservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * 功能2: 实现黑名单短信的拦截
 * 接收得到短信广播消息的Receiver(权限为最高): 若为110则abortBroadcast,使系统无法收到短信
 * @author Zach
 *
 */
public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 1. 得到Intent中的短信数据, 并封装为短信对象SmsMessage
		// 1) 得到intent中的所有数据
		Bundle extras = intent.getExtras();
		// 2) 得到所有的短信信息
		Object[] pdus = (Object[]) extras.get("pdus");
		// 3) 取出其中一条的信息, 封装为对象对象
		SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
		// 2. 取出号码
		String fromNumber = smsMessage.getOriginatingAddress();
		String msgBody = smsMessage.getMessageBody();
		Log.e("TAG", fromNumber + ": " + msgBody);
		// 3. 判断是否为黑名单号
		if("110".equals(fromNumber)){
			// 3.1 若是黑名单号码: 拦截短信
			abortBroadcast();
			Log.e("TAG", "拦截到一条短信");
		}
	}

}
