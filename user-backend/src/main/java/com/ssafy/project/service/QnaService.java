package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.Qna;

public interface QnaService {
	public int intsertQna(Qna qna);

	public Qna getQna(Qna qna);

	public List<Qna> getAllQnaList();

	public int deleteQna(Qna qna);

	public int updateQna(Qna qna);

	public int cntUpQnaView(Qna qna);
}
