package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.BookjRent;
import com.ssafy.project.repository.BookjRentDao;

@Service
public class BookjRentService {

	@Autowired
	BookjRentDao bookjrentdao;

	public List<BookjRent> getAllRentBookList(String cityId, String userRoleId) {
		return bookjrentdao.getAllRentBookList(cityId, userRoleId);
	}

	public List<BookjRent> getAllReturnBookList(String cityId, String userRoleId) {
		return bookjrentdao.getAllReturnBookList(cityId, userRoleId);
	}

	public List<BookjRent> getAllExpiredBookList(String cityId, String userRoleId) {
		return bookjrentdao.getAllExpiredBookList(cityId, userRoleId);
	}

	public String getBookRentId(String bookId) {
		return bookjrentdao.getBookRentId(bookId);
	}

	public int getRentCnt(String cityId, String tday, String userRoleId) {
		return bookjrentdao.getRentCnt(cityId, tday, userRoleId);
	}

	public int getExpireBookCnt(String cityId, String tday, String userRoleId) {
		return bookjrentdao.getExpireBookCnt(cityId, tday, userRoleId);
	}

	public double getRealReturnCnt(String cityId) {
		return bookjrentdao.getRealReturnCnt(cityId);
	}

	public double getTodayReturnCnt(String cityId) {
		return bookjrentdao.getTodayReturnCnt(cityId);
	}

	public List<BookjRent> getRentCntListByPeriod(String cityId, String userRoleId) {
		return bookjrentdao.getRentCntListByPeriod(cityId, userRoleId);
	}

	public double getEpBookCnt(String tmpCityId, String tday) {
		return bookjrentdao.getEpBookCnt(tmpCityId, tday);
	}

	public double getTodayReal(String cityId) {
		// TODO Auto-generated method stub
		return bookjrentdao.getTodayReal(cityId);
	}

	public double getTodayRet(String cityId) {
		// TODO Auto-generated method stub
		return bookjrentdao.getTodayRet(cityId);
	}

}
