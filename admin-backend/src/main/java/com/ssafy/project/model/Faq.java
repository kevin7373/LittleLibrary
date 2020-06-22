package com.ssafy.project.model;

public class Faq {
	private int faqId;
	private String faqTitle;
	private String faqContent;
	private String faqWriteDate;
	private int faqViewCnt;
	private String userId;
	private String userName;

	public Faq() {
		super();
	}

	public Faq(int faqId, String faqTitle, String faqContent, String faqWriteDate, int faqViewCnt, String userId) {
		super();
		this.faqId = faqId;
		this.faqTitle = faqTitle;
		this.faqContent = faqContent;
		this.faqWriteDate = faqWriteDate;
		this.faqViewCnt = faqViewCnt;
		this.userId = userId;
	}

	public Faq(int faqId, String faqTitle, String faqContent, String faqWriteDate, int faqViewCnt, String userId,
			String userName) {
		super();
		this.faqId = faqId;
		this.faqTitle = faqTitle;
		this.faqContent = faqContent;
		this.faqWriteDate = faqWriteDate;
		this.faqViewCnt = faqViewCnt;
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Faq [faqId=" + faqId + ", faqTitle=" + faqTitle + ", faqContent=" + faqContent + ", faqWriteDate="
				+ faqWriteDate + ", faqViewCnt=" + faqViewCnt + ", userId=" + userId + ", userName=" + userName + "]";
	}

	public int getFaqId() {
		return faqId;
	}

	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}

	public String getFaqTitle() {
		return faqTitle;
	}

	public void setFaqTitle(String faqTitle) {
		this.faqTitle = faqTitle;
	}

	public String getFaqContent() {
		return faqContent;
	}

	public void setFaqContent(String faqContent) {
		this.faqContent = faqContent;
	}

	public String getFaqWriteDate() {
		return faqWriteDate;
	}

	public void setFaqWriteDate(String faqWriteDate) {
		this.faqWriteDate = faqWriteDate;
	}

	public int getFaqViewCnt() {
		return faqViewCnt;
	}

	public void setFaqViewCnt(int faqViewCnt) {
		this.faqViewCnt = faqViewCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
