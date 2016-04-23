package com.zichao.app10_contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText et_main_num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_main_num = (EditText) findViewById(R.id.et_main_num);
	}
	
	public void toContacts(View v){
		// 启动联系人列表界面: 带回调的startActivity
		startActivityForResult(new Intent(this, ContactsActivity.class), 1);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode == RESULT_OK){
			// 得到返回的number
			String number = data.getStringExtra("NUMBER");
			// 显示
			et_main_num.setText(number);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
