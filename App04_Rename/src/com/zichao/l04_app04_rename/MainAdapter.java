package com.zichao.l04_app04_rename;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	// 需要将data传入
	private String[] names;
	private int[] icons;
	private Context context;
	private SharedPreferences sp;

	public MainAdapter(Context context, String[] names, int[] icons) {
		super();
		this.names = names;
		this.icons = icons;
		this.context = context;
		sp = context.getSharedPreferences("zzc", Context.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return names[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = View.inflate(context, R.layout.item_main, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_icon);
		TextView tv = (TextView) convertView.findViewById(R.id.tv_item_name);
		
		iv.setImageResource(icons[position]);
		tv.setText(names[position]);	// setText1: 首次显示时加载默认名字
		
		// 判断是否有更新的名字存放在SharedPreferences中
		if(position == 0){
			String savedName = sp.getString("NAME", null);
			if(savedName != null){
				tv.setText(savedName);	// setText2: 检查sharedPreferences中是否有保存过的新名字
			}
		}
		
		return convertView;
	}

}
