package com.ssafy.project.model;

public class BookRent {
	private String bookRentId;
	private String rentFrom;
	private String rentTo;
	private int delflag;
	private String userId;
	private String bookId;
	private String realReturn;

	public BookRent() {
		super();
	}

	public BookRent(String bookRentId, String rentFrom, String rentTo, int delflag, String userId, String bookId,
			String realReturn) {
		super();
		this.bookRentId = bookRentId;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.delflag = delflag;
		this.userId = userId;
		this.bookId = bookId;
		this.realReturn = realReturn;
	}

	@Override
	public String toString() {
		return "BookRent [bookRentId=" + bookRentId + ", rentFrom=" + rentFrom + ", rentTo=" + rentTo + ", delflag="
				+ delflag + ", userId=" + userId + ", bookId=" + bookId + ", realReturn=" + realReturn + "]";
	}

	public String getBookRentId() {
		return bookRentId;
	}

	public void setBookRentId(String bookRentId) {
		this.bookRentId = bookRentId;
	}

	public String getRentFrom() {
		return rentFrom;
	}

	public void setRentFrom(String rentFrom) {
		this.rentFrom = rentFrom;
	}

	public String getRentTo() {
		return rentTo;
	}

	public void setRentTo(String rentTo) {
		this.rentTo = rentTo;
	}

	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getRealReturn() {
		return realReturn;
	}

	public void setRealReturn(String realReturn) {
		this.realReturn = realReturn;
	}

}
