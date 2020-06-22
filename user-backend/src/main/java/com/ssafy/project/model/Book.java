package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Book {
	private String bookId;
	private String bookISBN;
	private String bookName;
	private int bookPrice;
	private String publisher;
	private String publishDate;
	private String author;
	private String bookImg;
	private int rentCnt;
	private String categoryId;
	private String cityId;
	private String classId;
	private String bookSummary;

	public Book() {
		super();
	}

	public Book(String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId) {
		super();
		this.bookId = bookId;
		this.bookISBN = bookISBN;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.author = author;
		this.bookImg = bookImg;
		this.rentCnt = rentCnt;
		this.categoryId = categoryId;
		this.cityId = cityId;
		this.classId = classId;
	}

	public Book(String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId, String bookSummary) {
		super();
		this.bookId = bookId;
		this.bookISBN = bookISBN;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.author = author;
		this.bookImg = bookImg;
		this.rentCnt = rentCnt;
		this.categoryId = categoryId;
		this.cityId = cityId;
		this.classId = classId;
		this.bookSummary = bookSummary;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookISBN=" + bookISBN + ", bookName=" + bookName + ", bookPrice=" + bookPrice + ", publisher=" + publisher + ", publishDate=" + publishDate + ", author=" + author + ", bookImg=" + bookImg + ", rentCnt=" + rentCnt + ", categoryId=" + categoryId + ", cityId=" + cityId + ", classId=" + classId + ", bookSummary=" + bookSummary + "]";
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public int getRentCnt() {
		return rentCnt;
	}

	public void setRentCnt(int rentCnt) {
		this.rentCnt = rentCnt;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getBookSummary() {
		return bookSummary;
	}

	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}
}
