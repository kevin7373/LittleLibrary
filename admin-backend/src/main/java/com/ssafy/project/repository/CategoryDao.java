package com.ssafy.project.repository;

import com.ssafy.project.model.Category;

public interface CategoryDao {

	public Category getCategoryNameByUserId(String userId);

	public String getCategoryIdByCategoryName(String categoryName);

}
