package com.atguigu.l05_handler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class HandlerTestActivity extends Activity {

	private ProgressBar pb_handler1_loading;
	private EditText et_handler1_result;
	// 0. 创建Handler成员变量
	private Handler handler = new Handler() {
		// 3. 重写其HandlerMessage()方法: 在主线程接收从分线程中handler发出的消息,并处理
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				String result = msg.obj.toString();
				et_handler1_result.setText(result);
				pb_handler1_loading.setVisibility(View.INVISIBLE);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_test);

		pb_handler1_loading = (ProgressBar) findViewById(R.id.pb_handler1_loading);
		et_handler1_result = (EditText) findViewById(R.id.et_handler1_result);
	}

	/**
	 * 使用老方法完成网络请求
	 * @param v
	 */
	public void getSubmit1(View v) {
		// 1. 主线程: 显示提示视图ProgressBar
		pb_handler1_loading.setVisibility(View.VISIBLE);
		// 2. 分线程: 联网请求,并得到响应数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				String path = "http://192.168.2.12:8080/L04_WebServer/index.jsp?name=tom&age=12";
				try {
					final String result = requestToString(path);
					// 3. 主线程显示
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							et_handler1_result.setText(result);
							pb_handler1_loading.setVisibility(View.INVISIBLE);
						}
					});
				} catch (Exception e) {
				}
			}
		}).start();
	}

	/**
	 * 使用Handler完成网络请求
	 * @param v
	 */
	public void getSubmit2(View v) {
		pb_handler1_loading.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String path = "http://192.168.2.12:8080/L04_WebServer/index.jsp?name=tom&age=12";
				try {
					final String result = requestToString(path);
					// 1. 创建Message对象(主/分线程)
					Message msg = Message.obtain();
					msg.what = 1;	// 辨识值(id)
					msg.obj = result;	// 结果
					// 2. 使用handler发送消息
					handler.sendMessage(msg);
				} catch (Exception e) {
				}
			}
		}).start();
	}

	/**
	 * 工具方法: 请求服务器端, 得到返回的结果字符串
	 * @param path: http://192.168.30.165:8080/Web_server/index.jsp?name=tom&age=12
	 * @return 服务器返回的字符串
	 * @throws Exception
	 */
	public String requestToString(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.connect();
		InputStream is = connection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		baos.close();
		is.close();
		String result = baos.toString();
		connection.disconnect();

		return result;
	}
}
