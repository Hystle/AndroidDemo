package com.atguigu.l06_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 自定义的ImageView: 用于重写ImageView组件的方法, 测试MotionEvent
 * ImageView由系统创建对象,为了重写其方法,自定义了一个类继承ImageView
 * 在layout文件中,直接使用这个类的全类名替代原来的ImageView即可
 * @author Zach
 *
 */
public class MyImageView extends ImageView {

	/*
	 * ImageView没有不含参的构造器,因此必须重写构造器
	 * 而且由于layout文件中定义了一些属性,必须使用含AttributeSet这个参数的构造方法
	 */
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("TAG","MyImageView's Constructor");
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.e("TAG","MyImageView's dispatchTouchEvent() -> " + "Motion Event: " + event.getAction());
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("TAG","MyImageView's onTouchEvent() -> " + "Motion Event: " + event.getAction());
//		return super.onTouchEvent(event);
		return true;
	}
}
