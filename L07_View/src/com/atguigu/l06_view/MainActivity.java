package com.atguigu.l06_view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MyTextView myTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * 测试自定义View的构造方法1:创建对象的方式
		 */
		//	MyTextView textView = new MyTextView(this);
		//	textView.setText("www.atguigu.com");
		//	setContentView(textView);


		/*
		 * 测试自定义View的构造方法2:导入布局的方式
		 */
		// 1) 
		// setContentView(R.layout.activity_main);

		// 2) 
		LinearLayout layout = (LinearLayout) View.inflate(this, R.layout.activity_main, null);

		// 根据下标得到子View
		myTextView = (MyTextView) layout.getChildAt(0);

		setContentView(layout);
	}

	// Activity的生命周期方法: 经过此方法后,才会进入 测量 -> 布局 -> 绘制 等流程
	@Override
	protected void onResume() {
		Log.i("TAG", "onResume()");
		super.onResume();
	}

	// 强制重新布局
	public void forceLayout(View v) {
		myTextView.requestLayout();
		Toast.makeText(this, "强制重新布局", 0).show();
	}

	// 强制重绘视图: invalidate() & postInvalidate() 
	public void forceDraw(View v) {
		// 主线程都可以使用
		// myTextView.invalidate();
		// myTextView.postInvalidate();
		new Thread(){
			public void run() {
				//myTextView.invalidate();// 不可以, 只能在主线程执行
				myTextView.postInvalidate(); // 可以在主线程和分线程执行
			}
		}.start();
		Toast.makeText(this, "强制重新绘", 0).show();
	}

	/*
	 * 测试视图的死亡
	 */
	public void removeView(View v) {
		ViewGroup viewGroup = (ViewGroup) myTextView.getParent();
		viewGroup.removeView(myTextView);
	}
}
