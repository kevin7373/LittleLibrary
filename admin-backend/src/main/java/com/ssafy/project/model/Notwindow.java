package com.ssafy.project.model;

public class Notwindow {
	private int notwindowId;
	private String userId;
	private String content;
	private String writer;
	private String title;
	private String writeDate;
	private String readFlag;
	private String writerName;
	private String writerImg;
	private String writerCity;
	private String writerClass;

	public Notwindow() {
		super();
	}

	public Notwindow(int notwindowId, String userId, String content, String writer, String title, String writeDate) {
		super();
		this.notwindowId = notwindowId;
		this.userId = userId;
		this.content = content;
		this.writer = writer;
		this.title = title;
		this.writeDate = writeDate;
	}

	public Notwindow(String writer, String content, String title) {
		super();
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	public Notwindow(int notwindowId, String userId, String content, String writer, String title, String writeDate, String readFlag) {
		super();
		this.notwindowId = notwindowId;
		this.userId = userId;
		this.content = content;
		this.writer = writer;
		this.title = title;
		this.writeDate = writeDate;
		this.readFlag = readFlag;
	}

	public Notwindow(int notwindowId, String userId, String content, String writer, String title, String writeDate, String readFlag, String writerName, String writerImg, String writerCity, String writerClass) {
		super();
		this.notwindowId = notwindowId;
		this.userId = userId;
		this.content = content;
		this.writer = writer;
		this.title = title;
		this.writeDate = writeDate;
		this.readFlag = readFlag;
		this.writerName = writerName;
		this.writerImg = writerImg;
		this.writerCity = writerCity;
		this.writerClass = writerClass;
	}

	@Override
	public String toString() {
		return "Notwindow [notwindowId=" + notwindowId + ", userId=" + userId + ", content=" + content + ", writer=" + writer + ", title=" + title + ", writeDate=" + writeDate + ", readFlag=" + readFlag + ", writerName=" + writerName + ", writerImg=" + writerImg + ", writerCity=" + writerCity + ", writerClass=" + writerClass + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public int getNotwindowId() {
		return notwindowId;
	}

	public void setNotwindowId(int notwindowId) {
		this.notwindowId = notwindowId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getWriterImg() {
		return writerImg;
	}

	public void setWriterImg(String writerImg) {
		this.writerImg = writerImg;
	}

	public String getWriterCity() {
		return writerCity;
	}

	public void setWriterCity(String writerCity) {
		this.writerCity = writerCity;
	}

	public String getWriterClass() {
		return writerClass;
	}

	public void setWriterClass(String writerClass) {
		this.writerClass = writerClass;
	}

}