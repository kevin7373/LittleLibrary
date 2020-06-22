package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Qna;
import com.ssafy.project.repository.QnaDao;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	QnaDao qnaDao;

	@Override
	public int intsertQna(Qna qna) {
		return qnaDao.insertQna(qna);
	}

	@Override
	public Qna getQna(Qna qna) {
		return qnaDao.getQna(qna);
	}

	@Override
	public List<Qna> getAllQnaList() {
		return qnaDao.getAllQnaList();
	}

	@Override
	public int deleteQna(Qna qna) {
		return qnaDao.deleteQna(qna);
	}

	@Override
	public int updateQna(Qna qna) {
		return qnaDao.updateQna(qna);
	}

	@Override
	public int cntUpQnaView(Qna qna) {
		return qnaDao.cntUpQnaView(qna);
	}

	@Override
	public Qna getQnaByQnaId(int qnaId) {
		return qnaDao.getQnaByQnaId(qnaId);
	}

}
