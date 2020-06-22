package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.BookComment;

@Repository
public class BookCommentDaoImpl implements BookCommentDao {
	String ns = "ssafy.bookComment.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<BookComment> getBookComment(String bookISBN) {
		return sqlSession.selectList(ns + "getBookComment", bookISBN);
	}

	@Override
	public void insertBookComment(BookComment bc) throws Exception {
		sqlSession.insert(ns + "insertBookComment", bc);
	}

	@Override
	public void updateBookComment(BookComment bc) throws Exception {
		sqlSession.update(ns + "updateBookComment", bc);
	}

	@Override
	public void deleteBookComment(String bookCommentId) throws Exception {
		sqlSession.delete(ns + "deleteBookComment", bookCommentId);
	}

}
