package com.example.l13_fragment2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment1 extends Fragment {
	/*
	 *  加载布局得到View对象并且返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// 创建视图对象并设置数据返回
		TextView textView = new TextView(getActivity());
		textView.setText("Fragment1");
		textView.setBackgroundColor(Color.CYAN);
		return textView;
	}
}
