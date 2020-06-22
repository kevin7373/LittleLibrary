package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.UserjRentjBook;

public interface UserjRentjBookDao {

	List<UserjRentjBook> getRentUserList();

	UserjRentjBook getBookDetail(String bookId);

	UserjRentjBook getRentUserDetail(String bookRentId);

}
