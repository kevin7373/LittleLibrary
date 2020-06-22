package com.ssafy.project.model;

import java.util.List;

public class User {
	private String userId;
	private String userPw;
	private String studentId;
	private String email;
	private String userName;
	private String phone;
	private int delFlag;
	private int overduePeriod;
	private String userRoleId;
	private String userRoleName;
	private String cityId;
	private String cityName;
	private String classId;
	private String className;
	private String userImg;
	private List<String> categoryId;
	private String categoryName;

	public User() {
		super();
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, int delFlag, int overduePeriod, String userRoleId, String cityId, String userImg, List<String> categoryId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.delFlag = delFlag;
		this.overduePeriod = overduePeriod;
		this.userRoleId = userRoleId;
		this.cityId = cityId;
		this.userImg = userImg;
		this.categoryId = categoryId;
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, String cityId, List<String> categoryId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.cityId = cityId;
		this.categoryId = categoryId;
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, String cityId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.cityId = cityId;
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, int delFlag, int overduePeriod, String userRoleId, String cityId, List<String> categoryId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.delFlag = delFlag;
		this.overduePeriod = overduePeriod;
		this.userRoleId = userRoleId;
		this.cityId = cityId;
		this.categoryId = categoryId;
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, int delFlag, int overduePeriod, String userRoleId, String cityId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.delFlag = delFlag;
		this.overduePeriod = overduePeriod;
		this.userRoleId = userRoleId;
		this.cityId = cityId;
	}

	public User(String userId, String userPw, String studentId, String email, String userName, String phone, int delFlag, int overduePeriod, String userRoleId, String userRoleName, String cityId, String cityName, String classId, String className, String userImg, List<String> categoryId) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.delFlag = delFlag;
		this.overduePeriod = overduePeriod;
		this.userRoleId = userRoleId;
		this.userRoleName = userRoleName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.classId = classId;
		this.className = className;
		this.userImg = userImg;
		this.categoryId = categoryId;
	}

	public User(String userId, String studentId, String email, String userName, String phone, int overduePeriod,
			String userRoleId, String userRoleName, String cityId, String cityName, String classId, String className,
			String userImg) {
		super();
		this.userId = userId;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.overduePeriod = overduePeriod;
		this.userRoleId = userRoleId;
		this.userRoleName = userRoleName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.classId = classId;
		this.className = className;
		this.userImg = userImg;
	}

	public User(String userId, String studentId, String email, String userName, String phone, int overduePeriod, String userRoleName, String cityName, String className, String userImg) {
		super();
		this.userId = userId;
		this.studentId = studentId;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.overduePeriod = overduePeriod;
		this.userRoleName = userRoleName;
		this.cityName = cityName;
		this.className = className;
		this.userImg = userImg;
	}

	public User(String userId, String userPw) {
		this.userId = userId;
		this.userPw = userPw;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPw=" + userPw + ", studentId=" + studentId + ", email=" + email + ", userName=" + userName + ", phone=" + phone + ", delFlag=" + delFlag + ", overduePeriod=" + overduePeriod + ", userRoleId=" + userRoleId + ", userRoleName=" + userRoleName + ", cityId=" + cityId + ", cityName=" + cityName + ", classId=" + classId + ", className=" + className + ", userImg=" + userImg + ", categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getOverduePeriod() {
		return overduePeriod;
	}

	public void setOverduePeriod(int overduePeriod) {
		this.overduePeriod = overduePeriod;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public List<String> getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(List<String> categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
