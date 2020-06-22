package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.BookRent;
import com.ssafy.project.model.RentCnt;

public interface BookRentDao {

	public int getExpireBookCnt(String tday);

	public int getRentCnt(String tday);

	public int getRealReturnCnt();

	public int getTodayReturnCnt();

	public int getRentBookCntByBookId(RentCnt rentCnt);

	public int getExpireBookCntByBookId(RentCnt rentC);

	public List<BookRent> getAllRentBookList();

	public List<BookRent> getAllReturnBookList();

	public List<BookRent> getAllExpiredBookList();

	public String getUserIdByBookId(String bookId);

	public int isRentedByBookId(String bookId);

	public void deleteBookRentHistory(String bookId);

	public String getRemainDay(String brId, String td);

	public String getBookRentId(String bookId);

	public String getExpiredDay(String brId, String td);

	public int isNowRent(String bookId);

	public int isNowExpire(String bookId);

}
