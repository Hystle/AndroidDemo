package com.atguigu.l12_graphics;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/*
	Matrix ，中文里叫矩阵，高等数学里有介绍，在图像处理方面，主要是用于平面的缩放、平移、旋转等操作
	
 */
public class MatrixTestActivity extends Activity {

	private EditText et_matrix_scale;
	private EditText et_matrix_rotate;
	private EditText et_matrix_translateX;	// 偏移量
	private EditText et_matrix_translateY;
	
	private ImageView iv_matrix_icon;

	private Matrix matrix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrix);

		et_matrix_scale = (EditText) findViewById(R.id.et_matrix_scale);
		et_matrix_rotate = (EditText) findViewById(R.id.et_matrix_rotate);
		et_matrix_translateX = (EditText) findViewById(R.id.et_matrix_translateX);
		et_matrix_translateY = (EditText) findViewById(R.id.et_matrix_translateY);

		iv_matrix_icon = (ImageView) findViewById(R.id.iv_matrix_icon);
		
		matrix = new Matrix();
	}

	
	public void scaleBitmap(View view) {
		float scale = Float.parseFloat(et_matrix_scale.getText().toString());
		// 1.设置matrix的缩放比例
		matrix.postScale(scale, scale);
		// 2.将matrix设置到ImageView上, 使其生效
		iv_matrix_icon.setImageMatrix(matrix);
		// 3. 在layout文件中指定此ImageView的scaleType为matrix
	}

	public void rotateBitmap(View view) {
		float degree = Float.parseFloat(et_matrix_rotate.getText().toString());
		matrix.postRotate(degree);
		iv_matrix_icon.setImageMatrix(matrix);
	}

	public void translateBitmap(View view) {
		float dx = Float.parseFloat(et_matrix_translateX.getText().toString());
		float dy = Float.parseFloat(et_matrix_translateY.getText().toString());
		matrix.postTranslate(dx, dy);
		iv_matrix_icon.setImageMatrix(matrix);
	}

	public void clearMatrix(View view) {
		matrix.reset();
		iv_matrix_icon.setImageMatrix(matrix);
	}
}
