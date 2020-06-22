package com.ssafy.project.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	String ns = "ssafy.user.";

	@Autowired
	SqlSession sqlSession;

	@Override
	public String getPW(User user) {
		return sqlSession.selectOne(ns + "getPassWord", user);
	}

	@Override
	public int insertUser(User user) {
		return sqlSession.insert(ns + "insertUser", user);
	}

	@Override
	public User getUser(User user) {
		return sqlSession.selectOne(ns + "getUser", user);
	}

	@Override
	public void insertIC(InterestCategory ic) throws Exception {
		sqlSession.insert(ns + "insertIC", ic);
	}

	@Override
	public List<String> getInterestCategory(String userId) {
		return sqlSession.selectList(ns + "getInterestCategory", userId);
	}

	@Override
	public void updateInfo(User user) throws Exception {
		sqlSession.update(ns + "updateInfo", user);
	}

	@Override
	public void deleteIC(String userId) throws Exception {
		sqlSession.delete(ns + "deleteIC", userId);
	}

	@Override
	public List<User> getAllUser(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("cityId", cityId);
		map.put("userRoleId", userRoleId);
		return sqlSession.selectList(ns + "getAllUser", map);
	}

	@Override
	public int checkId(String userId) {
		return sqlSession.selectOne(ns + "checkId", userId);
	}

	@Override
	public User getProfile(String userId) {
		return sqlSession.selectOne(ns + "getProfile", userId);
	}

	@Override
	public User getUserInfoByUserId(String userId) {
		return sqlSession.selectOne(ns + "getUserInfoByUserId", userId);
	}

	@Override
	public List<String> getInterestCategoryByUserId(String userId) {
		return sqlSession.selectList(ns + "getInterestCategoryByUserId", userId);
	}

	@Override
	public String getUserRoleIdByUserId(String userId) {
		return sqlSession.selectOne(ns + "getUserRoleIdByUserId", userId);
	}

	@Override
	public User getAdminInfoByUserId(String userId) {
		return sqlSession.selectOne(ns + "getAdminInfoByUserId", userId);
	}

	@Override
	public int checkStudentId(String studentId, String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("studentId", studentId);
		return sqlSession.selectOne(ns + "checkStudentId", map);
	}

	@Override
	public void updateUserPw(User user) {
		sqlSession.update(ns + "updateUserPw", user);
	}

}
