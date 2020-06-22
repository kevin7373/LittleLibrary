package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.ReqBook;
import com.ssafy.project.repository.BookDao;
import com.ssafy.project.repository.ReqBookDao;

@Service
public class ReqBookServiceImpl implements ReqBookService {

	@Autowired
	ReqBookDao reqBookDao;

	@Autowired
	BookDao bookDao;

	@Override
	public int insertReqBook(ReqBook reqBook) {
		if (bookDao.getAllBookByISBN(new Book(null, reqBook.getBookISBN(), null, 0, null, null, null, null, 0, null, reqBook.getReqCity(), null)).size() > 0) {
			reqBook.setHave(1);
		}
		return reqBookDao.insertReqBook(reqBook);
	}

	@Override
	public List<ReqBook> getAllReqBook(ReqBook reqBook) {
		return reqBookDao.getAllReqBook(reqBook);
	}

}
