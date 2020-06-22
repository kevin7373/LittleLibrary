package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.project.model.BookComment;
import com.ssafy.project.model.BookCommentUser;

public interface BookCommentDao {

	public List<BookCommentUser> getBookComment(@Param("bookISBN") String bookISBN, @Param("userId") String userId);

	public void insertBookComment(BookComment bc) throws Exception;

	public void updateBookComment(BookComment bc) throws Exception;

	public void deleteBookComment(String bookCommentId) throws Exception;

	public String getCmntUser(String bookCommentId);

}
