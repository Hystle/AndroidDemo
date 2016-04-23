package com.zichao.app08_musicplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 在Activity中播放音乐可行但是有bug
 * 若点击后退键,音乐会在后台播放,但Activity失去了对音乐的控制
 * @author Zach
 *
 */
public class MainActivity2 extends Activity implements OnClickListener {

	private Button btn_main_play;
	private Button btn_main_stop;
	private Button btn_main_pause;
	private Button btn_main_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_main_play = (Button) findViewById(R.id.btn_main_play);
		btn_main_stop = (Button) findViewById(R.id.btn_main_stop);
		btn_main_pause = (Button) findViewById(R.id.btn_main_pause);
		btn_main_exit = (Button) findViewById(R.id.btn_main_exit);

		btn_main_play.setOnClickListener(this);
		btn_main_stop.setOnClickListener(this);
		btn_main_pause.setOnClickListener(this);
		btn_main_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (btn_main_play == v) {
			playMusic();
		} else if (btn_main_stop == v) {
			stopMusic();
		} else if (btn_main_pause == v) {
			pauseMusic();
		} else if (btn_main_exit == v) {
			exitMusic();
		}
	}

	private MediaPlayer player;


	private void playMusic() {
		if(player == null){
			player = MediaPlayer.create(this, R.raw.kimberley);
		}
		player.start();
	}

	private void pauseMusic() {
		if(player != null && player.isPlaying()){
			player.pause();
		}
	}

	private void stopMusic() {
		if(player != null){
			player.stop();	
			player.reset();	  // 重置MediaPlayer
			player.release(); // 释放加载的音乐资源
			player = null;	// 保证exitMusic()调用stopMusic时能正常通过判断
		}
	}

	private void exitMusic() {
		stopMusic();
		finish();
	}
}
