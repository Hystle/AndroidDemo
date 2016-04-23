package com.zichao.l03_component;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ProgressBarActivity extends Activity {
	private LinearLayout ll_progressBar_loading;
	private ProgressBar pb_progressBar_trueLoading;
	private SeekBar sb_progressBar_setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_bar);
		
		ll_progressBar_loading = (LinearLayout) findViewById(R.id.ll_progressBar_loading);
		pb_progressBar_trueLoading = (ProgressBar) findViewById(R.id.pb_progressBar_trueLoading);
		sb_progressBar_setting = (SeekBar) findViewById(R.id.sb_progressBar_setting);
		
		sb_progressBar_setting.setOnSeekBarChangeListener(onSeekBarChangeListener );
	}

	private OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) { // 离开滑杆时调用
			Log.e("Tag", "离开滑杆");
			// 功能1: 使两个进度条的进度一致
			int progress = sb_progressBar_setting.getProgress();
			pb_progressBar_trueLoading.setProgress(progress);
			// 功能2: 滑动到最大值时,隐藏ll_progressBar_loading; 若没有则为可见
			if(progress == sb_progressBar_setting.getMax()){
				// {@link #VISIBLE}, {@link #INVISIBLE}, or {@link #GONE}
				ll_progressBar_loading.setVisibility(View.GONE);
			}
			else{
				ll_progressBar_loading.setVisibility(View.VISIBLE);
			}
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {	// 按下滑杆时调用
			Log.e("Tag", "按下滑杆");
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { // 滑杆移动时调用
			Log.e("Tag", "移动滑杆");
		}
	};

}
