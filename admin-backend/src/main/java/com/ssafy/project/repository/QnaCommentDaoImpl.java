package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.QnaComment;

@Repository
public class QnaCommentDaoImpl implements QnaCommentDao {

	String ns = "ssafy.qnaComment.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertQnaComment(QnaComment qnaComment) {
		// TODO Auto-generated method stub
		return sqlSession.insert(ns + "insertQnaComment", qnaComment);
	}

	@Override
	public List<QnaComment> getAllQnaComment(QnaComment qnaComment) {
		return sqlSession.selectList(ns + "getAllQnaComment", qnaComment);
	}

	@Override
	public void updateQnaComment(QnaComment qnaComment) {
		sqlSession.update(ns + "updateQnaComment", qnaComment);
	}

	@Override
	public void deleteQnaComment(int qnaCommentId) {
		sqlSession.delete(ns + "deleteQnaComment", qnaCommentId);
	}

}
