package com.zichao.l03_app03_gridview;

import android.content.Context;
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

	public MainAdapter(Context context, String[] names, int[] icons) {
		super();
		this.names = names;
		this.icons = icons;
		this.context = context;
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
		tv.setText(names[position]);
		
		return convertView;
	}

}
