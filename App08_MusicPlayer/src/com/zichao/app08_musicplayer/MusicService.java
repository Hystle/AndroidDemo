package com.zichao.app08_musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * 使用Service播放音乐
 * @author Zach
 *
 */
public class MusicService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getStringExtra("action");
		if ("play".equals(action)) {
			playMusic();
		} else if ("pause".equals(action)) {
			pauseMusic();
		} else if ("stop".equals(action)) {
			stopMusic();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		stopMusic();
		super.onDestroy();
	}

	/*
	 *  used to control playback of audio/video files and streams
	 */
	private MediaPlayer player;

	private void playMusic() {
		if (player == null) {
			player = MediaPlayer.create(this, R.raw.kimberley);
		}
		player.start();
	}

	private void pauseMusic() {
		if (player != null && player.isPlaying()) {
			player.pause();
		}
	}

	private void stopMusic() {
		if (player != null) {
			player.stop();
			player.reset(); // 重置MediaPlayer
			player.release(); // 释放加载的音乐资源
			player = null; // 保证exitMusic()调用stopMusic时能正常通过判断
		}
	}

}
