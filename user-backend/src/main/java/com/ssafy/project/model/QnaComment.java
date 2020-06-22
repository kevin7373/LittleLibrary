package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaComment {
	private int qnaCommentId;
	private String qnaCommentContent;
	private String qnaCommentWriter;
	private String qnaCommentDate;
	private int qnaId;
	
	public QnaComment() {
		super();
	}

	public QnaComment(int qnaCommentId, String qnaCommentContent, String qnaCommentWriter, String qnaCommentDate, int qnaId) {
		super();
		this.qnaCommentId = qnaCommentId;
		this.qnaCommentContent = qnaCommentContent;
		this.qnaCommentWriter = qnaCommentWriter;
		this.qnaCommentDate = qnaCommentDate;
		this.qnaId = qnaId;
	}

	@Override
	public String toString() {
		return "QnaComment [qnaCommentId=" + qnaCommentId + ", qnaCommentContent=" + qnaCommentContent + ", qnaCommentWriter=" + qnaCommentWriter + ", qnaCommentDate=" + qnaCommentDate + ", qnaId=" + qnaId + "]";
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

}
