package com.ssafy.project.model;

public class BookjRent {
	private String bookRentId;
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
	private String state;
	private String rentFrom;
	private String rentTo;
	private int delflag;
	private String studentId;
	private String userId;
	private String userName;
	private String realReturn;

	public BookjRent() {
		super();
	}

	public BookjRent(String bookRentId, String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId, String state, String rentFrom, String rentTo, int delflag, String userId, String realReturn) {
		super();
		this.bookRentId = bookRentId;
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
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.delflag = delflag;
		this.userId = userId;
		this.realReturn = realReturn;
	}

	public BookjRent(String bookRentId, String bookId, String bookISBN, String bookName, int bookPrice, String publisher, String publishDate, String author, String bookImg, int rentCnt, String categoryId, String cityId, String classId, String state, String rentFrom, String rentTo, int delflag, String studentId, String userId, String userName, String realReturn) {
		super();
		this.bookRentId = bookRentId;
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
		this.rentFrom = rentFrom;
		this.rentTo = rentTo;
		this.delflag = delflag;
		this.studentId = studentId;
		this.userId = userId;
		this.userName = userName;
		this.realReturn = realReturn;
	}

	@Override
	public String toString() {
		return "BookjRent [bookRentId=" + bookRentId + ", bookId=" + bookId + ", bookISBN=" + bookISBN + ", bookName=" + bookName + ", bookPrice=" + bookPrice + ", publisher=" + publisher + ", publishDate=" + publishDate + ", author=" + author + ", bookImg=" + bookImg + ", rentCnt=" + rentCnt + ", categoryId=" + categoryId + ", cityId=" + cityId + ", classId=" + classId + ", state=" + state + ", rentFrom=" + rentFrom + ", rentTo=" + rentTo + ", delflag=" + delflag + ", studentId=" + studentId + ", userId=" + userId + ", userName=" + userName + ", realReturn=" + realReturn + "]";
	}

	public String getBookRentId() {
		return bookRentId;
	}

	public void setBookRentId(String bookRentId) {
		this.bookRentId = bookRentId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealReturn() {
		return realReturn;
	}

	public void setRealReturn(String realReturn) {
		this.realReturn = realReturn;
	}

}
