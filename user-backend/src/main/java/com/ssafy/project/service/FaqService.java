package com.ssafy.project.service;

import java.util.List;

import com.ssafy.project.model.Faq;

public interface FaqService {
	public int intsertFaq(Faq faq);

	public Faq getFaq(Faq faq);

	public List<Faq> getAllFaqList();

	public int deleteFaq(Faq faq);

	public int updateFaq(Faq faq);

	public int cntUpFaqView(Faq faq);
}
