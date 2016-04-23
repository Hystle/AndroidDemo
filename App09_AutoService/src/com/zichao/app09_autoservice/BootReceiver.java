package com.zichao.app09_autoservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 功能1: 实现电话监听服务的自启动
 * 接受开机完成的Receiver: 借用之前挂断电话Service功能的代码
 * @author Zach
 *
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 启动电话监听的Service
		context.startService(new Intent(context, ListenCallService.class));
	}

}
