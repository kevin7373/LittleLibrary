package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.ReqBook;

public interface ReqBookService {
	public int insertReqBook(ReqBook reqBook);

	public List<ReqBook> getAllReqBook(ReqBook reqBook);

}
