package com.ssafy.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookRent;
import com.ssafy.project.model.Notwindow;
import com.ssafy.project.model.RentMark;
import com.ssafy.project.model.User;
import com.ssafy.project.repository.BookDao;
import com.ssafy.project.repository.BookRentDao;
import com.ssafy.project.repository.NotwindowDao;
import com.ssafy.project.repository.RentMarkDao;
import com.ssafy.project.repository.UserDao;

@Service
public class RentMarkServiceImpl implements RentMarkService {

	@Autowired
	RentMarkDao rentMarkDao;

	@Autowired
	NotwindowDao notwindowDao;

	@Override
	public int insertRentMark(RentMark rentMark) {
		// TODO Auto-generated method stub
		return rentMarkDao.insertRentMark(rentMark);
	}

	@Override
	public int deleteRentMark(RentMark rentMark) {
		// TODO Auto-generated method stub
		return rentMarkDao.deleteRentMark(rentMark);
	}

	@Override
	public int insertNotwindow(RentMark rentMark) {
		// TODO Auto-generated method stub
		List<String> userList = rentMarkDao.getUserList(rentMark);
		
		for(int i = 0 ; i < userList.size() ; i++) {
			notwindowDao.insertNotwindow(new Notwindow(-1, userList.get(i), 
					"도서 반납 알림", "시스템", "도서 반납 알림", "DATE"));
		}
		
		return 0;
	}



	

}
