package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.Notice;

public interface NoticeDao {
	public int insertNotice(Notice notice);

	public Notice getNotice(Notice notice);

	public List<Notice> getAllNoticeList();

	public int deleteNotice(Notice notice);

	public int updateNotice(Notice notice);

	public int cntUpNoticeView(Notice notice);

	public Notice getNoticeByNoticeId(int noticeId);
}
