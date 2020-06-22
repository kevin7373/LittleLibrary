package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.UserjRentjBook;
import com.ssafy.project.repository.UserjRentjBookDao;

@Service
public class UserjRentjBookService {

	@Autowired
	UserjRentjBookDao userjRentjBookDao;

	public List<UserjRentjBook> getRentUserList() {
		return userjRentjBookDao.getRentUserList();
	}

	public UserjRentjBook getBookDetail(String bookId) {
		return userjRentjBookDao.getBookDetail(bookId);
	}

	public UserjRentjBook getRentUserDetail(String bookRentId) {
		return userjRentjBookDao.getRentUserDetail(bookRentId);
	}

}
