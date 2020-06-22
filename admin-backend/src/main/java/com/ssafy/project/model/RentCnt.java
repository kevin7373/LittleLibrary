package com.ssafy.project.model;

public class RentCnt {
	private String bookId;
	private String today;

	public RentCnt() {
		super();
	}

	public RentCnt(String bookId, String today) {
		super();
		this.bookId = bookId;
		this.today = today;
	}

	@Override
	public String toString() {
		return "RentCnt [bookId=" + bookId + ", today=" + today + "]";
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}
}
