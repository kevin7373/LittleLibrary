package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.ReqBook;

public interface ReqBookDao {
	public int insertReqBook(ReqBook reqBook);

	public List<ReqBook> getAllReqBook(ReqBook reqBook);

}
