package com.atguigu.l06_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 测试自定义TextView的生命周期
 */
public class MyTextView extends TextView {

	// 构造方法1:手动new创建对象时使用
	public MyTextView(Context context) {
		super(context);
		Log.e("TAG", " MyTextView(Context)");
	}

	// 构造方法2:使用导入布局文件方法创建对象时,由系统调用
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("TAG", " MyTextView(Context, AttributeSet)");
	}
	
	/**
	 * 只有导入布局的方式才会调用此方法
	 * 一般重写此方法用于得到其子View对象
	 */
	@Override
	protected void onFinishInflate() {
		Log.e("TAG", " onFinishInflate()");
		super.onFinishInflate();
	}

	/**
	 * 无论new还是布局都会调用此方法
	 * 一般重写此方法用于得到其子View对象
	 */
	@Override
	protected void onAttachedToWindow() {
		Log.e("TAG", " onAttachedToWindow()");
		super.onAttachedToWindow();
	}
	
	/*
	 * callback回调方法:由系统调用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int measuredHeight = this.getMeasuredHeight();
		int measuredWidth = this.getMeasuredWidth();
		Log.e("TAG", "onMeasure() measuredHeight="+measuredHeight+" measuredWidth="+measuredWidth);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/*
	 * 测试layout()和其回调方法onLayout()
	 */
	@Override
	public void layout(int l, int t, int r, int b) {
		Log.e("TAG","layout()");
		super.layout(l, t, r, b);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.e("TAG","onLayout() changed="+changed);
		super.onLayout(changed, left, top, right, bottom);
	}
	
	/*
	 * 测试draw()和onDraw()
	 */
	@Override
	public void draw(Canvas canvas) {
		Log.e("TAG","draw() ");
		super.draw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("TAG","onDraw() ");
		super.onDraw(canvas);
	}
	
	/*
	 * 测试死亡的流程方法
	 */
	@Override
	protected void onDetachedFromWindow() {
		Log.e("TAG","onDetachedFromWindow() ");
		super.onDetachedFromWindow();
	}
}
