package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Qna;

@Repository
public class QnaDaoImpl implements QnaDao {

	String ns = "ssafy.qna.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertQna(Qna qna) {
		return sqlSession.insert(ns + "insertQna", qna);
	}

	@Override
	public Qna getQna(Qna qna) {
		return sqlSession.selectOne(ns + "getQna", qna);
	}

	@Override
	public List<Qna> getAllQnaList() {
		return sqlSession.selectList(ns + "getAllQnaList");
	}

	@Override
	public int deleteQna(Qna qna) {
		return sqlSession.delete(ns + "deleteQna", qna);
	}

	@Override
	public int updateQna(Qna qna) {
		return sqlSession.update(ns + "updateQna", qna);
	}

	@Override
	public int cntUpQnaView(Qna qna) {
		return sqlSession.update(ns + "cntUpQnaView", qna);
	}

	@Override
	public Qna getQnaByQnaId(int qnaId) {
		return sqlSession.selectOne(ns + "getQnaByQnaId", qnaId);
	}

}
