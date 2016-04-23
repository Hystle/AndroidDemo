package com.zichao.app11_splash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_main_input;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_main_input = (EditText) findViewById(R.id.et_main_input);
	}
	
	public void login(View v){
		// 得到输入文本
		String name = et_main_input.getText().toString();
		if(name.trim().equals("")){
			Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_main_input.startAnimation(animation);
		}
		else{
			Toast.makeText(this, name, Toast.LENGTH_SHORT).show();;
		}
	}
}
