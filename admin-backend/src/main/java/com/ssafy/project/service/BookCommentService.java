package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.BookComment;
import com.ssafy.project.repository.BookCommentDao;

@Service
public class BookCommentService {

	@Autowired
	BookCommentDao bookCmntDao;

	public List<BookComment> getBookComment(String bookISBN) {
		return bookCmntDao.getBookComment(bookISBN);
	}

	public void insertBookComment(BookComment bc) {
		try {
			bookCmntDao.insertBookComment(bc);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public void updateBookComment(BookComment bc) {
		try {
			bookCmntDao.updateBookComment(bc);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public void deleteBookComment(String bookCommentId) {
		try {
			bookCmntDao.deleteBookComment(bookCommentId);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

}
