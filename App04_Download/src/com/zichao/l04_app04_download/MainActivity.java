package com.zichao.l04_app04_download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends Activity {

	private File file = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void downloadAPK(View v){
		// 1. 主线程显示视图
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.show();
		// 2. 准备保存APK文件的File对象:由于需要安装,不能放在私有的data中,只能放在SD卡中
		file = new File(getExternalFilesDir(null), "download.apk");
		//3. 启动分线程,请求下载APK文件,下载过程中显示下载进度
		new Thread(new Runnable() {
			@Override
			public void run() {
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
					if(conn.getResponseCode() == 200){
						// 6.1) 设置dialog的最大进度
						dialog.setMax(conn.getContentLength());
						// 4) 得到包含APK文件数据的InputStream
						InputStream is = conn.getInputStream();
						// 5) 创建指向file的FileOutputStream
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int len = -1;
						while((len = is.read(buffer))!=-1){
							fos.write(buffer, 0 ,len);
							// 6.2) 显示下载进度: 内部使用handler处理,因此可以在分线程更新UI线程的界面
							dialog.incrementProgressBy(len);
							// 模拟网络情况
							SystemClock.sleep(50);
						}
						fos.close();
						is.close();
					}
					// 7) 下载完成:关闭, 进入UI线程
					conn.disconnect();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							// 8) 启动安装
							installAPK();
						}

					});
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}
	
	/**
	 * 下载完成启动安装APK
	 */
	private void installAPK() {
		Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}
}
