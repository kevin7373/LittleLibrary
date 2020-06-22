package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qna {
	private int qnaId;
	private String qnaTitle;
	private String qnaContent;
	private String qnaWriteDate;
	private int qnaViewCnt;
	private String userId;

	public Qna() {
		super();
	}

	public Qna(int qnaId, String qnaTitle, String qnaContent, String qnaWriteDate, int qnaViewCnt, String userId) {
		super();
		this.qnaId = qnaId;
		this.qnaTitle = qnaTitle;
		this.qnaContent = qnaContent;
		this.qnaWriteDate = qnaWriteDate;
		this.qnaViewCnt = qnaViewCnt;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Qna [qnaId=" + qnaId + ", qnaTitle=" + qnaTitle + ", qnaContent=" + qnaContent + ", qnaWriteDate=" + qnaWriteDate + ", qnaViewCnt=" + qnaViewCnt + ", userId=" + userId + "]";
	}

	public int getQnaId() {
		return qnaId;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public String getQnaContent() {
		return qnaContent;
	}

	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}

	public String getQnaWriteDate() {
		return qnaWriteDate;
	}

	public void setQnaWriteDate(String qnaWriteDate) {
		this.qnaWriteDate = qnaWriteDate;
	}

	public int getQnaViewCnt() {
		return qnaViewCnt;
	}

	public void setQnaViewCnt(int qnaViewCnt) {
		this.qnaViewCnt = qnaViewCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
