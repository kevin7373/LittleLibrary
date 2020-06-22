package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Notice;

@Repository
public class NoticeDaoImpl implements NoticeDao {

	String ns = "ssafy.notice.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertNotice(Notice notice) {
		return sqlSession.insert(ns + "insertNotice", notice);
	}

	@Override
	public Notice getNotice(Notice notice) {
		return sqlSession.selectOne(ns + "getNotice", notice);
	}

	@Override
	public List<Notice> getAllNoticeList() {
		return sqlSession.selectList(ns + "getAllNoticeList");
	}

	@Override
	public int deleteNotice(Notice notice) {
		return sqlSession.delete(ns + "deleteNotice", notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return sqlSession.update(ns + "updateNotice", notice);
	}

	@Override
	public int cntUpNoticeView(Notice notice) {
		return sqlSession.update(ns + "cntUpNoticeView", notice);
	}

	@Override
	public Notice getNoticeByNoticeId(int noticeId) {
		return sqlSession.selectOne(ns + "getNoticeByNoticeId", noticeId);
	}

}
