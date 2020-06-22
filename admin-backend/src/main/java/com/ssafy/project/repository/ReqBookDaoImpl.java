package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.ReqBookDetail;

@Repository
public class ReqBookDaoImpl implements ReqBookDao {
	String ns = "ssafy.reqBook.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<ReqBook> getReqBookMain(String reqCity, String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("reqCity", reqCity);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getReqBookMain", map);
	}

	@Override
	public List<ReqBook> getAllReqBookList(String reqCity, String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("reqCity", reqCity);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns+"getAllReqBookList", map);
	}

	@Override
	public void insertDelFlag(String reqId) {
		sqlSession.update(ns+"insertDelFlag", reqId);
		
	}
	@Override
	public ReqBookDetail getReqBookDetailByReqId(String reqId) {
		return sqlSession.selectOne(ns + "getReqBookDetailByReqId", reqId);
	}

}