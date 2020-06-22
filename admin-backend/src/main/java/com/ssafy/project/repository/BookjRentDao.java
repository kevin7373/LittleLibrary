package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.BookjRent;

public interface BookjRentDao {

	public List<BookjRent> getAllRentBookList(String cityId, String userRoleId);

	public List<BookjRent> getAllReturnBookList(String cityId, String userRoleId);

	public List<BookjRent> getAllExpiredBookList(String cityId, String userRoleId);

	public String getBookRentId(String bookId);

	public int getRentCnt(String cityId, String tday, String userRoleId);

	public int getExpireBookCnt(String cityId, String tday, String userRoleId);

	public double getRealReturnCnt(String cityId);

	public double getTodayReturnCnt(String cityId);

	public List<BookjRent> getRentCntListByPeriod(String cityId, String userRoleId);

	public double getEpBookCnt(String tmpCityId, String tday);

	public double getTodayReal(String cityId);

	public double getTodayRet(String cityId);

}
