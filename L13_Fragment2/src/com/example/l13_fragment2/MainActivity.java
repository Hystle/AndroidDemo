package com.example.l13_fragment2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
/**
 * @author xfzhang
 * 测试使用Fragment(动态使用)
 * 1. 使用FragmentManager和FragmentTransaction动态使用一个Fragment
 * 2. 方式:
 * 		add(viewId, fragment): 将fragment的视图添加为指定视图的子视图(加在原有子视图的后面)
 * 		replace(viewId, fragment): 将fragment的视图添加为指定视图的子视图(先remove原有的子视图)
 *      remove(fragment) : 将fragment的视图移除
 * 
 */
public class MainActivity extends FragmentActivity {

	public MainActivity() {
		Log.e("TAG", "MainActivity()..");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("TAG", "MainActivity onCreate()..");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 创建Fragment对象 
		MyFragment1 fragment1 = new MyFragment1();
		// 创建FragmentManager对象
		FragmentManager manager = getSupportFragmentManager();
		// 得到FragmentTransaction
		FragmentTransaction transaction = manager.beginTransaction();
		// 添加Fragment对象, 并提交
		transaction.add(R.id.ll_main, fragment1).commit();

	}

	private MyFragment2 fragment2 = null;
	public void showFragment2(View v) {
		// 创建Fragment对象 
		fragment2 = new MyFragment2();
		// 创建FragmentManager对象
		FragmentManager manager = getSupportFragmentManager();
		// 得到FragmentTransaction
		FragmentTransaction transaction = manager.beginTransaction();
		// 若希望实现点击返回键时回到上一个Fragment: 添加当前操作到backStack
		transaction.addToBackStack(null);
		// 替换Fragment对象并提交
		transaction.replace(R.id.ll_main, fragment2).commit();
	}

	public void deleteFragment2(View v) {
		// 创建FragmentManager对象
		FragmentManager manager = getSupportFragmentManager();
		// 得到FragmentTransaction
		FragmentTransaction transaction = manager.beginTransaction();
		// 移除Fragment并提交
		transaction.remove(fragment2).commit();
	}
}
