package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BookRent {
	private String bookRentId;
	private String rentFrom;
	private String rentTo;
	private String realReturn;
	private int delFlag;
	private String userId;
	private String bookId;

	public BookRent() {
		super();
	}

	public BookRent(String bookRentId, String rentFrom, String rentTo, int delFlag, String userId, String bookId) {
		super();
		this.bookRentId = bookRentId;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.delFlag = delFlag;
		this.userId = userId;
		this.bookId = bookId;
	}

	public BookRent(String realReturn, int delFlag, String userId, String bookId) {
		super();
		this.realReturn = realReturn;
		this.delFlag = delFlag;
		this.userId = userId;
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "BookRent [bookRentId=" + bookRentId + ", rentFrom=" + rentFrom + ", rentTo=" + rentTo + ", realReturn=" + realReturn + ", delFlag=" + delFlag + ", userId=" + userId + ", bookId=" + bookId + "]";
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

	public String getRealReturn() {
		return realReturn;
	}

	public void setRealReturn(String realReturn) {
		this.realReturn = realReturn;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
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
}
