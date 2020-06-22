package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.User;

public interface BookDao {
	public List<Book> getAllBookList();

	public Book getBookById(String bookId);

	public Book getBookByName(String bookName);

	public void addBook(Book book) throws Exception;

	public Book getBookByISBN(String bookISBN) throws Exception;

	public List<Book> getBookListByCategoryId(User user);

	public void upRentCnt(Book book);

	public List<Book> getBestBookList();
	
	public List<Book> getAllBookByISBN(Book book);

	public List<Book> getAllBookRentOKByCityId(String cityId);

	public List<Book> getAllBookByCategoryId(String categoryId);
	
	public List<Book> getTodayBook();
	
	public int cntRentableBookByISBNandcityId(Book book);
	
	public int cntAllBookByISBNandcityId(Book book);

}
