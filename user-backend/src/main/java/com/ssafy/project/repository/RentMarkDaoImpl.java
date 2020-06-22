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
import com.ssafy.project.model.RentMark;

@Repository
public class RentMarkDaoImpl implements RentMarkDao {
	String ns = "ssafy.rentmark.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertRentMark(RentMark rentMark) {
		 return sqlSession.insert(ns +"insertRentMark", rentMark);
		
	}

	@Override
	public int deleteRentMark(RentMark rentMark) {
		return sqlSession.delete(ns +"deleteRentMark", rentMark);
	}

	@Override
	public List<String> getUserList(RentMark rentMark) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(ns + "getAllUser", rentMark);
	}
	

	

}
