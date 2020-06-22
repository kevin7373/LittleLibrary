package com.ssafy.project.model;

public class InterestCategory {
	private String userId;
	private String categoryId;
	private String categoryName;

	public InterestCategory() {
		super();
	}

	public InterestCategory(String userId, String categoryId) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
	}

	public InterestCategory(String userId, String categoryId, String categoryName) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "InterestCategory [userId=" + userId + ", categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
