package com.zichao.l08_service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 测设Remote Service: 服务端
 * 注意: 需要在manifest中注册此Service,包含action供其他应用访问
 * @author Zach
 *
 */
public class MyRemoteService extends Service{

	/**
	 * 处理Student相关的业务逻辑类:
	 * 在gen包下,eclipse自动生成了IStudentService的aidl代码
	 * IStudentService是抽象接口;Stub是一个抽象类,继承了Binder
	 */
	class StudentService extends IStudentService.Stub{	// 继承IStudentService.Stub
		@Override
		public Student getStudentById(int id) throws RemoteException {
			Log.e("TAG", "MyRemoteService's getStudentById()" + id);
			// 模拟实现根据客户端传入的id查找并返回某个Student的业务逻辑
			return new Student(id, "Tom", 10000);
		}
	}
	/**
	 * 绑定的回调方法
	 * 由于Stub继承了Binder, Binder实现了IBinder这个顶级接口, 因此可以返回StudentService()的对象
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("TAG", "MyRemoteService's onBind()");
		return new StudentService();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("TAG", "MyRemoteService's onUnbind()");
		return super.onUnbind(intent);
	}
}
