package com.ssafy.project.model;

import java.util.List;

import lombok.Data;

@Data
public class Message {
	String desc;
	List<String> buttonName;
	List<String> routeURL;
	public Message(String desc, List<String> buttonName, List<String> routeURL) {
		super();
		this.desc = desc;
		this.buttonName = buttonName;
		this.routeURL = routeURL;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<String> getButtonName() {
		return buttonName;
	}
	public void setButtonName(List<String> buttonName) {
		this.buttonName = buttonName;
	}
	public List<String> getRouteURL() {
		return routeURL;
	}
	public void setRouteURL(List<String> routeURL) {
		this.routeURL = routeURL;
	}

	
}
