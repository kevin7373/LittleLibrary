package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.Notwindow;

public interface NotwindowService {
	public int intsertNotwindow(Notwindow notwindow);

	public int deleteNotwindow(Notwindow notwindow);

	public List<Notwindow> getNotwindowList(Notwindow notwindow);

}
