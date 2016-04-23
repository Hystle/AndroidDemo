package com.zichao.l03_app03_listview;

import java.util.ArrayList;
import java.util.List;

import com.zichao.l03_app03_listview.entities.AppInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemLongClickListener {

	private ListView lv_main;
	private List<AppInfo> data;
	private AppAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 初始化成员变量
		lv_main = (ListView) findViewById(R.id.lv_main);
		data = getAllAppInfos();
		adapter = new AppAdapter();
		// 1. 显示列表
		lv_main.setAdapter(adapter);
		// 2. 为item添加点击事件
		lv_main.setOnItemClickListener(new OnItemClickListener() {
			/*
			 * parent: ListView
			 * view: 当前行的视图对象
			 * position: 当前行的下标
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 功能: 提示点击应用的名称
				String appName = data.get(position).getAppName();
				Toast.makeText(MainActivity.this, appName, Toast.LENGTH_SHORT).show();;
			}
		});
		// 3. 为item添加长按事件
		lv_main.setOnItemLongClickListener(this);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// 一. 删除当前行数据
		data.remove(position);
		// 二. 刷新ListView
		// 方法1 重新加载ListView布局: 删除后会返回第一条所在页面,而不是当前位置;即不使用缓存重新加载
//		lv_main.setAdapter(adapter);
		// 方法2 通知数据集变化: 会保持当前删除页的位置;使用了当前的缓存 -> 推荐!
		adapter.notifyDataSetChanged();
		return true;
	}

	/*
	 * 得到手机中所有应用信息的列表 AppInfo 
	 * Drawable icon 
	 * String appName 
	 * String packageName
	 */
	protected List<AppInfo> getAllAppInfos() {

		List<AppInfo> list = new ArrayList<AppInfo>();
		// 得到应用的packgeManager
		PackageManager packageManager = getPackageManager();
		// 创建一个主界面的intent
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 得到包含应用信息的列表
		List<ResolveInfo> ResolveInfos = packageManager.queryIntentActivities(intent, 0);
		// 遍历
		for (ResolveInfo ri : ResolveInfos) {
			// 得到包名
			String packageName = ri.activityInfo.packageName;
			// 得到图标
			Drawable icon = ri.loadIcon(packageManager);
			// 得到应用名称
			String appName = ri.loadLabel(packageManager).toString();
			// 封装应用信息对象
			AppInfo appInfo = new AppInfo(icon, appName, packageName);
			// 添加到list
			list.add(appInfo);
		}
		return list;
	}

	class AppAdapter extends BaseAdapter{

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

		// 返回带数据的当前行的Item视图对象
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 1. 加载布局文件
			if(convertView == null){
				convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
			}
			// 2. 得到当前行的数据
			AppInfo info = data.get(position);
			// 3. 得到当前行西药更新的子View对象
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_icon);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_item_name);
			// 4. 为子View设置数据
			iv.setImageDrawable(info.getIcon());
			tv.setText(info.getAppName());
			
			return convertView;
		}
		
	}

	
}
