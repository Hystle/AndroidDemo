package com.zichao.app11_scan;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ImageView iv_main_scan;
	private TextView tv_main_info;
	private ProgressBar pb_main_progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv_main_scan = (ImageView) findViewById(R.id.iv_main_scan);
		tv_main_info = (TextView) findViewById(R.id.tv_main_info);
		pb_main_progress = (ProgressBar) findViewById(R.id.pb_main_progress);
		
		// 1. 显示扫描动画
		showScanAnimation();
		// 2. 显示扫描进度条
		scanProgress();
	}

	private void scanProgress() {
		// 启动异步任务
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				// 主线程显示提示视图
				tv_main_info.setText("扫描引擎正在启动中...");
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				// 2. 分线程: 扫描
				int appCount = 60;
				pb_main_progress.setMax(appCount);
				for(int i = 0; i < appCount; i++){
					SystemClock.sleep(40);
					publishProgress();
				}
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Void... values) {
				// 主线程更新进度
				pb_main_progress.incrementProgressBy(1);
				// 即pb_main_progress.setProgress(pb_main_progress.getProgress() + 1);
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// 主线程更新界面
				pb_main_progress.setVisibility(View.GONE);
				// 更新文本
				tv_main_info.setText("启动完成");
				// 停止扫描动画
				iv_main_scan.clearAnimation();
			}
		}
		// 必须执行才会生效
		.execute();
		
	}

	private void showScanAnimation() {
		RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setInterpolator(new LinearInterpolator());
		iv_main_scan.startAnimation(animation);
	}
}
