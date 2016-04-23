package com.zichao.app06_motionevent;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout parentView;
	private ImageView iv_main;
	private int lastX;
	private int lastY;
	private int parentRight;
	private int parentBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		iv_main = (ImageView) findViewById(R.id.iv_main);
		parentView = (RelativeLayout) iv_main.getParent();
		
		// 设置Touch监听
		iv_main.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int eventX = (int) event.getRawX();
				int eventY = (int) event.getRawY();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 若在onCreate()中取值为0: 由于视图对象刚创建,还没有到达 测量 -> 布局 -> 绘制 等阶段
					if (parentRight == 0) {
						parentRight = parentView.getRight();
						parentBottom = parentView.getBottom();
					}
					// 第一次记录lastX和lastY
					lastX = eventX;
					lastY = eventY;
					break;
				case MotionEvent.ACTION_MOVE:
					// 计算事件的偏移
					int dx = eventX - lastX;
					int dy = eventY - lastY;
					// 移动图片:根据偏移量设置新位置的坐标
					int left = iv_main.getLeft() + dx;
					int top = iv_main.getTop() + dy;
					int right = iv_main.getRight() + dx;
					int bottom = iv_main.getBottom() + dy;
					// 限制left
					if (left < 0) {
						right += -left;
						left = 0;
					}
					// 限制top
					if (top < 0) {
						bottom += -top;
						top = 0;
					}
					// 限制right
					if (right > parentRight){
						left -= right - parentRight;
						right = parentRight;
					}
					// 限制bottom
					if (bottom > parentBottom){
						top -= bottom - parentBottom;
						bottom = parentBottom;
					}
					iv_main.layout(left, top, right, bottom);
					// 再次记录lastX和lastY, 准备下次移动
					lastX = eventX;
					lastY = eventY;
					break;

				default:
					break;
				}
				return true; // 所有的motionEvent都交给ImageView处理
			}
		});
	}
}
