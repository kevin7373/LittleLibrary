package com.ssafy.project.model;

public class BookDetail {
	private String bookImg;
	private String bookISBN;
	private String bookId;
	private String bookName;
	private String rentFrom;
	private String rentTo;
	private String realReturn;
	private String publisher;
	private String author;
	private String publishDate;
	private int bookPrice;
	private String categoryName;
	private int rentCnt;
	private String cityName;
	private String className;
	private String state;

	public BookDetail() {
		super();
	}

	public BookDetail(String bookImg, String bookISBN, String bookId, String bookName, String rentFrom, String rentTo, String realReturn, String publisher, String author, String publishDate, int bookPrice, String categoryName, int rentCnt, String cityName, String className, String state) {
		super();
		this.bookImg = bookImg;
		this.bookISBN = bookISBN;
		this.bookId = bookId;
		this.bookName = bookName;
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.realReturn = realReturn;
		this.publisher = publisher;
		this.author = author;
		this.publishDate = publishDate;
		this.bookPrice = bookPrice;
		this.categoryName = categoryName;
		this.rentCnt = rentCnt;
		this.cityName = cityName;
		this.className = className;
		this.state = state;
	}

	@Override
	public String toString() {
		return "BookDetail [bookImg=" + bookImg + ", bookISBN=" + bookISBN + ", bookId=" + bookId + ", bookName=" + bookName + ", rentFrom=" + rentFrom + ", rentTo=" + rentTo + ", realReturn=" + realReturn + ", publisher=" + publisher + ", author=" + author + ", publishDate=" + publishDate + ", bookPrice=" + bookPrice + ", categoryName=" + categoryName + ", rentCnt=" + rentCnt + ", cityName=" + cityName + ", className=" + className + ", state=" + state + "]";
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getRentCnt() {
		return rentCnt;
	}

	public void setRentCnt(int rentCnt) {
		this.rentCnt = rentCnt;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
