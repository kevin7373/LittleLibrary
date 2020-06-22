package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.BookComment;
import com.ssafy.project.model.BookCommentUser;
import com.ssafy.project.repository.BookCommentDao;

@Service
public class BookCommentService {

	@Autowired
	BookCommentDao bookCmntDao;

	public List<BookCommentUser> getBookComment(String bookISBN, String userId) {
		return bookCmntDao.getBookComment(bookISBN, userId);
	}

	public void insertBookComment(BookComment bc) {
		try {
			bookCmntDao.insertBookComment(bc);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("insertBookCmnt Fail");
		}
	}

	public void updateBookComment(BookComment bc) {
		try {
			bookCmntDao.updateBookComment(bc);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("updateBookCmnt Fail");
		}
	}

	public void deleteBookComment(String bookCommentId) {
		try {
			bookCmntDao.deleteBookComment(bookCommentId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("deleteBookCmnt Fail");
		}
	}

	public String getCmntUser(String bookCommentId) {
		try {
			return bookCmntDao.getCmntUser(bookCommentId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("getCmntUser Fail");
			throw new NullPointerException(e.getMessage());
		}
	}

}
