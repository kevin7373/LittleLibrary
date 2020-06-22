package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notwindow {
	int notwindowId;
	String userId;
	String content;
	String writer;
	String title;
	String writeDate;

	public Notwindow(int notwindowId, String userId, String content, String writer, String title, String writeDate) {
		super();
		this.notwindowId = notwindowId;
		this.userId = userId;
		this.content = content;
		this.writer = writer;
		this.title = title;
		this.writeDate = writeDate;
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

}
