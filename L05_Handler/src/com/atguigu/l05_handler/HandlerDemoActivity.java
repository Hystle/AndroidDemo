package com.atguigu.l05_handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 使用Handler的小DEMO
 * 1. 手动增加/减少
 * 2. 自动增加/减少
 * 3. 限制数字的最大和最小值 [1,20]
 * 4. 限制Button可操作性
 */
public class HandlerDemoActivity extends Activity implements OnClickListener {

	private static final int WHAT_INCREASE = 1;
	private static final int WHAT_DECREASE = 2;

	private TextView tv_demo_number;
	private Button btn_demo_increase;
	private Button btn_demo_decrease;
	private Button btn_demo_pause;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			// 得到当前的数值
			int num = Integer.parseInt(tv_demo_number.getText().toString());
			switch (msg.what) {
			case WHAT_INCREASE:
				// 限制最大值
				if(num == 20){
					// 设置暂停键不能操作
					btn_demo_pause.setEnabled(false);
					Toast.makeText(HandlerDemoActivity.this, "达到MAX", Toast.LENGTH_SHORT).show();
					return;
				}
				num++;
				tv_demo_number.setText(num+"");
				// 发送增加的延迟消息:添加到消息队列,不能使用即时消息,否则会急速增加
				handler.sendEmptyMessageDelayed(WHAT_INCREASE, 1000);
				break;
			case WHAT_DECREASE:
				// 限制最小值
				if(num == 0){
					// 设置暂停键不能操作
					btn_demo_pause.setEnabled(false);
					Toast.makeText(HandlerDemoActivity.this, "达到MIN", Toast.LENGTH_SHORT).show();
					return;
				}
				num--;
				tv_demo_number.setText(num+"");
				// 发送减少的延迟消息
				handler.sendEmptyMessageDelayed(WHAT_DECREASE, 1000);
				break;
			default:
				break;
			}
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_demo);
		init();
	}

	private void init() {
		tv_demo_number = (TextView) findViewById(R.id.tv_demo_number);
		btn_demo_increase = (Button) findViewById(R.id.btn_demo_increase);
		btn_demo_decrease = (Button) findViewById(R.id.btn_demo_decrease);
		btn_demo_pause = (Button) findViewById(R.id.btn_demo_pause);
		
		btn_demo_increase.setOnClickListener(this);
		btn_demo_decrease.setOnClickListener(this);
		btn_demo_pause.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==btn_demo_increase) {//自动增加  what =1, 一般定义为常量
			// 移除未处理的减少的消息
			handler.removeMessages(WHAT_DECREASE);
			// 发送Handler
			handler.sendEmptyMessage(WHAT_INCREASE);
			// 限制的button的可操作性
			btn_demo_increase.setEnabled(false);
			btn_demo_decrease.setEnabled(true);
			btn_demo_pause.setEnabled(true);
		} else if(v==btn_demo_decrease) {//自动减少 what=2
			handler.removeMessages(WHAT_INCREASE);
			handler.sendEmptyMessage(WHAT_DECREASE);
			// 限制的button的可操作性
			btn_demo_increase.setEnabled(true);
			btn_demo_decrease.setEnabled(false);
			btn_demo_pause.setEnabled(true);
		} else if(v==btn_demo_pause) {//暂停
			// 停止增加或减少
			handler.removeMessages(WHAT_INCREASE);
			handler.removeMessages(WHAT_DECREASE);
			// 限制的button的可操作性
			btn_demo_increase.setEnabled(true);
			btn_demo_decrease.setEnabled(true);
			btn_demo_pause.setEnabled(false);
		}
	}
}
