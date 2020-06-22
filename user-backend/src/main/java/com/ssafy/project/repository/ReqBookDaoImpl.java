package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.ReqBook;

@Repository
public class ReqBookDaoImpl implements ReqBookDao {

	String ns = "ssafy.reqbook.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertReqBook(ReqBook reqBook) {
		System.out.println(reqBook.toString());
		return sqlSession.insert(ns + "insertReqBook", reqBook);
	}

	@Override
	public List<ReqBook> getAllReqBook(ReqBook reqBook) {
		return sqlSession.selectList(ns + "getAllReqBook", reqBook);
	}

}
