package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookDetail;

@Repository
public class BookDaoImpl implements BookDao {
	String ns = "ssafy.book.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public int getAllBookCnt(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectOne(ns + "getAllBookCnt", map);
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
	public List<Book> getBestBookList(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getBestBookList", map);
	}

	@Override
	public BookDetail getBookDetailById(String bookId) {
		return sqlSession.selectOne(ns + "getBookDetailById", bookId);
	}

	@Override
	public BookDetail getReturnBookDetailByBookRentId(@Param("bookRentId") String bookRentId,
			@Param("cityId") String cityId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bookRentId", bookRentId);
		map.put("cityId", cityId);
		return sqlSession.selectOne(ns + "getReturnBookDetailByBookRentId", map);
	}

	@Override
	public void deleteBook(String bookId) {
		sqlSession.delete(ns + "deleteBook", bookId);

	}

	@Override
	public void addBookByFile(Book book) {
		sqlSession.insert(ns + "addBookByFile", book);
	}

	@Override
	public String getCityIdByCityName(String cityName) {
		return sqlSession.selectOne(ns + "getCityIdByCityName", cityName);
	}

	@Override
	public String getClassIdByClassName(String className) {
		return sqlSession.selectOne(ns + "getClassIdByClassName", className);
	}

	@Override
	public List<Book> getAllBookList(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getAllBookList", map);
	}

	@Override
	public List<String> getexistBookCityIdList(String inputBid) {
		return sqlSession.selectList(ns + "getexistBookCityIdList", inputBid);
	}

	@Override
	public int checkBookId(String editBookId, String cityId) {
		Map<String, String> map = new HashMap<>();
		map.put("bookId", editBookId);
		map.put("cityId", cityId);
		return sqlSession.selectOne(ns + "checkBookId", map);
	}

	@Override
	public List<String> getCityIdByBookId(String bookId) {
		return sqlSession.selectList(ns + "getCityIdByBookId", bookId);
	}

}