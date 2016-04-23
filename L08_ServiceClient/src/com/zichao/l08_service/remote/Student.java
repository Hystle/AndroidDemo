package com.zichao.l08_service.remote;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Student implements Parcelable{
	private int id;
	private String name;
	private double price;
	
	/*
	 * 将当前对象的属性数据打包,即写到包对象中
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.e("TAG", "打包");
		dest.writeInt(id);			// id
		dest.writeString(name);		// name
		dest.writeDouble(price);	// price 
	}
	@Override
	public int describeContents() {
		return 0;
	}
	
	/*
	 * 解包方法: 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
	 */
	public static final Parcelable.Creator<Student> CREATOR = new Creator<Student>() {
		// 读取包中的数据并重新组装为对象
		@Override
		public Student createFromParcel(Parcel source) {
			// 必须有序读取:与打包时的顺序相同,即与内存中存储的顺序相同
			int id = source.readInt();
			String name = source.readString();
			Double price = source.readDouble();
			Log.e("TAG", "解包");
			return new Student(id, name, price);
		}
		
		// 返回指定大小的对象容器
		@Override
		public Student[] newArray(int size) {
			return new Student[size];
		}
	};

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Student(int id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	public Student() {
		super();
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
}
