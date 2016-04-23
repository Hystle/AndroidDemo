package com.zichao.app05_shoplist;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 用于加载图片和显示
 * @author Zach
 *
 */
public class ImageLoader {

	private Context context;
	private int loadingImageRes;
	private int errorImageRes;

	public ImageLoader(Context context, int loadingImageRes, int errorImageRes) {
		super();
		this.context = context;
		this.loadingImageRes = loadingImageRes;
		this.errorImageRes = errorImageRes;
	}

	private Map<String, Bitmap> cacheMap = new HashMap<String, Bitmap>();

	public void loadImage(String imagePath, ImageView iv) {
		// 0. 解决图片闪动
		iv.setTag(imagePath);
		
		// 1. 一级缓存
		Bitmap bitmap = getFromFirstCache(imagePath);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
			return;
		}

		// 2. 二级缓存
		bitmap = getFromSecondCache(imagePath);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
			cacheMap.put(imagePath, bitmap);
			return;
		}

		// 3. 三级缓存
		loadBitmapFromThirdCache(imagePath, iv);
	}

	/**
	 * 根据URL从三级缓存中取
	 * final:
	 * 在内存中,变量存在于栈空间,变量实体存在于堆空间;
	 * final规定了变量指向堆空间中的某一个固定的对象, 其指向不能改变, 但是堆空间中的对象值可以改变
	 * 例: final Person p = new Person("AAA",12); 
	 * p指向这个Person("AAA",12)对象所在的位置,但Person中属性的值改变不受影响
	 */
	private void loadBitmapFromThirdCache(final String imagePath, final ImageView iv) {
		new AsyncTask<Void, Void, Bitmap>() {
			
			protected void onPreExecute() {
				iv.setImageResource(loadingImageRes);
			};

			@Override
			protected Bitmap doInBackground(Void... params) {
				Bitmap bitmap = null;
				try {

					// 0.1 向服务器取照片之前, 判断是否需要加载: 即是否ImageView已被复用
					String newImagePath = (String) iv.getTag();
					if(newImagePath != imagePath){
						return null;
					}
					
					URL url = new URL(imagePath);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.connect();
					int respCode = conn.getResponseCode();
					if(respCode == 200){
						InputStream is = conn.getInputStream();
						// 将InputStream封装为Bitmap
						bitmap = BitmapFactory.decodeStream(is);
						is.close();
						
						if(bitmap != null){
							// 缓存到一级缓存
							cacheMap.put(imagePath, bitmap);
							// 缓存到二级缓存
							String filesPath = context.getExternalFilesDir(null).getAbsolutePath();
							String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1); // f10.jpg
							String filePath = filesPath + "/" + fileName;
							bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(filePath));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return bitmap;
			}
			
			protected void onPostExecute(Bitmap bitmap) {
				// 0.2 从开始请求图片到真正得到图片并显示之间也有一小段时间,imageView也有可能被复用
				String newImagePath = (String) iv.getTag();
				if(newImagePath != imagePath){
					return;
				}
				
				if (bitmap == null){
					iv.setImageResource(errorImageRes);
				}
				else{
					iv.setImageBitmap(bitmap);
				}
			};
		}.execute();
	}

	/**
	 * 根据URL从二级缓存中取
	 */
	private Bitmap getFromSecondCache(String imagePath) {
		// 需要MainActivity的context对象来得到SD卡的位置
		String filesPath = context.getExternalFilesDir(null).getAbsolutePath();
		String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1); // f10.jpg
		String filePath = filesPath + "/" + fileName;
		return BitmapFactory.decodeFile(filePath);
	}

	/**
	 * 根据URL从一级缓存中取
	 */
	private Bitmap getFromFirstCache(String imagePath) {
		return cacheMap.get(imagePath);
	}

}
