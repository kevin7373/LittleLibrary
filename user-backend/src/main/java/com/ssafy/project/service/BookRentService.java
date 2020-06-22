package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.BookRent;

public interface BookRentService {
	public int insertBookRent(BookRent bookRent);

	public int cntRentedBook(BookRent bookRent);

	public int deleteBookRent(BookRent bookRent);

	public BookRent getBookIdByUserId(String userId);
	
	public List<BookRent> getBookRentByUserId(BookRent bookRent);
	
	public List<BookRent> getBookRentByUserIdAll(BookRent bookRent);
}
