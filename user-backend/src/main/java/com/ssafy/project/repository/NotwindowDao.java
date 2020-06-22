package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.Notwindow;

public interface NotwindowDao {
	public int insertNotwindow(Notwindow notwindow);

	public List<Notwindow> getNotwindowList(Notwindow notwindow);

	public int deleteNotwindow(Notwindow notwindow);
}
