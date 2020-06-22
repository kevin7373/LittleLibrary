package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.User;
import com.ssafy.project.repository.BookDao;
import com.ssafy.project.repository.UserDao;
import com.ssafy.project.util.model.ChangePassWord;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	BookDao bookDao;

	// 로그인 계정은 고정값을 이용한다.
	public User signin(String userId, String userPw) {

		User user = new User(userId, userPw, null, null, null, null, 0, 0, null, null);

		if (userPw.equals(userDao.getPW(user))) {
			return userDao.getUser(user);
		} else {
			throw new RuntimeException("그런 사람은 없어요~");
		}
	}

	public int register(User user) {

//		User user = new User(userId, userPw, studentId, email, userName, phone, delFlag, overduePeriod, userRoleId, cityId, classId);

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
			System.out.println("IC register fail");
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
			System.out.println(e.getMessage());
			System.out.println("update Fail");
		}
	}

	public List<String> getCateList(String userId) {
		List<String> cateList = new ArrayList<>();

		try {
			cateList.addAll(userDao.getInterestCategory(userId));
		} catch (Exception e) {
			System.out.println("getCateList Fail");
		}
		return cateList;
	}

	public void updateIC(InterestCategory ic) {
		try {
			userDao.insertIC(ic);
		} catch (Exception e) {
			System.out.println("IC update fail");
//			System.out.println(e.getMessage());
		}
	}

	public void deleteIC(String userId) {
		try {
			userDao.deleteIC(userId);
		} catch (Exception e) {
			System.out.println("IC delete fail");
		}
	}

	// 관리자가 user 검색 시
	public List<User> getAllUserList() {
		List<User> userList = new ArrayList<>();

		try {
			userList = userDao.getAllUser();
		} catch (Exception e) {
			System.out.println("getAllUser fail");
		}
		return userList;
	}

	public int checkId(String userId) {
		try {
			int result = userDao.checkId(userId);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("checkId fail");
		}
	}

	public List<Book> getBookListByCategoryId(User user) {
		return bookDao.getBookListByCategoryId(user);
	}

	public int checkStudentId(String studentId) {
		try {
			int result = userDao.checkStudentId(studentId);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("check studentId fail");
		}
	}

	public int setUserPw(ChangePassWord changePassWord) {
		if (userDao.getPW(new User(changePassWord.getUserId(), null)).equals(changePassWord.getUserPw())) {
			userDao.setUserPw(new User(changePassWord.getUserId(), changePassWord.getUserNewPw()));
			return 1;
		}
		return 0;
	}

	public int setUserImg(User user) {
		return userDao.setUserImg(user);
	}

	public User getUserByUserId(User user) {
		return userDao.getUser(user);
	}
	
	public String getUserPw(User user) {
		return userDao.getPW(user);
	}

}
