package com.ssafy.project.model;

public class ReqBookDetail {
	private String reqId;
	private String reqCity;
	private String userClass;
	private String consultant;
	private String coach;
	private String pjtWeek;
	private String period;
	private String userId;
	private String userName;
	private String userTeam;
	private String bookName;
	private String bookISBN;
	private String publisher;
	private String bookPrice;
	private String reqReason;
	private int have;
	private String bookImg;
	private int delFlag;

	public ReqBookDetail() {
		super();
	}

	public ReqBookDetail(String reqId, String reqCity, String userClass, String consultant, String coach, String pjtWeek, String period, String userId, String userName, String userTeam, String bookName, String bookISBN, String publisher, String bookPrice, String reqReason, int have, String bookImg, int delFlag) {
		super();
		this.reqId = reqId;
		this.reqCity = reqCity;
		this.userClass = userClass;
		this.consultant = consultant;
		this.coach = coach;
		this.pjtWeek = pjtWeek;
		this.period = period;
		this.userId = userId;
		this.userName = userName;
		this.userTeam = userTeam;
		this.bookName = bookName;
		this.bookISBN = bookISBN;
		this.publisher = publisher;
		this.bookPrice = bookPrice;
		this.reqReason = reqReason;
		this.have = have;
		this.bookImg = bookImg;
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return "ReqBookDetail [reqId=" + reqId + ", reqCity=" + reqCity + ", userClass=" + userClass + ", consultant=" + consultant + ", coach=" + coach + ", pjtWeek=" + pjtWeek + ", period=" + period + ", userId=" + userId + ", userName=" + userName + ", userTeam=" + userTeam + ", bookName=" + bookName + ", bookISBN=" + bookISBN + ", publisher=" + publisher + ", bookPrice=" + bookPrice + ", reqReason=" + reqReason + ", have=" + have + ", bookImg=" + bookImg + ", delFlag=" + delFlag + "]";
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqCity() {
		return reqCity;
	}

	public void setReqCity(String reqCity) {
		this.reqCity = reqCity;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getConsultant() {
		return consultant;
	}

	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getPjtWeek() {
		return pjtWeek;
	}

	public void setPjtWeek(String pjtWeek) {
		this.pjtWeek = pjtWeek;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getUserTeam() {
		return userTeam;
	}

	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getReqReason() {
		return reqReason;
	}

	public void setReqReason(String reqReason) {
		this.reqReason = reqReason;
	}

	public int getHave() {
		return have;
	}

	public void setHave(int have) {
		this.have = have;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
