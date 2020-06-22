package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.ReqBookDetail;

public interface ReqBookDao {

	public List<ReqBook> getAllReqBookList(String reqCity, String userRoleId);

	public List<ReqBook> getReqBookMain(String reqCity, String userRoleId);

	public void insertDelFlag(String reqId);

	public ReqBookDetail getReqBookDetailByReqId(String reqId);
}
