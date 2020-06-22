package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Faq;

@Repository
public class FaqDaoImpl implements FaqDao {

	String ns = "ssafy.faq.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertFaq(Faq faq) {
		System.out.println(faq.toString());
		return sqlSession.insert(ns + "insertFaq", faq);
	}

	@Override
	public Faq getFaq(Faq faq) {
		return sqlSession.selectOne(ns + "getFaq", faq);
	}

	@Override
	public List<Faq> getAllFaqList() {
		return sqlSession.selectList(ns + "getAllFaqList");
	}

	@Override
	public int deleteFaq(Faq faq) {
		return sqlSession.delete(ns + "deleteFaq", faq);
	}

	@Override
	public int updateFaq(Faq faq) {
		return sqlSession.update(ns + "updateFaq", faq);
	}

	@Override
	public int cntUpFaqView(Faq faq) {
		return sqlSession.update(ns + "cntUpFaqView", faq);
	}

}
