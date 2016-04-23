package com.zichao.app11_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

/**
 * 欢迎界面
 * 1. 全屏显示需要在manifest中设置此activity的theme(不是整个应用的theme)
 * 2. 欢迎界面应该无法被返回并再次显示
 * 3. 自定义进度条: 1)在res中添加img_progress.xml 2)将其加载入activity_splash.xml的ProgressBar中
 * @author Zach
 *
 */
public class SplashActivity extends Activity {

	private RelativeLayout rl_splash_root;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				startActivity(new Intent(SplashActivity.this, GuideActivity1.class));
				// 保证无法返回此界面
				finish();
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);

		showAnimation();
		// 延迟3秒后结束欢迎界面
		handler.sendEmptyMessageDelayed(1, 3000);
	}

	private void showAnimation() {
		//旋转动画  RotateAnimation: 0-->360 视图的中心点 2s
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(2000);
		//透明度动画 AlphaAnimation: 0-->1 2s
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		//缩放动画 ScaleAnimation: 0-->1 视图的中心点 2s
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(2000);
		//创建复合动画,并添加
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		//启动
		rl_splash_root.startAnimation(animationSet);
	}
}
