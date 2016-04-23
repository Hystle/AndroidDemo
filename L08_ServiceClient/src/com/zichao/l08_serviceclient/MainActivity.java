package com.zichao.l08_serviceclient;

import com.zichao.l08_service.remote.IStudentService;
import com.zichao.l08_service.remote.Student;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_aidl_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_aidl_id = (EditText) findViewById(R.id.et_aidl_id);
	}

	private IStudentService studentService;		// 
	private ServiceConnection conn;		// bindService()需要ServiceConnection的参数

	/**
	 * 绑定启动Remote Service:与绑定启动Local Service类似
	 * 目的: 得到studentService对象
	 * @param v
	 */
	public void bindRemoteService(View v){
		// Action为服务器端Service的manifest中配置的intent-filter
		String action = "com.zichao.l08_service.remote.MyRemoteService.Action";
		Intent intent = new Intent(action);

		if(conn == null){
			conn = new ServiceConnection() {
				@Override
				public void onServiceDisconnected(ComponentName name) {

				}
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					Log.e("TAG", "onServiceConnected()");
					// 得到studentService对象
					studentService = IStudentService.Stub.asInterface(service);
				}
			};
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
			Toast.makeText(this, "Bind Service", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "Already Bind Service", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 使用studentService对象来调用服务端的getStudentById()方法
	 * @param v
	 * @throws Exception
	 */
	public void invokeRemote(View v) throws Exception{
		if(studentService != null){
			int id = Integer.parseInt(et_aidl_id.getText().toString());
			// 调用服务端的getStudentById()方法
			Student student = studentService.getStudentById(id);
			Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 解绑Remote Service:与解绑Local Service类似
	 * @param v
	 */
	public void unbindRemoteService(View v){
		if(conn != null){
			unbindService(conn);
			conn = null;
			studentService = null;
			Toast.makeText(this, "Unbind Service", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Have not Bind Service", Toast.LENGTH_SHORT).show();
		}
	}
}
