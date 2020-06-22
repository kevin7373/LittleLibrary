package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	private int noticeId;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriteDate;
	private int noticeViewCnt;
	private String userId;

	public Notice() {
		super();
	}

	public Notice(int noticeId, String noticeTitle, String noticeContent, String noticeWriteDate, int noticeViewCnt, String userId) {
		super();
		this.noticeId = noticeId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWriteDate = noticeWriteDate;
		this.noticeViewCnt = noticeViewCnt;
		this.userId = userId;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeWriteDate() {
		return noticeWriteDate;
	}

	public void setNoticeWriteDate(String noticeWriteDate) {
		this.noticeWriteDate = noticeWriteDate;
	}

	public int getNoticeViewCnt() {
		return noticeViewCnt;
	}

	public void setNoticeViewCnt(int noticeViewCnt) {
		this.noticeViewCnt = noticeViewCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent + ", noticeWriteDate=" + noticeWriteDate + ", noticeViewCnt=" + noticeViewCnt + ", userId=" + userId + "]";
	}

}
