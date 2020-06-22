package com.ssafy.project.repository;

import java.util.List;

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
	public List<User> getAllUser() throws Exception {
		return sqlSession.selectList(ns + "getAllUser");
	}

	@Override
	public int checkId(String userId) {
		return (Integer) sqlSession.selectOne(ns + "checkId", userId) > 0 ? 0 : 1;
	}

	@Override
	public int setOverduePeriod(User user) {
		return sqlSession.update(ns + "setOverduePeriod", user);
	}

	@Override
	public int checkStudentId(String studentId) throws Exception {
		return (Integer) sqlSession.selectOne(ns + "checkStudentId", studentId) > 0 ? 0 : 1;
	}

	@Override
	public int setUserPw(User user) {
		return sqlSession.update(ns + "setUserPw", user);
	}

	@Override
	public int setUserImg(User user) {
		return sqlSession.update(ns + "setUserImg", user);
	}

	@Override
	public int getUserByUserId(User user) {
		return sqlSession.selectOne(ns + "getUserByUserId", user);
	}

}
