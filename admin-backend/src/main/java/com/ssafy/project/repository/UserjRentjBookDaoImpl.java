package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.UserjRentjBook;

@Repository
public class UserjRentjBookDaoImpl implements UserjRentjBookDao {

	String ns = "ssafy.userjRentjBook.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<UserjRentjBook> getRentUserList() {
		return sqlSession.selectList(ns + "getRentUserList");
	}

	@Override
	public UserjRentjBook getBookDetail(String bookId) {
		return sqlSession.selectOne(ns + "getBookDetail", bookId);
	}

	@Override
	public UserjRentjBook getRentUserDetail(String bookRentId) {
		return sqlSession.selectOne(ns + "getRentUserDetail", bookRentId);
	}

}
