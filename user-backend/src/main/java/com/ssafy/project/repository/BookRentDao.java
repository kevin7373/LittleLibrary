package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.BookRent;

public interface BookRentDao {
	public int insertBookRent(BookRent bookRent);

	public int cntRentedBook(BookRent bookRent);

	public int deleteBookRent(BookRent bookRent);

	public List<BookRent> getBookRentByUser(BookRent bookRent);

	public List<BookRent> getBookRentByUserAll(BookRent bookRent);

	public int cntBookRentByUser(BookRent bookRent);

	public BookRent getBookIdByUserId(String userId);
	
	
}
