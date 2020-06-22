package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.User;
import com.ssafy.project.repository.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	// 로그인 계정은 고정값을 이용한다.
	public User signin(String userId, String userPw) {
		User user = new User(userId, userPw, null, null, null, null, 0, 0, null, null);

		if (userPw.equals(userDao.getPW(user))) {
			return userDao.getUser(user);
		} else {
			throw new RuntimeException();
		}
	}

	public int register(String userId, String userPw, String studentId, String email, String userName, String phone,
			int delFlag, int overduePeriod, String userRoleId, String cityId) {

		User user = new User(userId, userPw, studentId, email, userName, phone, delFlag, overduePeriod, userRoleId,
				cityId);

		if (userDao.insertUser(user) > 0) {
			return 1;
		} else {
			throw new RuntimeException("register fail");
		}
	}

	public void insertIC(InterestCategory ic) {
		try {
			userDao.insertIC(ic);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// 귀중한 정보를 전달한다.
	public String getServerInfo() {
		return "made by 은서파";
	}

	public void updateInfo(User user) {
		try {
			userDao.updateInfo(user);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<String> getCateList(String userId) {
		List<String> cateList = new ArrayList<>();

		try {
			cateList.addAll(userDao.getInterestCategory(userId));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return cateList;
	}

	public void updateIC(InterestCategory ic) {
		try {
			userDao.insertIC(ic);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteIC(String userId) {
		try {
			userDao.deleteIC(userId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// 관리자가 user 검색 시
	public List<User> getAllUserList(String cityId, String userRoleId) {
		List<User> userList = new ArrayList<>();

		try {
			userList = userDao.getAllUser(cityId, userRoleId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return userList;
	}

	public int checkId(String userId) {
		try {
			int result = userDao.checkId(userId);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public User getProfile(String userId) {
		return userDao.getProfile(userId);
	}

	public User getUserInfoByUserId(String userId) {
		return userDao.getUserInfoByUserId(userId);
	}

	public List<String> getCateListByUserId(String userId) {
		return userDao.getInterestCategoryByUserId(userId);
	}

	public String getUserRoleIdByUserId(String userId) {
		return userDao.getUserRoleIdByUserId(userId);
	}

	public User getAdminInfoByUserId(String userId) {
		return userDao.getAdminInfoByUserId(userId);
	}

	public boolean checkStudentId(String studentId, String userId) {
		if (userDao.checkStudentId(studentId, userId) > 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean updatePassword(User user, String newUserPw) {
		if (user.getUserPw().equals(userDao.getPW(user))) {
			user.setUserPw(newUserPw);
			userDao.updateUserPw(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean getUserPw(User user) {
		if (user.getUserPw().equals(userDao.getPW(user))) {
			return true;
		} else {
			return false;
		}
	}
}
