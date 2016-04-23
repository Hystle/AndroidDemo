package com.zichao.app05_shoplist;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int WHAT_REQUEST_SUCCESS = 1;
	protected static final int WHAT_REQUEST_ERROR = 2;
	private ListView lv_main;
	private LinearLayout ll_main_loading;
	private List<ShopInfo> data;
	private ShopInfoAdapter adapter;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_REQUEST_SUCCESS:
				ll_main_loading.setVisibility(View.GONE);
				lv_main.setAdapter(adapter);
				break;
			case WHAT_REQUEST_ERROR:
				ll_main_loading.setVisibility(View.INVISIBLE);
				Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv_main = (ListView) findViewById(R.id.lv_main);
		ll_main_loading = (LinearLayout) findViewById(R.id.ll_main_loading);
		adapter = new ShopInfoAdapter();

		// 1. 主线程显示提示视图
		ll_main_loading.setVisibility(View.VISIBLE);
		// 2. 分线程联网请求
		// 启动分线程请求服务器动态加载数据并显示
		new Thread(){
			public void run(){
				// 得到jsonString
				try {
					String jsonString = getJsonString();
					data = new Gson().fromJson(jsonString, new TypeToken<List<ShopInfo>>(){}.getType());
					// 3. 主线程更新界面
					handler.sendEmptyMessage(WHAT_REQUEST_SUCCESS);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(WHAT_REQUEST_ERROR);
				}
			}
		}.start();



	}
	
	/**
	 * 使用原生的HttpURLConnection联网得到JsonString
	 */
	private String getJsonString() throws Exception {
		// 请求地址
		String path = "http://192.168.2.10:8080/L05_WebServer/ShopInfoListServlet";
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.connect();
		InputStream is = connection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		baos.close();
		is.close();
		connection.disconnect();
		String result = baos.toString();

		return result;
	}

	class ShopInfoAdapter extends BaseAdapter{
		private ImageLoader imageLoader;

		// 只需要一个ImageLoader对象: 在构造器中新建对象
		public ShopInfoAdapter() {
			imageLoader = new ImageLoader(MainActivity.this, R.drawable.loading, R.drawable.error);
		}

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
				convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
			}
			// 得到当前行的数据对象
			ShopInfo shopInfo = data.get(position);
			// 得到当前行的子View
			TextView nameTv = (TextView) convertView.findViewById(R.id.tv_item_name);
			TextView contentTv = (TextView) convertView.findViewById(R.id.tv_item_content);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_icon);
			// 设置数据
			nameTv.setText(shopInfo.getName());
			contentTv.setText(shopInfo.getPrice() + " 元");
			String imagePath = shopInfo.getImagePath();
			/*
			 * 根据图片路径, 动态请求服务加载图片并显示
			 */
			imageLoader.loadImage(imagePath, iv);

			return convertView;
		}

	}
}
