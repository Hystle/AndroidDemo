package com.zichao.l03_component;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
/*
一. OptionMenu 
1. 如何触发Menu的显示? 
	点击menu键 
2. 如何向Menu中添加MenuItem? 
	重写onCreateOptionMenu()
	menu.add()或者加载菜单文件
3. 选择某个MenuItem时如何响应? 
	重写onOptionsItemSelected(), 根据itemId做响应
二. ContextMenu
1. 如何触发Menu的显示? 
	长按某个视图 
	view.setOnCreateContextMenuListener(this)
2. 如何向Menu中添加MenuItem? 
	重写onCreateContextMenu()
	menu.add()
3. 选择某个MenuItem时如何响应? 
	重写onContextItemSelected(), 根据itemId做响应
 */
public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		// 设置对Button的ContextMenu监听:Activity已经实现了OnCreateContextMenuListener,所以可以直接使用this
		findViewById(R.id.btn_menu_cm).setOnCreateContextMenuListener(this);
	}
	
	// 触发OptionMenu
	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		// 1. 直接编码方式
//		menu.add(Menu.NONE, 1, Menu.NONE, "添加(直接编码方式)");
//		menu.add(Menu.NONE, 2, Menu.NONE, "删除(直接编码方式)");
		// 2. 导入编写的Menu.xml文件方式
		// 1) 得到菜单加载器
		MenuInflater menuInflater = getMenuInflater();
		// 2) 将编写好的menu.xml加载到传入的m中
		menuInflater.inflate(R.menu.option_menu, m);
		return super.onCreateOptionsMenu(m);
	}
	
	// 响应OptionMenu的选择
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 1. 直接编码方式: id改为相应的1和2
		// 2. 导入编写的Menu.xml文件方式: xml中定义的id
		switch (item.getItemId()) {
		case R.id.add:
			Toast.makeText(this, "选择了添加", Toast.LENGTH_SHORT).show();
			break;
		case R.id.delete:
			Toast.makeText(this, "选择了删除", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// 触发ContextMenu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, 1, Menu.NONE, "添加");
		menu.add(Menu.NONE, 2, Menu.NONE, "删除");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	// 响应ContextMenu的选择
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Toast.makeText(this, "添加被点击", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(this, "删除被点击", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
