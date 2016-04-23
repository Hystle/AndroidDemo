package com.zichao.l04_app04_rename;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemLongClickListener {

	private GridView gv_main;
	private MainAdapter adapter;
	private SharedPreferences sp;
	
	String[] names = new String[] { "手机防盗", "通讯卫士", "软件管理", "流量管理", "进程管理", "手机杀毒",
			"缓存清理", "高级工具", "设置中心" };

	int[] icons = new int[] { 
		R.drawable.widget01, R.drawable.widget02, R.drawable.widget03,
		R.drawable.widget04, R.drawable.widget05, R.drawable.widget06,
		R.drawable.widget07, R.drawable.widget08, R.drawable.widget09 };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gv_main = (GridView) findViewById(R.id.gv_main);
		adapter = new MainAdapter(this, names,icons);
		
		gv_main.setAdapter(adapter);
		
		sp = getSharedPreferences("zzc", Context.MODE_PRIVATE);
		
		/*
		 * 点击显示名字
		 */
		gv_main.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String name = names[position];
				Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
			}
		});
		
		/*
		 * 长按修改名字
		 */
		gv_main.setOnItemLongClickListener(this);
	}

	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// 为第一个图标添加长按监听:弹出对话框进行重命名
		if (position == 0){
			// 从点击的item中取出name
			final TextView textView = (TextView) view.findViewById(R.id.tv_item_name);
			String name = textView.getText().toString();
			// 为dialog准备layout:此处直接使用一个EditText, 不需要导入配置文件
			final EditText editText = new EditText(this);
			editText.setHint(name);
			// 拼装dialog
			new AlertDialog.Builder(this)
				.setTitle("重命名")
				.setView(editText)	// 设置布局
				.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newName = editText.getText().toString();
						// 保存newName到SharedPreferences中
						sp.edit().putString("NAME", newName).commit();
						// 注意: 必须在adapter中读取最新的newName,否则会再次被刷新为原来的名字
						textView.setText(newName);
					}
				})
				.setNegativeButton("取消", null)
				.show();
		}
		return false;
	}
}
