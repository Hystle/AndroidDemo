package com.zichao.l03_component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn_main_test1).setOnClickListener(this);
		findViewById(R.id.btn_main_test2).setOnClickListener(this);
		findViewById(R.id.btn_main_test3).setOnClickListener(this);
		findViewById(R.id.btn_main_test4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_main_test1:
		startActivity(new Intent(this, SimpleComponentActivity.class));
		break;
	case R.id.btn_main_test2:
		startActivity(new Intent(this, MenuActivity.class));
		break;
	case R.id.btn_main_test3:
		startActivity(new Intent(this, ProgressBarActivity.class));
		break;
	case R.id.btn_main_test4:
		startActivity(new Intent(this, DialogActivity.class));
		break;

	default:
		break;
	}
	}
}
