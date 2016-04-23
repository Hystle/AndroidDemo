package com.zichao.l04_app04_blacklist.test;

import java.util.List;

import com.zichao.l04_app04_blacklist.BlackListDAO;
import com.zichao.l04_app04_blacklist.BlackListItem;

import android.test.AndroidTestCase;
import android.util.Log;
/**
 * BlackListDAO的单元测试类
 * @author Zach
 */
public class BlackListDAOTest extends AndroidTestCase {
	
	public void testAdd(){
		BlackListDAO dao = new BlackListDAO(getContext());
		dao.add(new BlackListItem(-1, "789"));
	}
	
	public void testQuery(){
		BlackListDAO dao = new BlackListDAO(getContext());
		List<BlackListItem> list = dao.getAll();
		Log.i("TAG", list.toString());
	}
	public void testUpdate(){
		BlackListDAO dao = new BlackListDAO(getContext());
		dao.update(new BlackListItem(1, "321"));
	}
	public void testDelete(){
		BlackListDAO dao = new BlackListDAO(getContext());
		dao.deleteById(1);
	}
	
}
