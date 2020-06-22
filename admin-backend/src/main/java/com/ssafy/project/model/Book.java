package com.ssafy.project.model;

public class Book {
	private int num;
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
	private String categoryName;
	private String cityId;
	private String cityName;
	private String classId;
	private String className;
	private String state;
	private int delFlag;
	private String bookSummary;

	public Book() {
		super();
	}

	public Book(String bookId, String bookISBN, String categoryId, String cityId, String classId) {
		super();
		this.bookId = bookId;
		this.bookISBN = bookISBN;
		this.categoryId = categoryId;
		this.cityId = cityId;
		this.classId = classId;
	}

	public Book(int num, String bookId, String bookISBN, String bookName, int bookPrice, String publisher,
			String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId,
			String classId, String state, int delFlag) {
		super();
		this.num = num;
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
		this.state = state;
		this.delFlag = delFlag;
	}

	public Book(int num, String bookId, String bookISBN, String bookName, int bookPrice, String publisher,
			String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId,
			String classId, String state) {
		super();
		this.num = num;
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
		this.state = state;
	}

	public Book(String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate,
			String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId) {
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

	public Book(String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate,
			String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId,
			String state) {
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
		this.state = state;
	}

	public Book(String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate,
			String author, String bookImg, int rentCnt, String categoryId, String categoryName, String cityId,
			String cityName, String classId, String className, String state, String flag) {
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
		this.categoryName = categoryName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.classId = classId;
		this.className = className;
		this.state = state;
	}

	public Book(int num, String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate,
			String author, String bookImg, int rentCnt, String categoryId, String categoryName, String cityId,
			String cityName, String classId, String className, int delFlag, String bookSummary) {
		super();
		this.num = num;
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
		this.categoryName = categoryName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.classId = classId;
		this.className = className;
		this.delFlag = delFlag;
		this.bookSummary = bookSummary;
	}

	@Override
	public String toString() {
		return "Book [num=" + num + ", bookId=" + bookId + ", bookISBN=" + bookISBN + ", bookName=" + bookName
				+ ", bookPrice=" + bookPrice + ", publisher=" + publisher + ", publishDate=" + publishDate + ", author="
				+ author + ", bookImg=" + bookImg + ", rentCnt=" + rentCnt + ", categoryId=" + categoryId
				+ ", categoryName=" + categoryName + ", cityId=" + cityId + ", cityName=" + cityName + ", classId="
				+ classId + ", className=" + className + ", state=" + state + ", delFlag=" + delFlag + ", bookSummary="
				+ bookSummary + "]";
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getBookSummary() {
		return bookSummary;
	}

	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}

}
