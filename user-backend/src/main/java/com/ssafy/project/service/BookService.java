package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;
import com.ssafy.project.repository.BookDao;

@Service
public class BookService {

	@Autowired
	BookDao bookDao;

	public List<Book> getAllBookList() {
		return bookDao.getAllBookList();
	}

	public Book getBookById(String bookId) throws Exception {
		try {
			return bookDao.getBookById(bookId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Book getBookDetail(String bookName) {
		return bookDao.getBookByName(bookName);
	}

	public void addBook(Book book) {
		try {
			bookDao.addBook(book);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("addBook Fail");
		}
	}

	public Book getBookByISBN(String bookISBN) throws Exception {
		return bookDao.getBookByISBN(bookISBN);
	}

	public List<Book> getBestBookList() {
		return bookDao.getBestBookList();
	}

	public List<Book> getAllBookRentOkByCityId(String cityId) {
		return bookDao.getAllBookRentOKByCityId(cityId);
	}
	
	public List<Book> getAllBookByCategoryId(String categoryId){
		return bookDao.getAllBookByCategoryId(categoryId);
	}
	
	public Book getTodayBook() {
		return bookDao.getTodayBook().get(0);
	}
	
	public int cntRentableBookByISBNandCityId(Book book) {
		return bookDao.cntRentableBookByISBNandcityId(book);
	}
	
	public int cntAllBookByISBNandCityId(Book book) {
		return bookDao.cntAllBookByISBNandcityId(book);
	}
}
