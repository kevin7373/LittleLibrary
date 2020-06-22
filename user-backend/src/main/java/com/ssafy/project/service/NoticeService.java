package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.Notice;

public interface NoticeService {
	public int intsertNotice(Notice notice);

	public Notice getNotice(Notice notice);

	public List<Notice> getAllNoticeList();

	public int deleteNotice(Notice notice);

	public int updateNotice(Notice notice);

	public int cntUpNoticeView(Notice notice);
}
