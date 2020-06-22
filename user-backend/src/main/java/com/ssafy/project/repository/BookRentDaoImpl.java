package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.BookRent;

@Repository
public class BookRentDaoImpl implements BookRentDao {

	String ns = "ssafy.bookrent.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertBookRent(BookRent bookRent) {
		int res = sqlSession.insert(ns + "insertBookRent", bookRent);
		System.out.println(res);
		return res;
	}

	@Override
	public int cntRentedBook(BookRent bookRent) {
		int res = sqlSession.selectOne(ns + "cntRentedBook", bookRent);
		return res;
	}

	@Override
	public int deleteBookRent(BookRent bookRent) {
		System.out.println("Dao : " + bookRent);
		return sqlSession.update(ns + "deleteBookRent", bookRent);
	}

	@Override
	public List<BookRent> getBookRentByUser(BookRent bookRent) {
		return sqlSession.selectList(ns + "getBookRentByUser", bookRent);
	}
	
	@Override
	public List<BookRent> getBookRentByUserAll(BookRent bookRent) {
		return sqlSession.selectList(ns + "getBookRentByUserAll", bookRent);
	}

	@Override
	public int cntBookRentByUser(BookRent bookRent) {
		int res = sqlSession.selectOne(ns + "cntBookRentByUser", bookRent);
		return res;
	}

	@Override
	public BookRent getBookIdByUserId(String userId) {
		return sqlSession.selectOne(ns + "getBookIdByUserId", userId);
	}

}
