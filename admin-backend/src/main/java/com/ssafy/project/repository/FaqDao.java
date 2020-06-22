package com.ssafy.project.repository;

import java.util.List;

import com.ssafy.project.model.Faq;

public interface FaqDao {
	public int insertFaq(Faq faq);

	public Faq getFaq(Faq faq);

	public List<Faq> getAllFaqList();

	public int deleteFaq(Faq faq);

	public int updateFaq(Faq faq);

	public int cntUpFaqView(Faq faq);

	public Faq getFaqByFaqId(int faqId);
}
