package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.BookRent;
import com.ssafy.project.model.RentCnt;

@Repository
public class BookRentDaoImpl implements BookRentDao {
	String ns = "ssafy.bookRent.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int getRentCnt(String tday) {
		return sqlSession.selectOne(ns + "getRentCnt", tday);
	}

	@Override
	public int getExpireBookCnt(String tday) {
		return sqlSession.selectOne(ns + "getExpireBookCnt", tday);
	}

	@Override
	public int getRealReturnCnt() {
		return sqlSession.selectOne(ns + "getRealReturnCnt");
	}

	@Override
	public int getTodayReturnCnt() {
		return sqlSession.selectOne(ns + "getTodayReturnCnt");
	}

	@Override
	public int getRentBookCntByBookId(RentCnt rentCnt) {
		return sqlSession.selectOne(ns + "getRentBookCntByBookId", rentCnt);
	}

	@Override
	public int getExpireBookCntByBookId(RentCnt rentCnt) {
		return sqlSession.selectOne(ns + "getExpireBookCntByBookId", rentCnt);
	}

	@Override
	public List<BookRent> getAllRentBookList() {
		return sqlSession.selectList(ns + "getAllRentBookList");
	}

	@Override
	public List<BookRent> getAllReturnBookList() {
		return sqlSession.selectList(ns + "getAllReturnBookList");
	}

	@Override
	public List<BookRent> getAllExpiredBookList() {
		return sqlSession.selectList(ns + "getAllExpiredBookList");
	}

	@Override
	public String getUserIdByBookId(String bookId) {
		return sqlSession.selectOne(ns + "getUserIdByBookId", bookId);
	}

	@Override
	public int isRentedByBookId(String bookId) {
		return sqlSession.selectOne(ns + "isRentedByBookId", bookId);
	}

	@Override
	public void deleteBookRentHistory(String bookId) {
		sqlSession.delete(ns + "deleteBookRentHistory", bookId);
	}

	@Override
	public String getRemainDay(@Param("brId") String brId, @Param("td") String td) {
		Map<String, String> map = new HashMap<>();
		map.put("brId", brId);
		map.put("td", td);
		return sqlSession.selectOne(ns + "getRemainDay", map);
	}

	@Override
	public String getBookRentId(String bookId) {
		return sqlSession.selectOne(ns + "getBookRentId", bookId);
	}

	@Override
	public String getExpiredDay(@Param("brId") String brId, @Param("td") String td) {
		Map<String, String> map = new HashMap<>();
		map.put("brId", brId);
		map.put("td", td);
		return sqlSession.selectOne(ns + "getExpiredDay", map);
	}

	@Override
	public int isNowRent(String bookId) {
		return sqlSession.selectOne(ns + "isNowRent", bookId);
	}

	@Override
	public int isNowExpire(String bookId) {
		return sqlSession.selectOne(ns + "isNowExpire", bookId);
	}

}