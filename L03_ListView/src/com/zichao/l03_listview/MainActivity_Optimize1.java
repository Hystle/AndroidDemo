package com.zichao.l03_listview;

import java.util.ArrayList;
import java.util.List;

import com.zichao.l03_listview.entities.ShopInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity_Optimize1 extends Activity {

	private ListView lv_main;
	private List<ShopInfo> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv_main = (ListView) findViewById(R.id.lv_main);
		
		// 1. 准备集合数据: 每一个map中的key必须一致
		data = new ArrayList<ShopInfo>();
		data.add(new ShopInfo(R.drawable.f1, "name --- 1", "content --- 1"));
		data.add(new ShopInfo(R.drawable.f2, "name --- 2", "content --- 2"));
		data.add(new ShopInfo(R.drawable.f3, "name --- 3", "content --- 3"));
		data.add(new ShopInfo(R.drawable.f4, "name --- 4", "content --- 4"));
		data.add(new ShopInfo(R.drawable.f5, "name --- 5", "content --- 5"));
		data.add(new ShopInfo(R.drawable.f6, "name --- 6", "content --- 6"));
		data.add(new ShopInfo(R.drawable.f7, "name --- 7", "content --- 7"));
		data.add(new ShopInfo(R.drawable.f8, "name --- 8", "content --- 8"));
		data.add(new ShopInfo(R.drawable.f9, "name --- 9", "content --- 9"));
		data.add(new ShopInfo(R.drawable.f10, "name --- 10", "content --- 10"));
		
		// 2. 准备Adapter对象
		MyAdapter adapter = new MyAdapter();
		
		// 3. 设置Adapter对象
		lv_main.setAdapter(adapter);
	}
	
	class MyAdapter extends BaseAdapter{

		// 返回集合数据的数量
		@Override
		public int getCount() {
			return data.size();
		}
		
		// 返回指定下标对应的数据对象
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		/**
		 *  返回指定下标所对应的item的View对象,并封装了数据
		 *  position:下标
		 *  convertView:可复用的缓存Item视图对象,即翻页后隐藏的Item,可以对其重写加载数据就不需要新建View对象
		 *  parent: ListView对象
 		 */
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			/*
			 *  第一级优化: 利用convertView进行判断
			 * 	只有在没有convertView时才新建,保证View的数量最多只有n+1个(n为每页显示的条目;当首条和末条各显示一部分时为n+1)
			 *  否则每次滑动页面显示新的Item都会新建View对象,内存会瞬间溢出
			 */
			if(convertView == null){
			// 加载item的布局, 得到View对象
				convertView = View.inflate(MainActivity_Optimize1.this, R.layout.item_simple_adapter, null);
			}
			// 根据下标position,为View设置数据
			// 得到对象
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_icon);
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_item_name);
			TextView tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);
			ShopInfo shopInfo = data.get(position);
			// 为对象赋值
			iv.setImageResource(shopInfo.getIcon());
			tv_name.setText(shopInfo.getName());
			tv_content.setText(shopInfo.getContent());
			
			return convertView;
		}
	}
}
