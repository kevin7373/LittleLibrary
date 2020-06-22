package com.ssafy.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
public class BookRentServiceImpl implements BookRentService {

	@Autowired
	BookRentDao bookRentDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	BookDao bookDao;
	
	@Autowired
	RentMarkDao rentMarkDao;
	
	@Autowired
	NotwindowDao notwindowDao;

	@Override
	public int insertBookRent(BookRent bookRent) {
		if (bookRentDao.cntBookRentByUser(bookRent) > 0)
			return -2;

		if (bookRentDao.cntRentedBook(bookRent) > 0)
			return -1;
		
		int res = bookRentDao.insertBookRent(bookRent);
		bookDao.upRentCnt(new Book(bookRent.getBookId(), null, null, 0, null, null, null, null, 0, null, null, null, null));
		
		return res;
	}

	@Override
	public int cntRentedBook(BookRent bookRent) {
		return bookRentDao.cntRentedBook(bookRent);
	}

	@Override
	public int deleteBookRent(BookRent bookRent) {
		if (bookRentDao.cntBookRentByUser(bookRent) == 0) {
			return -2;
		} else if (bookRentDao.cntRentedBook(bookRent) == 0) {
			return -1;
		} else if (!bookRentDao.getBookIdByUserId(bookRent.getUserId()).getBookId().equals((String) bookRent.getBookId())) {
			System.out.println(bookRent.getBookId() + " " + bookRentDao.getBookIdByUserId(bookRent.getUserId()));
			return -3;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
				Date now = new Date();
				Date toDate = sdf.parse(bookRentDao.getBookRentByUser(bookRent).get(0).getRentTo());
				long overdue = 0;
				if ((overdue = (now.getTime() - toDate.getTime()) / (24 * 60 * 60 * 1000)) > 0) {
					long overduePeriod = userDao.getUser(new User(bookRent.getUserId(), null, null, null, null, null, -1, -1, null, null)).getOverduePeriod();
					System.out.println(overdue + " " + overduePeriod);
					userDao.setOverduePeriod(new User(bookRent.getUserId(), null, null, null, null, null, -1, (int) overduePeriod + (int) overdue, null, null));
				}
				
				String cityId = userDao.getUser(new User(bookRent.getUserId(), null)).getCityId();
				String bookISBN = bookDao.getBookById(bookRent.getBookId()).getBookISBN();
				String bookName = bookDao.getBookById(bookRent.getBookId()).getBookName();
				System.out.println(cityId+" "+bookISBN);
				
				
				List<String> userList = rentMarkDao.getUserList(new RentMark(bookISBN, cityId, null));
				rentMarkDao.deleteRentMark(new RentMark(bookISBN, cityId, null));
				System.out.println(userList.toString());
				for(int i = 0 ; i < userList.size() ; i++) {
					notwindowDao.insertNotwindow(new Notwindow(-1, userList.get(i), sdf.format(now) +"에 ["+bookName+"] 이(가) 반납되었습니다.", "시스템", "["+bookName+"]이(가) 대여 가능합니다.", ""+sdf.format(now)));
					System.out.println(userList.get(i));
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return bookRentDao.deleteBookRent(bookRent);
	}

	@Override
	public BookRent getBookIdByUserId(String userId) {
		return bookRentDao.getBookIdByUserId(userId);
	}

	@Override
	public List<BookRent> getBookRentByUserId(BookRent bookRent) {
		// TODO Auto-generated method stub
		return bookRentDao.getBookRentByUser(bookRent);
	}

	@Override
	public List<BookRent> getBookRentByUserIdAll(BookRent bookRent) {
		// TODO Auto-generated method stub
		return bookRentDao.getBookRentByUserAll(bookRent);
	}

}
