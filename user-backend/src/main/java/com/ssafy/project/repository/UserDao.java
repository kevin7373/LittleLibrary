package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.User;

public interface UserDao {

	// User
	public String getPW(User user);// seachById

	public int insertUser(User user);

	public User getUser(User user);

	public void insertIC(InterestCategory interestCategory) throws Exception;

	public List<String> getInterestCategory(String userId) throws Exception;

	public void updateInfo(User user) throws Exception;

	public void deleteIC(String userId) throws Exception;

	public List<User> getAllUser() throws Exception;

	public int checkId(String userId) throws Exception;

	public int setOverduePeriod(User user);

	public int checkStudentId(String studentId) throws Exception;

	public int setUserPw(User user);

	public int setUserImg(User user);

	public int getUserByUserId(User user);

}
