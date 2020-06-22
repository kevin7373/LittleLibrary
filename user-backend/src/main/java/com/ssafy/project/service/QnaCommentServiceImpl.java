package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.QnaComment;
import com.ssafy.project.repository.QnaCommentDao;

@Service
public class QnaCommentServiceImpl implements QnaCommentService {

	@Autowired
	QnaCommentDao qnaCommentDao;

	@Override
	public int insertQnaComment(QnaComment qnaComment) {
		// TODO Auto-generated method stub
		return qnaCommentDao.insertQnaComment(qnaComment);
	}

	@Override
	public List<QnaComment> gwtAllQnaComment(QnaComment qnaComment) {
		// TODO Auto-generated method stub
		return qnaCommentDao.getAllQnaComment(qnaComment);
	}

	@Override
	public void updateQnaComment(QnaComment qnaComment) {
		qnaCommentDao.updateQnaComment(qnaComment);
	}

	@Override
	public void deleteQnaComment(int qnaCommentId) {
		qnaCommentDao.deleteQnaComment(qnaCommentId);
	}

}
