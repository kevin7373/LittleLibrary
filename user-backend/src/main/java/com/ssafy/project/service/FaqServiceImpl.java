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
		// TODO Auto-generated method stub
		return faqDao.insertFaq(faq);
	}

	@Override
	public Faq getFaq(Faq faq) {
		// TODO Auto-generated method stub
		return faqDao.getFaq(faq);
	}

	@Override
	public List<Faq> getAllFaqList() {
		// TODO Auto-generated method stub
		return faqDao.getAllFaqList();
	}

	@Override
	public int deleteFaq(Faq faq) {
		// TODO Auto-generated method stub
		return faqDao.deleteFaq(faq);
	}

	@Override
	public int updateFaq(Faq faq) {
		// TODO Auto-generated method stub
		return faqDao.updateFaq(faq);
	}

	@Override
	public int cntUpFaqView(Faq faq) {
		// TODO Auto-generated method stub
		return faqDao.cntUpFaqView(faq);
	}

}
