package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Faq;
import com.ssafy.project.repository.FaqDao;

@Service
public class FaqServiceImpl implements FaqService {

	@Autowired
	FaqDao faqDao;

	@Override
	public int intsertFaq(Faq faq) {
		return faqDao.insertFaq(faq);
	}

	@Override
	public Faq getFaq(Faq faq) {
		return faqDao.getFaq(faq);
	}

	@Override
	public List<Faq> getAllFaqList() {
		return faqDao.getAllFaqList();
	}

	@Override
	public int deleteFaq(Faq faq) {
		return faqDao.deleteFaq(faq);
	}

	@Override
	public int updateFaq(Faq faq) {
		return faqDao.updateFaq(faq);
	}

	@Override
	public int cntUpFaqView(Faq faq) {
		return faqDao.cntUpFaqView(faq);
	}

	@Override
	public Faq getFaqByFaqId(int faqId) {
		return faqDao.getFaqByFaqId(faqId);
	}

}
