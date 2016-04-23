package com.zichao.l04_datastorage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * 1. 测试SharedPreference存储
	 */
	public void onClickSP(View v){
		startActivity(new Intent(this, SpActivity.class));
	}
	
	/**
	 * 2. 测试InternalFile存储
	 */
	public void onClickIF(View v){
		startActivity(new Intent(this, IfActivity.class));
	}
	/**
	 * 3. 测试ExternalFile存储
	 */
	public void onClickOF(View v){
		startActivity(new Intent(this, OfActivity.class));
	}
	/**
	 * 4. 测试数据库存储
	 */
	public void onClickDB(View v){
		startActivity(new Intent(this, DBActivity.class));
	}
	/**
	 * 5. 测试数据库存储
	 */
	public void onClickNW(View v){
		startActivity(new Intent(this, NWActivity.class));
	}
}
