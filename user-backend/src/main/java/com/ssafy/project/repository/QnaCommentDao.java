package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.QnaComment;

public interface QnaCommentDao {

	public int insertQnaComment(QnaComment qnaComment);

	public List<QnaComment> getAllQnaComment(QnaComment qnaComment);

	public void updateQnaComment(QnaComment qnaComment);

	public void deleteQnaComment(int qnaCommentId);

}
