package com.zichao.l03_component;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
	}
	
	/*
	 *  1. 一般AlertDialog: 方法链调用
	 */
	public void showAD(View v){
		new AlertDialog.Builder(this)
			.setTitle("删除数据").setMessage("确认删除吗???")
			.setPositiveButton("删了", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(DialogActivity.this, "点击了删除", Toast.LENGTH_SHORT).show();
				}
			})
			.setNegativeButton("算了", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(DialogActivity.this, "点击了放弃", Toast.LENGTH_SHORT).show();
				}
			})
			.show();
	}
	
	/*
	 *  2. 带单选列表的AlertDialog
	 */
	public void showLD(View v){
		// 选项的数组必须为final:否则局部变量在showLD方法调用的显示AlertDialog完成后就无效了,而后续的点击事件还没发生
		final String[] items = {"红", "蓝", "绿", "灰" };
		new AlertDialog.Builder(this)
		.setTitle("指定背景色")
		.setSingleChoiceItems(items, 3, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {	// which 是指items数组的下标
				// 提示点击的颜色:此时items数组必须还需要存在
				Toast.makeText(DialogActivity.this, items[which], Toast.LENGTH_SHORT).show();
				// 必须移除AlertDialog:将传入的dialog对象dismiss;DialogInterface是AlertDialog的顶层接口:多态现象
				dialog.dismiss();
			}
		})
		.show();
	}
	
	/*
	 *  3. 自定义AlertDialog
	 */
	public void showCD(View v){
		// 动态加载布局文件,得到View对象
		View customed_view = View.inflate(this, R.layout.customed_dialogview, null);
		/*	注意两个问题:
		 *  1. customed_view的真实类型:布局文件xml的根标签的类型; 
		 *  2. 如何从指定的xml文件中获取子标签的值: cutomed_view.findViewById();若不加会在activity_dialog.xml中查找
		 */
		final EditText nameET = (EditText) customed_view.findViewById(R.id.et_dialog_name);
		final EditText pwdET = (EditText) customed_view.findViewById(R.id.et_dialog_pwd);
		
		new AlertDialog.Builder(this)
		// 设置Dialog中的视图
		.setView(customed_view)
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = nameET.getText().toString() + ": "+ pwdET.getText().toString();
				Toast.makeText(DialogActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	/*
	 * 4. 圆形进度条ProgressBar: 模拟程序的执行,使进度条一小段时间后消失,并使用toast提示
	 * 1) 回调方法(如showPD)都在主线程执行,直接Thread.sleep()会导致主线程的点击按钮事件产生延迟,而不是dialog延迟
	 * 2) 因此长时间的操作需要启动分线程处理
	 * 3) dismiss()需要放在分线程中:若写在最后则仍然在主线程中,新建的分线程会与dialog无关地执行. dialog还是无法延迟后消失
	 * 4) 不能在分线程中直接更新UI:因此不能在分线程中直接使用toast方法; 而之前的dismiss方法是间接使用handler在主线程执行的
	 */
	public void showPD(View v) throws InterruptedException{		
		final ProgressDialog dialog = ProgressDialog.show(this, "请等待", "数据加载中...");
		// 启动一个分线程
		new Thread(){
			public void run(){
				for(int i=0; i<20; i++){
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 使dialog消失
				dialog.dismiss();
				
				// 显示toast
				runOnUiThread(new Runnable(){	// runOnUiThread()本身在分线程中
					@Override
					public void run() {		// runOnUiThread()的功能是让run()中的语句在UI线程,即主线程中执行
						Toast.makeText(DialogActivity.this, "加载完成了!!!", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}.start();
	}
	
	/*
	 * 5. 水平进度条ProgressBar
	 */
	public void showPD2(View v){
		// 1. 创建dialog对象
		final ProgressDialog pd = new ProgressDialog(this);
		// 2. 设置进度样式
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// 3. 显示
		pd.show();
		// 4. 启动分线程,加载数据,并显示进度;加载完成后移除dialog
		new Thread(new Runnable(){
			@Override
			public void run() {
				// 1) 设置最大的进度值
				int count = 20;
				pd.setMax(count);
				// 2) 启动进度
				for(int i=0; i<count; i++){
					try {
						Thread.sleep(100);		
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 3) 一次循环后设置进度
					pd.setProgress(pd.getProgress() + 1);
				}
				// 4) for循环完成后移除dialog
				pd.dismiss();
			}
		}).start();
	}
	
	/*
	 * 6. DatePickerDialog
	 */
	public void showDateAD(View v){
		// 创建日历对象
		Calendar calendar = Calendar.getInstance();
		// 得到当前的年月日
		final int year = calendar.get(Calendar.YEAR);//得到年份
		final int monthOfYear = calendar.get(Calendar.MONTH);//月
		final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//得到日
		
		// 创建DatePickerDialog对象
		new DatePickerDialog(this, new OnDateSetListener() {
			// 响应的回调函数
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// 注意: 月份从0开始,即monthOfYear为0-11
				Log.e("TAG", year + "/" + monthOfYear + "/" + dayOfMonth );
			}
		}, year, monthOfYear, dayOfMonth).show();
	}
	
	/*
	 * 7. TimePickerDialog
	 */
	public void showTimeAD(View v){
		Calendar c = Calendar.getInstance();
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY); //得到小时
		int minute = c.get(Calendar.MINUTE); //得到分钟
		
		// 创建TimePickerDialog对象
		new TimePickerDialog(this, new OnTimeSetListener() {
			// 响应的回调函数
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Log.e("TAG", hourOfDay + ":" + minute );
			}
		}, hourOfDay, minute, true).show();
	}
}
