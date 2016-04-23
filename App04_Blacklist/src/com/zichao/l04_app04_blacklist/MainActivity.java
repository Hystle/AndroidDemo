package com.zichao.l04_app04_blacklist;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 使用ListActivity优化功能
 *	1. extends ListActivity
 *	2. 布局文件中的<ListView>的id必须是系统定义的id: list
 *	3. 如果想在没有数据时显示一个全屏的提示文本 (在布局中定义 一个<TextView>,id必须为empty)
 * @author Zach
 *
 */
public class MainActivity extends ListActivity {
	private ListView lv_main;
	private BlackListAdapter adapter;
	private List<BlackListItem> data;
	private BlackListDAO dao;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 不能再findViewById了;系统提供的方法得到lv_main
		lv_main = getListView();
		adapter = new BlackListAdapter();
		dao = new BlackListDAO(this);
		data = dao.getAll();
		lv_main.setAdapter(adapter);
		lv_main.setOnCreateContextMenuListener(this);
		
	}
	
	public void addBlackList(View v){
		final EditText et = new EditText(this);
		et.setHint("输入黑名单号码");
		new AlertDialog.Builder(this).setTitle("添加黑名单")
			.setView(et)
			.setPositiveButton("添加", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String number = et.getText().toString();
					BlackListItem item = new BlackListItem(-1, number);
					dao.add(item);
					// util.List的方法:在List的指定位置插入数据; 同时,需要在DAO中将查询结果倒序输出
					data.add(0, item);
					adapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton("取消", null)
			.show();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(ContextMenu.NONE, 1, ContextMenu.NONE, "更新");
		menu.add(ContextMenu.NONE, 2, ContextMenu.NONE, "删除");
		// 得到选择条目的position
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		position = info.position;
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 根据position得到blackListItem对象
		BlackListItem blackListItem = data.get(position);
		switch (item.getItemId()) {
		case 1:
			showUpdateDialog(blackListItem);
			break;
		case 2:
			dao.deleteById(blackListItem.getId());
			data.remove(position);
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	/*
	 * 显示更新的AlertDialog
	 */
	private void showUpdateDialog(final BlackListItem blackListItem) {
		final EditText et = new EditText(this);
		et.setHint(blackListItem.getNumber());
		new AlertDialog.Builder(this).setTitle("更新黑名单")
			.setView(et)
			.setPositiveButton("更新", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					blackListItem.setNumber(et.getText().toString());
					dao.update(blackListItem);
					adapter.notifyDataSetChanged();
				}
			})
			.setNegativeButton("取消", null)
			.show();
	}

	class BlackListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 使用自带的简单视图
			if(convertView == null){
				convertView = View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
			}
			
			BlackListItem blackListItem = data.get(position);
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			tv.setText(blackListItem.getNumber());

			return convertView;
		}
		
	}
}
