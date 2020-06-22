package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.BookjRent;

@Repository
public class BookjRentDaoImpl implements BookjRentDao {
	String ns = "ssafy.bookjRent.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<BookjRent> getAllRentBookList(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getAllRentBookList", map);
	}

	@Override
	public List<BookjRent> getAllReturnBookList(@Param("cityId") String cityId,
			@Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getAllReturnBookList", map);
	}

	@Override
	public List<BookjRent> getAllExpiredBookList(@Param("cityId") String cityId,
			@Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getAllExpiredBookList", map);
	}

	@Override
	public String getBookRentId(String bookId) {
		return sqlSession.selectOne(ns + "getBookRentId", bookId);
	}

	@Override
	public int getRentCnt(@Param("cityId") String cityId, @Param("tday") String tday,
			@Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("tday", tday);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectOne(ns + "getRentCnt", map);
	}

	@Override
	public int getExpireBookCnt(@Param("cityId") String cityId, @Param("tday") String tday,
			@Param("userRoleId") String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("tday", tday);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectOne(ns + "getExpireBookCnt", map);
	}

	@Override
	public double getRealReturnCnt(String cityId) {
		return sqlSession.selectOne(ns + "getRealReturnCnt", cityId);
	}

	@Override
	public double getTodayReturnCnt(String cityId) {
		return sqlSession.selectOne(ns + "getTodayReturnCnt", cityId);
	}

	@Override
	public List<BookjRent> getRentCntListByPeriod(String cityId, String userRoleId) {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getRentCntListByPeriod", map);
	}

	@Override
	public double getEpBookCnt(@Param("tmpCityId") String tmpCityId, @Param("tday") String tday) {
		Map<String, String> map = new HashMap<>();
		map.put("tmpCityId", tmpCityId);
		map.put("tday", tday);
		return sqlSession.selectOne(ns + "getEpBookCnt", map);
	}

	@Override
	public double getTodayReal(String cityId) {
		return sqlSession.selectOne(ns + "getTodayReal", cityId);
	}

	@Override
	public double getTodayRet(String cityId) {
		return sqlSession.selectOne(ns + "getTodayRet", cityId);
	}

}