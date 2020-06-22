package com.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Category;
import com.ssafy.project.repository.CategoryDao;

@Service
public class CategoryService {

	@Autowired
	CategoryDao categoryDao;

	public Category getCategoryNameByUserId(String userId) {
		return categoryDao.getCategoryNameByUserId(userId);
	}

	public String getCategoryIdByCategoryName(String categoryName) {
		return categoryDao.getCategoryIdByCategoryName(categoryName);
	}

}