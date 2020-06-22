package com.ssafy.project.model;

public class ReqBookMain {
	private int cnt;
	private String bookName;
	private String bookISBN;
	private String bookImg;
	private int have;

	public ReqBookMain() {
		super();
	}

	@Override
	public String toString() {
		return "ReqBookMain [cnt=" + cnt + ", bookName=" + bookName + ", bookISBN=" + bookISBN + ", bookImg=" + bookImg
				+ "]";
	}

	public ReqBookMain(int cnt, String bookName, String bookISBN, String bookImg, int have) {
		super();
		this.cnt = cnt;
		this.bookName = bookName;
		this.bookISBN = bookISBN;
		this.bookImg = bookImg;
		this.have = have;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public int getHave() {
		return have;
	}

	public void setHave(int have) {
		this.have = have;
	}

}
