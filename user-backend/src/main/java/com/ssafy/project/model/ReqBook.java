package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqBook {
	private int reqId;
	private String reqCity;
	private String userClass;
	private String consultant;
	private String coach;
	private String pjtWeek;
	private String period;
	private String userName;
	private String userTeam;
	private String bookName;
	private String bookISBN;
	private String publisher;
	private String bookPrice;
	private String reqReason;
	private int have;
	private String bookImg;
	private boolean delFlag;
	private String userId;

	public ReqBook() {
		super();
	}

	public ReqBook(String userTeam, String bookName, String reqReason) {
		super();
		this.reqId = -1;
		this.reqCity = null;
		this.userClass = null;
		this.consultant = null;
		this.coach = null;
		this.pjtWeek = null;
		this.period = null;
		this.userName = null;
		this.userTeam = userTeam;
		this.bookName = bookName;
		this.bookISBN = null;
		this.publisher = null;
		this.bookPrice = null;
		this.reqReason = reqReason;
		this.have = 0;
		this.bookImg = null;
		this.delFlag = false;
	}

	public ReqBook(int reqId, String reqCity, String userClass, String consultant, String coach, String pjtWeek, String period, String userName, String userTeam, String bookName, String bookISBN, String publisher, String bookPrice, String reqReason, int have, String bookImg, boolean delFlag, String userId) {
		super();
		this.reqId = reqId;
		this.reqCity = reqCity;
		this.userClass = userClass;
		this.consultant = consultant;
		this.coach = coach;
		this.pjtWeek = pjtWeek;
		this.period = period;
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
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
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

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	@Override
	public String toString() {
		return "ReqBook [reqId=" + reqId + ", reqCity=" + reqCity + ", userClass=" + userClass + ", consultant="
				+ consultant + ", coach=" + coach + ", pjtWeek=" + pjtWeek + ", period=" + period + ", userName="
				+ userName + ", userTeam=" + userTeam + ", bookName=" + bookName + ", bookISBN=" + bookISBN
				+ ", publisher=" + publisher + ", bookPrice=" + bookPrice + ", reqReason=" + reqReason + ", have="
				+ have + ", bookImg=" + bookImg + ", delFlag=" + delFlag + ", userId=" + userId + "]";
	}

	

}
