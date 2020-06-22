package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.QnaComment;

public interface QnaCommentService {
	public int insertQnaComment(QnaComment qnaComment);

	public List<QnaComment> gwtAllQnaComment(QnaComment qnaComment);

	public void updateQnaComment(QnaComment qnaComment);

	public void deleteQnaComment(int qnaCommentId);
}
