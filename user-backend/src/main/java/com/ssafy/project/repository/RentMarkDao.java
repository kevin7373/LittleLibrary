package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.project.model.BookComment;
import com.ssafy.project.model.BookCommentUser;
import com.ssafy.project.model.RentMark;

public interface RentMarkDao {

	public int insertRentMark(RentMark rentMark);
	
	public int deleteRentMark(RentMark rentMark);
	
	public List<String> getUserList(RentMark rentMark);

}
