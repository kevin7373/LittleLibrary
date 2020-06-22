package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.ReqBookDetail;
import com.ssafy.project.repository.ReqBookDao;

@Service
public class ReqBookService {

	@Autowired
	ReqBookDao reqbookdao;

	public List<ReqBook> getReqBookMain(String reqCity, String userRoleId) {
		try {
			return reqbookdao.getReqBookMain(reqCity, userRoleId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<ReqBook> getAllReqBookList(String reqCity, String userRoleId) {
		return reqbookdao.getAllReqBookList(reqCity, userRoleId);
	}

	public void insertDelFlag(String reqId) {
		reqbookdao.insertDelFlag(reqId);
	}

	public ReqBookDetail getReqBookDetailByReqId(String reqId) {
		return reqbookdao.getReqBookDetailByReqId(reqId);
	}

}
