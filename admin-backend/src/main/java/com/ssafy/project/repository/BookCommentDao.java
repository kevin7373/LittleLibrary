package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.BookComment;

public interface BookCommentDao {
	
	public List<BookComment> getBookComment(String bookISBN);

	public void insertBookComment(BookComment bc) throws Exception;

	public void updateBookComment(BookComment bc) throws Exception;

	public void deleteBookComment(String bookCommentId) throws Exception;
	
}
