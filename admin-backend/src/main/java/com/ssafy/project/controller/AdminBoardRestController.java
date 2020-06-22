package com.ssafy.project.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.model.Faq;
import com.ssafy.project.model.Notice;
import com.ssafy.project.model.Qna;
import com.ssafy.project.model.QnaComment;
import com.ssafy.project.service.FaqService;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.NoticeService;
import com.ssafy.project.service.QnaCommentService;
import com.ssafy.project.service.QnaService;

@RestController
@RequestMapping("/admin/api")
public class AdminBoardRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FaqService faqService;

	@Autowired
	private QnaService qnaService;

	@Autowired
	private QnaCommentService qnaCommentService;

	@Value("${resources.user_location}")
	private String resourcesLocation;

	@Value("${resources.user_uri_path}")
	private String resourcesUriPath;

	@Value("${server.port}")
	private String serverPort;

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/board/notice/register")
	public ResponseEntity<Map<String, Object>> regNotice(@RequestBody Notice notice, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userRoleId = (String) tmpMap.get("userRoleId");

			if (userRoleId.equals("0")) {
				status = HttpStatus.OK;
				resultMap.put("msg", "0");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Notice addNotice = new Notice(-1, notice.getNoticeTitle(), notice.getNoticeContent(),
					"" + sdf.format(new Date()), 1, "" + tmpMap.get("userId"));
			noticeService.intsertNotice(addNotice);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/notice/update/{noticeId}")
	public ResponseEntity<Map<String, Object>> udtNotice(@PathVariable int noticeId, @RequestBody Notice notice,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = noticeService.updateNotice(
					new Notice(noticeId, notice.getNoticeTitle(), notice.getNoticeContent(), null, -1, null));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/notice/delete/{noticeId}")
	public ResponseEntity<Map<String, Object>> delNotice(@PathVariable int noticeId, @RequestBody Notice notice,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Notice delNotice = new Notice(noticeId, null, null, null, 0, null);
			noticeService.deleteNotice(delNotice);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/notice/read/{noticeId}")
	public ResponseEntity<Map<String, Object>> readNotice(@PathVariable int noticeId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Notice notice = noticeService.getNoticeByNoticeId(noticeId);
			notice.setNoticeWriteDate(notice.getNoticeWriteDate().substring(0, 16));

			resultMap.put("notice", notice);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/notice")
	public ResponseEntity<Map<String, Object>> listNotice(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Notice> noticeList = noticeService.getAllNoticeList();
			Collections.reverse(noticeList);

			for (int i = 0; i < noticeList.size(); i++) {
				noticeList.get(i).setNoticeWriteDate(noticeList.get(i).getNoticeWriteDate().substring(0, 16));
			}
			resultMap.put("noticeList", noticeList);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/notice/count/{noticeId}")
	public ResponseEntity<Map<String, Object>> cntNotice(@PathVariable int noticeId, @RequestBody Notice notice,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {

			int result = noticeService.cntUpNoticeView(new Notice(noticeId, null, null, null, -1, null));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/board/faq/register")
	public ResponseEntity<Map<String, Object>> regFaq(@RequestBody Faq faq, HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Faq addFaq = new Faq(-1, faq.getFaqTitle(), faq.getFaqContent(), "" + sdf.format(new Date()), 1,
					"" + tmpMap.get("userId"));
			faqService.intsertFaq(addFaq);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/faq/update/{faqId}")
	public ResponseEntity<Map<String, Object>> udtFaq(@PathVariable int faqId, @RequestBody Faq faq,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = faqService.updateFaq(new Faq(faqId, faq.getFaqTitle(), faq.getFaqContent(), null, -1, null));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/faq/delete/{faqId}")
	public ResponseEntity<Map<String, Object>> delFaq(@PathVariable int faqId, HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Faq delFaq = new Faq(faqId, null, null, null, 0, null);
			faqService.deleteFaq(delFaq);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/faq/read/{faqId}")
	public ResponseEntity<Map<String, Object>> readFaq(@PathVariable int faqId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Faq faq = faqService.getFaqByFaqId(faqId);
			faq.setFaqWriteDate(faq.getFaqWriteDate().substring(0, 16));

			resultMap.put("faq", faq);
			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/faq/list")
	public ResponseEntity<Map<String, Object>> listFaq(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Faq> faqList = faqService.getAllFaqList();
			Collections.reverse(faqList);

			for (int i = 0; i < faqList.size(); i++) {
				faqList.get(i).setFaqWriteDate(faqList.get(i).getFaqWriteDate().substring(0, 16));
			}
			resultMap.put("faqList", faqList);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/faq/count/{faqId}")
	public ResponseEntity<Map<String, Object>> cntFaq(@PathVariable int faqId, @RequestBody Faq faq,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = faqService.cntUpFaqView(new Faq(faqId, null, null, null, -1, null));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/board/qna/register")
	public ResponseEntity<Map<String, Object>> regQna(@RequestBody Qna qna, HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Qna addQna = new Qna(-1, qna.getQnaTitle(), qna.getQnaContent(), "" + sdf.format(new Date()), 1,
					"" + tmpMap.get("userId"));
			qnaService.intsertQna(addQna);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/qna/update/{qnaId}")
	public ResponseEntity<Map<String, Object>> udtQna(@PathVariable int qnaId, @RequestBody Qna qna,
			HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = qnaService.updateQna(new Qna(qnaId, qna.getQnaTitle(), qna.getQnaContent(), null, -1, null));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/qna/delete/{qnaId}")
	public ResponseEntity<Map<String, Object>> delQna(@PathVariable int qnaId, HttpServletRequest req,
			HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Qna delQna = new Qna(qnaId, null, null, null, 0, null);
			qnaService.deleteQna(delQna);

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/qna/read/{qnaId}")
	public ResponseEntity<Map<String, Object>> readQna(@PathVariable int qnaId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Qna qna = qnaService.getQnaByQnaId(qnaId);
			qna.setQnaWriteDate(qna.getQnaWriteDate().substring(0, 16));

			resultMap.put("qna", qna);
			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/qna/list")
	public ResponseEntity<Map<String, Object>> listQna(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Qna> qnaList = qnaService.getAllQnaList();
			Collections.reverse(qnaList);

			for (int i = 0; i < qnaList.size(); i++) {
				qnaList.get(i).setQnaWriteDate(qnaList.get(i).getQnaWriteDate().substring(0, 16));
			}
			resultMap.put("qnaList", qnaList);

			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/qna/count/{qnaId}")
	public ResponseEntity<Map<String, Object>> cntQna(@PathVariable int qnaId, @RequestBody Qna qna,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = qnaService.cntUpQnaView(new Qna(qnaId, null, null, null, -1, null));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/board/qna/{qnaId}/register")
	public ResponseEntity<Map<String, Object>> regQnaComment(@PathVariable int qnaId,
			@RequestBody QnaComment qnaComment, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			int result = qnaCommentService.insertQnaComment(new QnaComment(-1, qnaComment.getQnaCommentContent(),
					"" + tmpMap.get("userId"), "" + sdf.format(new Date()), qnaId));

			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/board/qna/{qnaId}/list")
	public ResponseEntity<Map<String, Object>> listQnaComment(@PathVariable int qnaId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<QnaComment> qnaCommentList = qnaCommentService
					.gwtAllQnaComment(new QnaComment(-1, null, null, null, qnaId));
			resultMap.put("qnaCommentList", qnaCommentList);

			for (int i = 0; i < qnaCommentList.size(); i++) {
				if (qnaCommentList.get(i).getUserImg() != null && !qnaCommentList.get(i).getUserImg().equals("")) {
					qnaCommentList.get(i)
							.setUserImg("https://i02b206.p.ssafy.io/image" + qnaCommentList.get(i).getUserImg());
				}
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/qna/{qnaId}/update/{qnaCommentId}")
	public ResponseEntity<Map<String, Object>> QnaCommentUpdate(@PathVariable int qnaId, @PathVariable int qnaCommentId,
			@RequestBody QnaComment qnaComment, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			qnaComment.setQnaCommentDate(sdf.format(today));

			qnaCommentService.updateQnaComment(qnaComment);

			status = HttpStatus.OK;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/board/qna/{qnaId}/delete/{qnaCommentId}")
	public ResponseEntity<Map<String, Object>> QnaCommentDelete(@PathVariable int qnaId, @PathVariable int qnaCommentId,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			qnaCommentService.deleteQnaComment(qnaCommentId);

			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}