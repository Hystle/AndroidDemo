package com.atguigu.l05_handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class AsyncTaskTestActivity extends Activity {

	private File file = null;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async);
	}

	public void downloadApk(View v) {
		/*
		 * 0. 创建对象并使用
		 * AsyncTask<Params, Progress, Result>{
		 * 		//重写多个方法
		 * }.execute()
		 * 执行多线程的异步任务
		 */
		new AsyncTask<Void, Integer, Void>() {
			/*
			 * 1. onPreExecute()
			 * 在分线程的任务开始前的操作,在主线程执行: 一般为显示提示视图
			 */
			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(AsyncTaskTestActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.show();
				// 准备用与保存APK文件的File对象:由于需要安装,不能放在私有的data中,只能放在SD卡中
				file = new File(getExternalFilesDir(null), "download.apk");
			}

			/*
			 * 2. doInBackground()
			 * 在分线程执行的具体任务主体: 一般需要较长时间
			 */
			@Override
			protected Void doInBackground(Void... params) {
				try {
					// 1) 得到连接对象
					String path = "http://192.168.2.12:8080/L04_WebServer/L04_DataStorage.apk";
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 2) 设置属性:默认为GET
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					// 3) 连接, 得到响应码
					conn.connect();
					if (conn.getResponseCode() == 200) {
						// 设置dialog的最大进度
						dialog.setMax(conn.getContentLength());
						// 4) 得到包含APK文件数据的InputStream
						InputStream is = conn.getInputStream();
						// 5) 创建指向file的FileOutputStream
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = is.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
							/*
							 * 3. publishProgress()
							 * 用于发布分线程执行的任务进度:参数类型需要填充为AsyncTask的Progress的类型
							 */
							publishProgress(len);
							
							// 模拟网络情况
							SystemClock.sleep(50);
						}
						fos.close();
						is.close();
					}
					// 6) 下载完成:关闭, 进入UI线程
					conn.disconnect();
				} catch (Exception e) {}
				return null;
			}

			/*
			 * 4. onPostExecute(Void result)
			 * 在分线程任务完成后,在主线程执行的操作:一般为更新UI界面
			 */
			protected void onPostExecute(Void result) {
				dialog.dismiss();
				installAPK();
			};
			
			/*
			 * 5. onProgressUpdate(Integer[] values)
			 * 用于在publishProgress(len)执行之后,在UI线程显示更新进度
			 */
			protected void onProgressUpdate(Integer[] values) {
				dialog.incrementProgressBy(values[0]);
			};
		}
		/*
		 *  6. execute(Params ... params)
		 *  开始异步任务
		 */
		.execute();
	}

	// 工具方法
	private void installAPK() {
		Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}
}
