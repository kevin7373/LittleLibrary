package com.ssafy.project.model;

public class BookComment {
	private int bookCommentId;
	private String bookCommentContent;
	private String bookCommentWriter;
	private String bookCommentDate;
	private String bookISBN;

	public BookComment() {
		super();
	}

	public BookComment(int bookCommentId, String bookCommentContent, String bookCommentDate) {
		super();
		this.bookCommentId = bookCommentId;
		this.bookCommentContent = bookCommentContent;
		this.bookCommentDate = bookCommentDate;
	}

	public BookComment(String bookCommentContent, String bookCommentWriter, String bookCommentDate, String bookISBN) {
		super();
		this.bookCommentContent = bookCommentContent;
		this.bookCommentWriter = bookCommentWriter;
		this.bookCommentDate = bookCommentDate;
		this.bookISBN = bookISBN;
	}

	public BookComment(int bookCommentId, String bookCommentContent, String bookCommentWriter, String bookCommentDate, String bookISBN) {
		super();
		this.bookCommentId = bookCommentId;
		this.bookCommentContent = bookCommentContent;
		this.bookCommentWriter = bookCommentWriter;
		this.bookCommentDate = bookCommentDate;
		this.bookISBN = bookISBN;
	}

	@Override
	public String toString() {
		return "BookComment [bookCommentId=" + bookCommentId + ", bookCommentContent=" + bookCommentContent + ", bookCommentWriter=" + bookCommentWriter + ", bookCommentDate=" + bookCommentDate + ", bookISBN=" + bookISBN + "]";
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

}
