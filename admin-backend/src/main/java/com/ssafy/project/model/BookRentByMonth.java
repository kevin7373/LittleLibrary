package com.ssafy.project.model;

public class BookRentByMonth {
	private int cnt;
	private String Month;

	public BookRentByMonth() {
		super();
	}

	public BookRentByMonth(int cnt, String month) {
		super();
		this.cnt = cnt;
		Month = month;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

}
