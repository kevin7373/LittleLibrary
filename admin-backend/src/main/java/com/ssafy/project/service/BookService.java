package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookDetail;
import com.ssafy.project.repository.BookDao;

@Service
public class BookService {

	@Autowired
	BookDao bookDao;

	public List<Book> getAllBookList(String cityId, String userRoleId) {
		return bookDao.getAllBookList(cityId, userRoleId);
	}

	public Book getBookById(String bookId) {
		return bookDao.getBookById(bookId);
	}

	public Book getBookDetail(String bookName) {
		return bookDao.getBookByName(bookName);
	}

	public void addBook(Book book) {
		try {
			bookDao.addBook(book);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public String getCityIdByCityName(String cityName) {
		return bookDao.getCityIdByCityName(cityName);
	}

	public String getClassIdByClassName(String className) {
		return bookDao.getClassIdByClassName(className);
	}

	public Book getBookByISBN(String bookISBN) throws Exception {
		return bookDao.getBookByISBN(bookISBN);
	}

	public int getAllBookCnt(String cityId, String userRoleId) {
		return bookDao.getAllBookCnt(cityId, userRoleId);
	}

	public List<Book> getBestBookList(String cityId, String userRoleId) {
		return bookDao.getBestBookList(cityId, userRoleId);
	}

	public BookDetail getBookDetailById(String bookId) {
		return bookDao.getBookDetailById(bookId);
	}

	public BookDetail getReturnBookDetailByBookRentId(String bookRentId, String cityId) {
		return bookDao.getReturnBookDetailByBookRentId(bookRentId, cityId);
	}

	public void deleteBook(String bookId) {
		bookDao.deleteBook(bookId);
	}

	public void addBookByFile(Book book) {
		bookDao.addBookByFile(book);
	}

	public List<String> getexistBookCityIdList(String inputBid) {
		return bookDao.getexistBookCityIdList(inputBid);
	}

	public boolean checkBookId(String editBookId, String cityId) {
		if (bookDao.checkBookId(editBookId, cityId) > 0) {
			return false;
		} else {
			return true;
		}
	}

	public List<String> getCityIdByBookId(String bookId) {
		return bookDao.getCityIdByBookId(bookId);
	}

}
