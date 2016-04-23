package com.atguigu.l05_handler.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.test.AndroidTestCase;

public class JsonTest extends AndroidTestCase{
	/*
	 *  1. 将json格式的字符串{}转换为Java对象, 使用原生API
	 */
	public void testJsonToString() throws Exception{
		String jsonString = "{'id':1, 'name':'大虾','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'}";
		
		// 将Json字符串封装为JsonObject对象
		JSONObject jsonObject = new JSONObject(jsonString);
		
		// 从对象中根据key得到value
		int id = jsonObject.getInt("id");
		String name = jsonObject.getString("name");
		double price = jsonObject.getDouble("price");
		String imagePath = jsonObject.getString("imagePath");
		
		// 封装为ShopInfo对象
		ShopInfo shopInfo = new ShopInfo(id, name, price, imagePath);
		System.out.println(shopInfo);
	}
	
	/*
	 * 2. 将json格式的字符串{}转换为Java对象, 使用GSON
	 */
	public void testJsonToString2(){
		String jsonString = "{'id':2, 'name':'大虾','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'}";

		ShopInfo shopInfo = new Gson().fromJson(jsonString, ShopInfo.class);
		System.out.println(shopInfo);
	}
	
	/*
	 * 3. 将json格式的字符串[]转换为Java对象的List, 使用原生API
	 */
	public void testJsonToList() throws JSONException{
		String jsonString = "[{'id':3, 'name':'大虾3','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'},"
						+ "{'id':4, 'name':'大虾4','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'}]";
		
		// 将JSON字符串包装为JSONArray对象
		JSONArray jsonArray = new JSONArray(jsonString);
		
		// 遍历JsonArray对象的所有元素(即JsonObject),并封装为ShopInfo对象,添加到list
		List<ShopInfo> list = new ArrayList<ShopInfo>();
		for(int i=0; i<jsonArray.length(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			int id = jsonObject.getInt("id");
			String name = jsonObject.getString("name");
			double price = jsonObject.getDouble("price");
			String imagePath = jsonObject.getString("imagePath");
			// 封装为ShopInfo对象
			ShopInfo shopInfo = new ShopInfo(id, name, price, imagePath);
			list.add(shopInfo);
		}
		System.out.println(list.toString());
	}
	
	/*
	 * 4. 将json格式的字符串[]转换为Java对象的List, 使用GSON
	 */
	public void testJsonToList2() throws JSONException{
		String jsonString = "[{'id':5, 'name':'大虾5','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'},"
						+ "{'id':6, 'name':'大虾6','price':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'}]";
		
		/*
		 * 将JsonArray转换为List的第二个参数需要使用Type对象 
		 * TypeToken<T>: 用来得到Type的类
		 * 但是由于TypeToken<List<ShopInfo>>()的构造方法为:protected TypeToken(), 即只能由是同一个包或是子类访问
		 * 因此添加一个{}表示定义一个匿名内部类继承于TypeToken
		 */
		List<ShopInfo> list = new Gson().fromJson(jsonString, new TypeToken<List<ShopInfo>>(){}.getType());
		System.out.println(list.toString());
	}
	
	/*
	 * 5. 将Java对象转换为json字符串{}, 使用GSON
	 */
	public void testObjectToJson(){
		ShopInfo shopInfo = new ShopInfo(3, "AAA", 123.4, "http://www.google.com");
		String jsonString = new Gson().toJson(shopInfo);
		System.out.println(jsonString);
	}
	
	/*
	 * 6. 将Java对象的List转换为json字符串[], 使用GSON
	 */
	public void testListToJson(){
		List<ShopInfo> list = new ArrayList<ShopInfo>();
		list.add(new ShopInfo(4, "BBB", 123.4, "http://www.google.com"));
		list.add(new ShopInfo(5, "CCC", 123.4, "http://www.google.com"));
		String jsonString = new Gson().toJson(list);
		System.out.println(jsonString);
	}
	
	/*
	 * 7. 将json格式的字符串{}转换为Map对象, 使用GSON
	 * JsonString的某些key为特殊值无法创建对应Java对象, 可以封装为Map
	 */
	public void testJsonToMap(){
		String jsonString = "{'id':2, 'my name':'大虾','1':12.3,	'imagePath':'http://192.168.10.165:8080/L05_Server/images/f1.jpg'}";

		Map<String, Object> map = new Gson().fromJson(jsonString, new TypeToken<Map<String, Object>>(){}.getType());
		System.out.println(map.toString());
	}
	
	/*
	 * 8. 复杂的情况: 从下面找出第1天的最高温度
	 */
	public void testComplicated() throws JSONException{
		final String weather_data = "{\"cod\":\"200\",\"message\":4.2116,\"city\":{\"id\":\"5375480\",\"name\":\"Mountain View\",\"coord\":{\"lon\":-122.075,\"lat\":37.4103},\"country\":\"United States of America\",\"population\":0},\"cnt\":7,\"list\":[{\"dt\":1401912000,\"temp\":{\"day\":20.17,\"min\":12.3,\"max\":20.17,\"night\":12.3,\"eve\":17.74,\"morn\":14.05},\"pressure\":1012.43,\"humidity\":77,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":1.67,\"deg\":253,\"clouds\":0},{\"dt\":1401998400,\"temp\":{\"day\":18.9,\"min\":10.74,\"max\":18.9,\"night\":10.74,\"eve\":15.54,\"morn\":14.02},\"pressure\":1009.89,\"humidity\":76,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":1.51,\"deg\":225,\"clouds\":0},{\"dt\":1402084800,\"temp\":{\"day\":13.59,\"min\":13.57,\"max\":14.1,\"night\":14.1,\"eve\":14.04,\"morn\":13.57},\"pressure\":1022.58,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":8.92,\"deg\":325,\"clouds\":0},{\"dt\":1402171200,\"temp\":{\"day\":13.71,\"min\":13.71,\"max\":13.93,\"night\":13.93,\"eve\":13.73,\"morn\":13.93},\"pressure\":1021.29,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":6.41,\"deg\":326,\"clouds\":0},{\"dt\":1402257600,\"temp\":{\"day\":13.55,\"min\":13.52,\"max\":13.72,\"night\":13.52,\"eve\":13.72,\"morn\":13.62},\"pressure\":1022.14,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":9.72,\"deg\":320,\"clouds\":0},{\"dt\":1402344000,\"temp\":{\"day\":12.72,\"min\":12.72,\"max\":13.22,\"night\":13.22,\"eve\":13.19,\"morn\":13.06},\"pressure\":1027.87,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":7.85,\"deg\":322,\"clouds\":7},{\"dt\":1402430400,\"temp\":{\"day\":13.11,\"min\":12.89,\"max\":13.35,\"night\":13.26,\"eve\":13.35,\"morn\":12.89},\"pressure\":1029.35,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":11.01,\"deg\":330,\"clouds\":0}]}";
		JSONObject weather = new JSONObject(weather_data);
		JSONArray days = weather.getJSONArray("list");
		JSONObject day = days.getJSONObject(1);
		JSONObject temp = day.getJSONObject("temp");
		Double maxTemp = temp.getDouble("max");
		System.out.println(maxTemp);
	}
}
