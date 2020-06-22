package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.Qna;

public interface QnaDao {
	public int insertQna(Qna qna);

	public Qna getQna(Qna qna);

	public List<Qna> getAllQnaList();

	public int deleteQna(Qna qna);

	public int updateQna(Qna qna);

	public int cntUpQnaView(Qna qna);

	public Qna getQnaByQnaId(int qnaId);
}
