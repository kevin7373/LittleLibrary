package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BookCommentUser {
	private int bookCommentId;
	private String bookCommentContent;
	private String bookCommentWriter;
	private String bookCommentDate;
	private String bookISBN;
	private String userName;
	private String userImg;

	public BookCommentUser() {
		super();
	}

	public BookCommentUser(int bookCommentId, String bookCommentContent, String bookCommentDate) {
		super();
		this.bookCommentId = bookCommentId;
		this.bookCommentContent = bookCommentContent;
		this.bookCommentDate = bookCommentDate;
	}

	public BookCommentUser(String bookCommentContent, String bookCommentWriter, String bookCommentDate, String bookISBN) {
		super();
		this.bookCommentContent = bookCommentContent;
		this.bookCommentWriter = bookCommentWriter;
		this.bookCommentDate = bookCommentDate;
		this.bookISBN = bookISBN;
	}

	public BookCommentUser(String bookCommentContent, String bookCommentWriter, String bookCommentDate, String bookISBN, String userName) {
		super();
		this.bookCommentContent = bookCommentContent;
		this.bookCommentWriter = bookCommentWriter;
		this.bookCommentDate = bookCommentDate;
		this.bookISBN = bookISBN;
		this.userName = userName;
	}

	public BookCommentUser(int bookCommentId, String bookCommentContent, String bookCommentWriter, String bookCommentDate, String bookISBN, String userName, String userImg) {
		super();
		this.bookCommentId = bookCommentId;
		this.bookCommentContent = bookCommentContent;
		this.bookCommentWriter = bookCommentWriter;
		this.bookCommentDate = bookCommentDate;
		this.bookISBN = bookISBN;
		this.userName = userName;
		this.userImg = userImg;
	}

	@Override
	public String toString() {
		return "BookCommentUser [bookCommentId=" + bookCommentId + ", bookCommentContent=" + bookCommentContent + ", bookCommentWriter=" + bookCommentWriter + ", bookCommentDate=" + bookCommentDate + ", bookISBN=" + bookISBN + ", userName=" + userName + ", userImg=" + userImg + "]";
	}

	public int getBookCommentId() {
		return bookCommentId;
	}

	public void setBookCommentId(int bookCommentId) {
		this.bookCommentId = bookCommentId;
	}

	public String getBookCommentContent() {
		return bookCommentContent;
	}

	public void setBookCommentContent(String bookCommentContent) {
		this.bookCommentContent = bookCommentContent;
	}

	public String getBookCommentWriter() {
		return bookCommentWriter;
	}

	public void setBookCommentWriter(String bookCommentWriter) {
		this.bookCommentWriter = bookCommentWriter;
	}

	public String getBookCommentDate() {
		return bookCommentDate;
	}

	public void setBookCommentDate(String bookCommentDate) {
		this.bookCommentDate = bookCommentDate;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

}
