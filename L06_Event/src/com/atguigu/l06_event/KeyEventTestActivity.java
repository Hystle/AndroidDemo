package com.atguigu.l06_event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
/**
 * 测试按键监听
 * @author Zach
 *
 */
public class KeyEventTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key);
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.e("TAG", "dispatchKeyEvent() | KeyCode: " + event.getKeyCode() + " | Action: " + event.getAction());
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("TAG", "onKeyDown() | KeyCode: " + event.getKeyCode() + " | Action: " + event.getAction());
		
		// startTracking(): 追踪事件,用于长按监听; return需为true
		event.startTracking();
		return true;
//		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.e("TAG", "onKeyUp() | KeyCode: " + event.getKeyCode() + " | Action: " + event.getAction());
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		Log.i("TAG", "onKeyLongPress() | KeyCode: " + event.getKeyCode() + " | Action: " + event.getAction());
		return super.onKeyLongPress(keyCode, event);
	}
}
