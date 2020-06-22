package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.BookComment;
import com.ssafy.project.model.BookCommentUser;

@Repository
public class BookCommentDaoImpl implements BookCommentDao {
	String ns = "ssafy.bookComment.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<BookCommentUser> getBookComment(@Param("bookISBN") String bookISBN, @Param("userId") String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("bookISBN", bookISBN);
		map.put("userId", userId);
		return sqlSession.selectList(ns + "getBookComment", map);
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

	@Override
	public String getCmntUser(String bookCommentId) {
		return sqlSession.selectOne(ns + "getCmntUser", bookCommentId);
	}

}
