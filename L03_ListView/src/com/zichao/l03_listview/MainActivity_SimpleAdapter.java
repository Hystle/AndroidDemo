package com.zichao.l03_listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity_SimpleAdapter extends Activity {

	private ListView lv_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv_main = (ListView) findViewById(R.id.lv_main);
		
		// 1. 准备集合数据: 每一个map中的key必须一致
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("icon", R.drawable.f1);
		map.put("name", "name --- 1");
		map.put("Content", "Content --- 1");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f2);
		map.put("name", "name --- 2");
		map.put("Content", "Content --- 2");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f3);
		map.put("name", "name --- 3");
		map.put("Content", "Content --- 3");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f4);
		map.put("name", "name --- 4");
		map.put("Content", "Content --- 4");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f5);
		map.put("name", "name --- 5");
		map.put("Content", "Content --- 5");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f6);
		map.put("name", "name --- 6");
		map.put("Content", "Content --- 6");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f7);
		map.put("name", "name --- 7");
		map.put("Content", "Content --- 7");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f8);
		map.put("name", "name --- 8");
		map.put("Content", "Content --- 8");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f9);
		map.put("name", "name --- 9");
		map.put("Content", "Content --- 9");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.f10);
		map.put("name", "name --- 10");
		map.put("Content", "Content --- 10");
		data.add(map);
		
		// 2. 准备from: 每一个map中的key 
		String[] from = {"icon", "name", "Content"};
		// 3. 准备to: layout中的View的id
		int[] to = {R.id.iv_item_icon,R.id.tv_item_name, R.id.tv_item_content};
		
		// 4. 准备SimpleAdapter对象
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_simple_adapter, from, to);
		// 5. 设置Adapter显示列表
		lv_main.setAdapter(adapter);
	}
}
