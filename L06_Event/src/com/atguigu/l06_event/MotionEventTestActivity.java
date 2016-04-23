package com.atguigu.l06_event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MotionEventTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		
		/*
		 * 操作使用自定义的ImageView来测试MotionEvent的分发和处理流程
		 */
		ImageView iv_touch_test = (ImageView) findViewById(R.id.iv_touch_test);
		
		iv_touch_test.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e("TAG","MyImageView's onTouchListener() -> " + "Motion Event: " + event.getAction());
				return false;
			}
		});
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("TAG","MotionEventTestActivity's dispatchKeyEvent() -> " + "Motion Event: " + ev.getAction());
		return super.dispatchTouchEvent(ev);
		
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("TAG","MotionEventTestActivity's onTouchEvent() -> " + "Motion Event: " + event.getAction());
		return super.onTouchEvent(event);
	}
}
