package com.zichao.app12_popupwindow;

import java.util.ArrayList;
import java.util.List;

import com.zichao.app12_popupwindow.entities.AppInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemLongClickListener, OnClickListener {

	private ListView lv_main;
	private List<AppInfo> data;
	private AppAdapter adapter;
	private PopupWindow pw;
	private View pwView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 初始化成员变量
		lv_main = (ListView) findViewById(R.id.lv_main);
		data = getAllAppInfos();
		adapter = new AppAdapter();
		lv_main.setAdapter(adapter);
		
		// 功能1: 长按删除item
		lv_main.setOnItemLongClickListener(this);
		
		// 功能2: 点击显示popup window
		lv_main.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String appName = data.get(position).getAppName();
				/*
				 *  PopupWindow
				 */
				if(pw == null){
					// 1. 导入布局
					pwView = View.inflate(MainActivity.this, R.layout.pw_layout, null);
					// 得到子View, 设置点击监听
					pwView.findViewById(R.id.ll_pw_uninstall).setOnClickListener(MainActivity.this);
					pwView.findViewById(R.id.ll_pw_run).setOnClickListener(MainActivity.this);
					pwView.findViewById(R.id.ll_pw_share).setOnClickListener(MainActivity.this);
					pw = new PopupWindow(pwView, view.getWidth()-80, view.getHeight());
					// 必须设置背景,否则无法显示动画, 内容任意
					pw.setBackgroundDrawable(new BitmapDrawable());
				}
				
				// 若正在显示, 先移除
				if(pw.isShowing()){
					pw.dismiss(); 	// 仅不显示,但对象仍然在内存中
				}
				// (再)显示
				// showAsDropDown(View anchor, int xoff, int yoff): 起点为左下角
				pw.showAsDropDown(view, 40, -view.getHeight());
				
				ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1);
				animation.setDuration(1000);
				// 调用者为变化的图形对象
				pwView.startAnimation(animation);
			}
		});

		// 功能3: 滚动时取消popup window
		lv_main.setOnScrollListener(new OnScrollListener() {
			/*
			 *  当ListView的滚动状态改变时调用
			 *  1. SCROLL_STATE_IDLE: 空闲
			 *  2. SCROLL_STATE_TOUCH_SCROLL: 手指拖动界面
			 *  3. SCROLL_STATE_FLING: 手指甩动界面
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Log.e("TAG", "onScrollStateChanged()...");
				if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
					if(pw != null && pw.isShowing()){
						pw.dismiss();
					}
				}
			}
			 // 当ListView在滚动时不断地调用
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				Log.e("Tag", "onScroll()...");
			}
		});
	}

	/**
	 * 长按删除item
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// 删除当前行数据
		data.remove(position);
		// 刷新ListView
		adapter.notifyDataSetChanged();
		return true;
	}

	/**
	 * 得到手机中所有应用信息的列表 AppInfo 
	 * Drawable icon; String appName; String packageName
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

	@Override
	public void onClick(View v) {
		switch ((v.getId())) {
		case R.id.ll_pw_uninstall:
			Toast.makeText(this, "Uninstall", Toast.LENGTH_SHORT).show();
			pw.dismiss();
			break;
		case R.id.ll_pw_run:
			Toast.makeText(this, "Uninstall", Toast.LENGTH_SHORT).show();
			pw.dismiss();
			break;
		case R.id.ll_pw_share:
			Toast.makeText(this, "Uninstall", Toast.LENGTH_SHORT).show();
			pw.dismiss();
			break;

		default:
			break;
		}
	}
}
