package com.ssafy.project.util.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ChangePassWord {
	
	String userPw;
	String userNewPw;
	String userId;
	
	public ChangePassWord(String userPw, String userNewPw, String userId) {
		super();
		this.userPw = userPw;
		this.userNewPw = userNewPw;
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNewPw() {
		return userNewPw;
	}

	public void setUserNewPw(String userNewPw) {
		this.userNewPw = userNewPw;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
