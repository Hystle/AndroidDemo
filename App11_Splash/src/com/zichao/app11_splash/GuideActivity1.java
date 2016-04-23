package com.zichao.app11_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 活动页1
 * @author Zach
 *
 */
public class GuideActivity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_activity1);
	}
	
	public void nextPage(View v){
		startActivity(new Intent(this, GuideActivity2.class));
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
}
