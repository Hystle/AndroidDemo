package com.zichao.l04_datastorage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class IfActivity extends Activity {

	private ImageView iv_if;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_if);
		iv_if = (ImageView) findViewById(R.id.iv_if);
	}

	public void save(View v) throws IOException {
		// 1. 读取assets下资源文件logo.png的输入流
		AssetManager manager = getAssets();
		InputStream is = manager.open("logo.png");
		// 2. 得到写入手机内部存储的输出流: /data/data/packageName/files/
		FileOutputStream fos = openFileOutput("logo.png", Context.MODE_PRIVATE);
		// 3. 边读边写(要熟练使用!)
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = is.read(buffer))!=-1){
			fos.write(buffer, 0, len);
		}
		fos.close();
		is.close();
		
		Toast.makeText(this, "已保存文件", Toast.LENGTH_SHORT).show();
	}

	public void read(View v) {
		// 1. 得到图片文件的路径
		String filePath = getFilesDir().getAbsolutePath();
		String imagePath = filePath + "/logo.png";
		// 2. 读取加载图片,得到Bitmap对象
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		iv_if.setImageBitmap(bitmap);
	}
}
