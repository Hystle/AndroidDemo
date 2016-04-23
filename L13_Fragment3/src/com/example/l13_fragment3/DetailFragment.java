package com.example.l13_fragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 用来显示详情的Fragment: 动态加载
 * 继承Fragment
 * @author Zach
 *
 */
public class DetailFragment extends Fragment{
	/*
	 * 构建显示detail的TextView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 创建TextView
		TextView tv = new TextView(getActivity());
		// 设置数据
		String detail = getArguments().getString("DETAIL");
		tv.setText(detail);
		return tv;
	}
}
