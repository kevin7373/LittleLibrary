package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Notice;
import com.ssafy.project.repository.NoticeDao;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao noticeDao;

	@Override
	public int intsertNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.insertNotice(notice);
	}

	@Override
	public Notice getNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.getNotice(notice);
	}

	@Override
	public List<Notice> getAllNoticeList() {
		// TODO Auto-generated method stub
		return noticeDao.getAllNoticeList();
	}

	@Override
	public int deleteNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.deleteNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.updateNotice(notice);
	}

	@Override
	public int cntUpNoticeView(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.cntUpNoticeView(notice);
	}

}
