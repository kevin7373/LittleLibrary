package com.ssafy.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	public List<User> getAllUser(@Param("cityId") String cityId, @Param("userRoleId") String userRoleId) throws Exception;

	public int checkId(String userId) throws Exception;

	public User getProfile(String userId);

	public User getUserInfoByUserId(String userId);

	public List<String> getInterestCategoryByUserId(String userId);

	public String getUserRoleIdByUserId(String userId);

	public User getAdminInfoByUserId(String userId);

	public int checkStudentId(String studentId, String userId);

	public void updateUserPw(User user);

}
