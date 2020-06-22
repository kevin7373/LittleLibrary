package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Notwindow;
import com.ssafy.project.repository.NotwindowDao;

@Service
public class NotwindowServiceImpl implements NotwindowService {

	@Autowired
	NotwindowDao notWindowDao;

	@Override
	public int intsertNotwindow(Notwindow notwindow) {
		return notWindowDao.insertNotwindow(notwindow);
	}

	@Override
	public int deleteNotwindow(Notwindow notwindow) {
		return notWindowDao.deleteNotwindow(notwindow);
	}

	@Override
	public List<Notwindow> getNotwindowList(Notwindow notwindow) {
		return notWindowDao.getNotwindowList(notwindow);
	}

}
