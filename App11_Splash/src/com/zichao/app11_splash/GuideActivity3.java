package com.zichao.app11_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * 活动页3
 * @author Zach
 *
 */
public class GuideActivity3 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_activity3);
	}
	
	public void confirm(View v){
		startActivity(new Intent(this, MainActivity.class));
		overridePendingTransition(R.anim.bottom_in, R.anim.alpha_out);
	}
	
	public void prevPage(View v){
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
