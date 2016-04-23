package com.zichao.l03_component;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleComponentActivity extends Activity {

	private TextView tv_simple_message;
	private Button btn_simple_submit;
	private EditText et_simple_number;
	private ImageView imageView;

	private CheckBox cb_simple_football;
	private CheckBox cb_simple_basketball;
	private CheckBox cb_simple_tennis;
	
	private RadioGroup rg_simple_sex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_component);
		// 1. TextView
		tv_simple_message = (TextView) findViewById(R.id.tv_simple_message);
		tv_simple_message.setText("This is TextVew Component!");
		// 2. EditView
		et_simple_number = (EditText) findViewById(R.id.et_simple_number);
		// 3. Button
		btn_simple_submit = (Button) findViewById(R.id.btn_simple_submit);
		btn_simple_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = et_simple_number.getText().toString();
				Toast.makeText(SimpleComponentActivity.this, content, Toast.LENGTH_SHORT).show();
			}
		});
		
		// 4. ImageView
		imageView = (ImageView) findViewById(R.id.iv_simple_play);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 *  此处使用的是系统的图片资源
				 *  1. 不能使用setBackground():此方法需要传入Drawable
				 *  2. 不能直接写R.drawable:会导致传入本项目的R.drawable中的资源而不是系统的;需要使用全类名区分
				 */
				imageView.setBackgroundResource(android.R.drawable.alert_dark_frame);
				imageView.setImageResource(android.R.drawable.ic_media_pause);
			}
		});
		
		// 5. CheckBox
		cb_simple_basketball = (CheckBox) findViewById(R.id.cb_simple_basketball);
		cb_simple_football = (CheckBox) findViewById(R.id.cb_simple_football);
		cb_simple_tennis = (CheckBox) findViewById(R.id.cb_simple_tennis);
		// 测试对checkBox的选中状态监听
		cb_simple_basketball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					Toast.makeText(SimpleComponentActivity.this, "篮球被选中", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(SimpleComponentActivity.this, "篮球未选中", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// 6. RadioGroup/RadioButton
		rg_simple_sex = (RadioGroup)findViewById(R.id.rg_simple_sex);
		rg_simple_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 找到选中的RadioButton
				RadioButton rb = (RadioButton) findViewById(checkedId);
				String value = rb.getText().toString();
				Toast.makeText(SimpleComponentActivity.this, value, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void submit(View v){
		StringBuffer sb = new StringBuffer();
		if(cb_simple_basketball.isChecked()){
			sb.append(cb_simple_basketball.getText().toString()).append(" ");
		}
		if(cb_simple_football.isChecked()){
			sb.append(cb_simple_football.getText().toString()).append(" ");
		}
		if(cb_simple_tennis.isChecked()){
			sb.append(cb_simple_tennis.getText().toString());
		}
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}
}
