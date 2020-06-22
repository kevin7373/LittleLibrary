package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.User;

@Repository
public class BookDaoImpl implements BookDao {
	String ns = "ssafy.book.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<Book> getAllBookList() {
		return sqlSession.selectList(ns + "getAllBookList");
	}

	@Override
	public Book getBookById(String bookId) {
		return sqlSession.selectOne(ns + "getBookById", bookId);
	}

	@Override
	public Book getBookByName(String bookName) {
		return sqlSession.selectOne(ns + "getBookByName", bookName);
	}

	@Override
	public void addBook(Book book) {
		sqlSession.insert(ns + "addBook", book);
	}

	@Override
	public Book getBookByISBN(String bookISBN) throws Exception {
		return sqlSession.selectOne(ns + "getBookByISBN", bookISBN);
	}

	@Override
	public List<Book> getBookListByCategoryId(User user) {
		return sqlSession.selectList(ns + "getBookListbyCategoryId", user);
	}

	@Override
	public void upRentCnt(Book book) {
		sqlSession.update(ns + "upRentCnt", book);
	}

	@Override
	public List<Book> getBestBookList() {
		return sqlSession.selectList(ns + "getBestBookList");
	}

	@Override
	public List<Book> getAllBookByISBN(Book book) {
		return sqlSession.selectList(ns + "getAllBookByISBNandCityId", book);
	}

	@Override
	public List<Book> getAllBookRentOKByCityId(String cityId) {
		return sqlSession.selectList(ns + "getAllBookRentOKByCityId", cityId);
	}
	
	@Override
	public List<Book> getAllBookByCategoryId(String categoryId) {
		return sqlSession.selectList(ns + "getAllBookByCategoryId" , categoryId);
	}

	@Override
	public List<Book> getTodayBook() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(ns + "getTodayBook");
	}

	@Override
	public int cntRentableBookByISBNandcityId(Book book) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(ns + "cntRentableBookByISBNandCityId", book);
	}

	@Override
	public int cntAllBookByISBNandcityId(Book book) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(ns + "cntAllBookByISBNandCityId", book);
	}

}