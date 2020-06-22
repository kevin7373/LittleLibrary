package com.ssafy.project.model;

public class QnaComment {
	private int qnaCommentId;
	private String qnaCommentContent;
	private String qnaCommentWriter;
	private String qnaCommentDate;
	private int qnaId;
	private String userName;
	private String cityName;
	private String className;
	private String userImg;

	public QnaComment() {
		super();
	}

	public QnaComment(int qnaCommentId, String qnaCommentContent, String qnaCommentWriter, String qnaCommentDate,
			int qnaId) {
		super();
		this.qnaCommentId = qnaCommentId;
		this.qnaCommentContent = qnaCommentContent;
		this.qnaCommentWriter = qnaCommentWriter;
		this.qnaCommentDate = qnaCommentDate;
		this.qnaId = qnaId;
	}

//	public QnaComment(String qnaCommentContent, String qnaCommentWriter, String qnaCommentDate, int qnaId) {
//		super();
//		this.qnaCommentContent = qnaCommentContent;
//		this.qnaCommentWriter = qnaCommentWriter;
//		this.qnaCommentDate = qnaCommentDate;
//		this.qnaId = qnaId;
//	}
//
//	public QnaComment(int qnaCommentId, String qnaCommentContent, String qnaCommentDate) {
//		super();
//		this.qnaCommentId = qnaCommentId;
//		this.qnaCommentContent = qnaCommentContent;
//		this.qnaCommentDate = qnaCommentDate;
//	}

	public QnaComment(int qnaCommentId, String qnaCommentContent, String qnaCommentWriter, String qnaCommentDate,
			int qnaId, String userName, String cityName, String className, String userImg) {
		super();
		this.qnaCommentId = qnaCommentId;
		this.qnaCommentContent = qnaCommentContent;
		this.qnaCommentWriter = qnaCommentWriter;
		this.qnaCommentDate = qnaCommentDate;
		this.qnaId = qnaId;
		this.userName = userName;
		this.cityName = cityName;
		this.className = className;
		this.userImg = userImg;
	}

	@Override
	public String toString() {
		return "QnaComment [qnaCommentId=" + qnaCommentId + ", qnaCommentContent=" + qnaCommentContent
				+ ", qnaCommentWriter=" + qnaCommentWriter + ", qnaCommentDate=" + qnaCommentDate + ", qnaId=" + qnaId
				+ ", userName=" + userName + ", cityName=" + cityName + ", className=" + className + ", userImg="
				+ userImg + "]";
	}

	public int getQnaCommentId() {
		return qnaCommentId;
	}

	public void setQnaCommentId(int qnaCommentId) {
		this.qnaCommentId = qnaCommentId;
	}

	public String getQnaCommentContent() {
		return qnaCommentContent;
	}

	public void setQnaCommentContent(String qnaCommentContent) {
		this.qnaCommentContent = qnaCommentContent;
	}

	public String getQnaCommentWriter() {
		return qnaCommentWriter;
	}

	public void setQnaCommentWriter(String qnaCommentWriter) {
		this.qnaCommentWriter = qnaCommentWriter;
	}

	public String getQnaCommentDate() {
		return qnaCommentDate;
	}

	public void setQnaCommentDate(String qnaCommentDate) {
		this.qnaCommentDate = qnaCommentDate;
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

}
