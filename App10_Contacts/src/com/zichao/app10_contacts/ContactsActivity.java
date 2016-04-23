package com.zichao.app10_contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 继承自ListActivity
 * @author Zach
 *
 */
public class ContactsActivity extends ListActivity implements OnItemClickListener {

	private ListView listView;
	private ContactAdapter adapter;
	private List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		// 初始化
		listView = getListView();
		adapter = new ContactAdapter();
		
		// 查询得到联系人的数据
		ContentResolver resolver = getContentResolver();
		Uri uri = Phone.CONTENT_URI;	// 联系人的Uri是一个常量
		String[] projection = {Phone.DISPLAY_NAME, Phone.NUMBER};	// 联系人名和号码分别为两列数据
		Cursor cursor = resolver.query(uri, projection, null, null, null);
		
		// 处理得到的结果集
		while(cursor.moveToNext()){
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("number", number);
			data.add(map);
		}
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);
	}
	
	class ContactAdapter extends BaseAdapter{

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
			if(convertView == null){
				convertView = View.inflate(ContactsActivity.this, R.layout.item_contact, null);
			}
			Map<String, String> map = data.get(position);
			TextView nameTv = (TextView) convertView.findViewById(R.id.tv_item_name);
			TextView numberTv = (TextView) convertView.findViewById(R.id.tv_item_number);
			nameTv.setText(map.get("name"));
			numberTv.setText(map.get("number"));
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String number = data.get(position).get("number");
		Intent intent = new Intent();
		intent.putExtra("NUMBER", number);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
