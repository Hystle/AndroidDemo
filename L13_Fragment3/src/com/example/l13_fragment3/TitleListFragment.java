package com.example.l13_fragment3;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 用来显示标题列表的Fragment: 静态加载
 * 继承ListFragment
 * @author Zach
 *
 */
public class TitleListFragment extends ListFragment{
	// super的onCreateView会创建一个ListView并返回,因此不能在其返回方法前设置adapter显示列表; 而是在onActivityCreated()中
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*
		 * 设置点击变色
		 * 1. 在item的layout文件中加上系统自带的小功能android:background="?android:attr/activatedBackgroundIndicator"
		 * 2. 引用此layout
		 * 3. 设置ListView为单选
		 */
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// 给listView设置adapter显示列表
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, DataUtils.TITLES));
		
		// 默认选中第一个item
		getListView().setItemChecked(0, true);
		showDetail(0);
	}
	
//	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetail(position);
	}

	private void showDetail(int position) {
		// 1. 创建DetailFragment
		DetailFragment fragment = new DetailFragment();
		// 2. 将对应的详细数据传过去
		Bundle args = new Bundle();
		args.putString("DETAIL", DataUtils.DETAILS[position]);
		fragment.setArguments(args);
		// 3. 将其替换到id为fl_main_container的相应容器布局中
		getFragmentManager().beginTransaction().replace(R.id.fl_main_container, fragment).commit();
	}
}
