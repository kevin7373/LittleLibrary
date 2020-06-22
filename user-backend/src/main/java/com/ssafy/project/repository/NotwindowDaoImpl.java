package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Notwindow;

@Repository
public class NotwindowDaoImpl implements NotwindowDao{
	
	String ns = "ssafy.notwindow.";
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertNotwindow(Notwindow notwindow) {
		return sqlSession.insert(ns+"insertNotwindow", notwindow);
	}

	@Override
	public List<Notwindow> getNotwindowList(Notwindow notwindow) {
		return sqlSession.selectList(ns+"getNotwindowList", notwindow);
	}

	@Override
	public int deleteNotwindow(Notwindow notwindow) {
		return sqlSession.delete(ns+"deleteNotwindow", notwindow);
	}



}
