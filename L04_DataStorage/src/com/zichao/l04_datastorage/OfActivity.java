package com.zichao.l04_datastorage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OfActivity extends Activity {

	private EditText et_of_name;
	private EditText et_of_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_of);
		et_of_name = (EditText) findViewById(R.id.et_of_name);
		et_of_value = (EditText) findViewById(R.id.et_of_value);
	}
	
	/**
	 * 保存/读取 
	 * 路径1: /storage/sdcard/Android/data/packageName/files/
	 */
	public void save(View v) throws UnsupportedEncodingException, IOException{
		// 1. 判断SD卡是否已插入
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			// 2. 得到文件名和内容
			String fileName = et_of_name.getText().toString();
			String fileValue = et_of_value.getText().toString();
			// 3. 得到OutputStream
			// 1) 组成完整的文件保存路径
			File file = getExternalFilesDir(null);
			String folderPath = file.getAbsolutePath();
			String filePath = folderPath + "/" + fileName;
			// 2) 创建文件流
			FileOutputStream fos = new FileOutputStream(filePath);
			// 3) 写数据
			fos.write(fileValue.getBytes("UTF-8"));
			fos.close();
			// 4. 在manifest中添加操作sd卡的权限!
			// 5. 提示
			Toast.makeText(this, "保存完成", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "没有SD卡", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void read(View v) throws Exception{
		String fileName = et_of_name.getText().toString();
		// 1. 组成文件路径
		File file = getExternalFilesDir(null);
		String folderPath = file.getAbsolutePath();
		String filePath = folderPath + "/" + fileName;
		// 2. 创建输入流
		FileInputStream fis = new FileInputStream(filePath);
		// 3. 将输入流-->字符串: 利用ByteArrayOutpuStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = fis.read(buffer)) != -1){
			baos.write(buffer, 0, len);
		}
		String fileValue = baos.toString();
		// 4. 显示
		et_of_value.setText(fileValue);
	}
	
	/**
	 * 保存/读取 
	 * 路径2: /storage/sdcard/xxx/
	 * @throws Exception 
	 */
	public void save2(View v) throws Exception{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			String fileName = et_of_name.getText().toString();
			String fileValue = et_of_value.getText().toString();
			// 1. 组件文件路径: /storage/sdcard/ZICHAO/xxx.txt
			// 1) /storage/sdcard/
			String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			// 2) /storage/sdcard/ZICHAO/
			File file  = new File(SDPath + "/ZICHAO"); 
			if(!file.exists()){	// 若不存在则创建
				file.mkdirs();
			}
			//3) /storage/sdcard/ZICHAO/xxx.txt
			String filePath = SDPath + "/ZICHAO/" + fileName;
			// 2. 创建文件输出流
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(fileValue.getBytes("UTF-8"));
			fos.close();
			// 3. 提示
			Toast.makeText(this, "保存完成", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
		}
	}
	public void read2(View v) throws Exception{
		String fileName = et_of_name.getText().toString();
		String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		String filePath = SDPath + "/ZICHAO/" + fileName;
		FileInputStream fis = new FileInputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = fis.read(buffer)) != -1){
			baos.write(buffer, 0, len);
		}
		String fileValue = baos.toString();
		fis.close();
		et_of_value.setText(fileValue);
	}
}
