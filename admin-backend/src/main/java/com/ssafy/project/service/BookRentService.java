package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.BookRent;
import com.ssafy.project.model.RentCnt;
import com.ssafy.project.repository.BookRentDao;

@Service
public class BookRentService {

	@Autowired
	BookRentDao bookRentDao;

	public int getRentBookCntByBookId(RentCnt rentCnt) {
		return bookRentDao.getRentBookCntByBookId(rentCnt);
	}

	public int getExpireBookCntByBookId(RentCnt rentCnt) {
		return bookRentDao.getExpireBookCntByBookId(rentCnt);
	}

	public List<BookRent> getAllRentBookList() {
		return bookRentDao.getAllRentBookList();
	}

	public List<BookRent> getAllReturnBookList() {
		return bookRentDao.getAllReturnBookList();
	}

	public List<BookRent> getAllExpiredBookList() {
		return bookRentDao.getAllExpiredBookList();
	}

	public String getUserIdByBookId(String bookId) {
		return bookRentDao.getUserIdByBookId(bookId);
	}

	public boolean isRentedByBookId(String bookId) {
		int isRented = bookRentDao.isRentedByBookId(bookId);

		if (isRented > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteBookRentHistory(String bookId) {
		bookRentDao.deleteBookRentHistory(bookId);
	}

	public String getRemainDay(String brId, String td) {
		return bookRentDao.getRemainDay(brId, td);
	}

	public String getBookRentId(String bookId) {
		return bookRentDao.getBookRentId(bookId);
	}

	public String getExpiredDay(String brId, String td) {
		return bookRentDao.getExpiredDay(brId, td);
	}

	public int isNowRent(String bookId) {
		if (bookRentDao.isNowRent(bookId) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public int isNowExpire(String bookId) {
		if (bookRentDao.isNowExpire(bookId) > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
