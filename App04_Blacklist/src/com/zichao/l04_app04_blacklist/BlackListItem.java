package com.zichao.l04_app04_blacklist;

public class BlackListItem {
	private int id;
	private String number;
	public BlackListItem() {
		super();
	}
	@Override
	public String toString() {
		return "BlackListItem [id=" + id + ", number=" + number + "]";
	}
	public BlackListItem(int id, String number) {
		super();
		this.id = id;
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
