package com.ssafy.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class InterestCategory {
	private String userId;
	private String categoryId;

	public InterestCategory() {
		super();
	}

	public InterestCategory(String userId, String categoryId) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "InterestCategory [userId=" + userId + ", categoryId=" + categoryId + "]";
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

}
