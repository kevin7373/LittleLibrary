package com.ssafy.project.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	String ns = "ssafy.category.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public Category getCategoryNameByUserId(String userId) {
		return sqlSession.selectOne(ns+"getCategoryNameByUserId", userId);
	}

	@Override
	public String getCategoryIdByCategoryName(String categoryName) {
		return sqlSession.selectOne(ns + "getCategoryIdByCategoryName", categoryName);
	}

}