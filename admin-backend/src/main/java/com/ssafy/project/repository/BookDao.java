package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookDetail;

public interface BookDao {

	public Book getBookById(String bookId);

	public Book getBookByName(String bookName);

	public void addBook(Book book) throws Exception;

	public Book getBookByISBN(String bookISBN) throws Exception;

	public BookDetail getBookDetailById(String bookId);

	public BookDetail getReturnBookDetailByBookRentId(@Param("bookRentId") String bookRentId,
			@Param("cityId") String cityId);

	public void deleteBook(String bookId);

	public void addBookByFile(Book book);

	public String getCityIdByCityName(String cityName);

	public String getClassIdByClassName(String className);

	public List<Book> getAllBookList(String cityId, String userRoleId);

	public int getAllBookCnt(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId);

	public List<Book> getBestBookList(String cityId, String userRoleId);

	public List<String> getexistBookCityIdList(String inputBid);

	public int checkBookId(String editBookId, String cityId);

	public List<String> getCityIdByBookId(String bookId);
}
