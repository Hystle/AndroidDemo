package com.zichao.app11_splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * 活动页2
 * @author Zach
 *
 */
public class GuideActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_activity2);
	}
	
	public void nextPage(View v){
		startActivity(new Intent(this, GuideActivity3.class));
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
	
	public void prevPage(View v){
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
