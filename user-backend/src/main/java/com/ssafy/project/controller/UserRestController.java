package com.ssafy.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookComment;
import com.ssafy.project.model.BookCommentUser;
import com.ssafy.project.model.BookRent;
import com.ssafy.project.model.Faq;
import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.Message;
import com.ssafy.project.model.Notice;
import com.ssafy.project.model.Notwindow;
import com.ssafy.project.model.Qna;
import com.ssafy.project.model.QnaComment;
import com.ssafy.project.model.RentMark;
import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.User;
import com.ssafy.project.repository.UserDao;
import com.ssafy.project.service.BookCommentService;
import com.ssafy.project.service.BookRentService;
import com.ssafy.project.service.BookService;
import com.ssafy.project.service.FaqService;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.NaverBookAPIService;
import com.ssafy.project.service.NoticeService;
import com.ssafy.project.service.NotwindowService;
import com.ssafy.project.service.QnaCommentService;
import com.ssafy.project.service.QnaService;
import com.ssafy.project.service.RentMarkService;
import com.ssafy.project.service.ReqBookService;
import com.ssafy.project.service.UserService;
import com.ssafy.project.util.model.ChangePassWord;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FaqService faqService;

	@Autowired
	private QnaService qnaService;

	@Autowired
	private QnaCommentService qnaCommentService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookCommentService bookCmntService;

	@Autowired
	private NaverBookAPIService naverBookAPIService;

	@Autowired
	private BookRentService bookRentService;

	@Autowired
	private NotwindowService notwindowService;

	@Autowired
	private ReqBookService reqBookService;
	
	@Autowired
	private RentMarkService RentMarkService;

	@Value("${resources.user_location}")
	private String resourcesLocation;

	@Value("${resources.user_uri_path}")
	private String resourcesUriPath;

	@Value("${server.port}")
	private String serverPort;

	@SuppressWarnings("unchecked")
	@PostMapping("/user/update/image")
	public ResponseEntity<Map<String, Object>> updateProfileImg(@RequestBody MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		String[] extensionArr = { ".jpg", ".jpeg", ".png" };

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			String studentId = (String) tmpMap.get("studentId");
			System.out.println();
			for (int i = 0; i < extensionArr.length; i++) {
				File oldFile = new File(resourcesLocation +"/"+ studentId + extensionArr[i]);//
				if (oldFile.exists()) {
					oldFile.delete();
					
				}
			}
			String fileType = file.getContentType();
			StringTokenizer st = new StringTokenizer(fileType, "/");
			st.nextToken();
			String fileExtension = st.nextToken();
			String userImg = "/"+studentId + "." + fileExtension;
			file.transferTo(new File(resourcesLocation + userImg));
			userService.setUserImg(new User(userId, null, studentId, null, null, null, -1, -1, null, null, null, null, null, null,  userImg, null));
			User user = userService.getUserByUserId(new User(userId, null));
			String token = jwtService.create(user);
			res.setHeader("token", token);
			status = HttpStatus.OK;
			resultMap.put("status", true);
			System.out.println(res.toString());
			System.out.println("IMAGE UPLOAD SUCS-" + userImg);
			System.out.println("UPLOAD TO:"+(resourcesLocation + userImg));
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/user/delete/image")
	public ResponseEntity<Map<String, Object>> deleteProfileWithFile(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		String[] extensionArr = { ".jpg", ".jpeg", ".png" };
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			String studentId = (String) tmpMap.get("studentId");
			for (int i = 0; i < extensionArr.length; i++) {
				File oldFile = new File(resourcesLocation +"/"+ studentId + extensionArr[i]);//
				if (oldFile.exists()) {
					oldFile.delete();
					
				}
			}
			userService.setUserImg(new User(userId, null, studentId, null, null, null, -1, -1, null, null, null, null, null, null, "", null));
			User user = userService.getUserByUserId(new User(userId, null));
			String token = jwtService.create(user);
			res.setHeader("token", token);
			status = HttpStatus.OK;
			resultMap.put("status", true);
			System.out.println(res.toString());
			System.out.println("IMAGE DELETE SUCS");
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/user/test")
	public ResponseEntity<Map<String, Object>> test(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("test");
			JSONObject item = naverBookAPIService.getBookByISBN("9791162241998");
			System.out.println(item);
			System.out.println("controller : " + item.get("description"));
			System.out.println("controller : " + item.get("link"));
		} catch (RuntimeException e) {
			System.out.println("FAIL");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println(user.toString());
			user.setOverduePeriod(0);
			user.setDelFlag(0);
			user.setUserRoleId("0");
			
			int result = userService.register(user);
			int size = user.getCategoryId().size();

			for (int i = 0; i < size; i++) {
				InterestCategory ic = new InterestCategory(user.getUserId(), user.getCategoryId().get(i));
				userService.insertIC(ic);
			}
			resultMap.put("status", true);
			status = HttpStatus.OK;

			resultMap.put("status", status);

			System.out.println("REGISTER SUCS");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/signin")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			User loginUser = userService.signin(user.getUserId(), user.getUserPw());

			List<String> cateList = new ArrayList<>();
			cateList.addAll(userService.getCateList(user.getUserId()));

			if (cateList.size() > 0) {
				loginUser.setCategoryId(cateList);
			}
			// 로그인 성공했다면 토큰을 생성한다.
			loginUser.setUserPw("");
			loginUser.setPhone("");
			String token = jwtService.create(loginUser);
			System.out.println("이거 찍고 이제 토큰 정보 뽑을 거임");
			System.out.println("토큰 정보 : " + token);
			// 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아주자.
			res.setHeader("token", token);
			System.out.println("헤더에 있는 토큰 : " + res.getHeader("token"));
			resultMap.put("token", token);
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
			status = HttpStatus.OK;

			System.out.println("SUCS LOGIN");
		} catch (RuntimeException e) {
			// log.error("로그인 실패", e);
			resultMap.put("message", "로그인 실패");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/user/check/password")
	public ResponseEntity<Map<String, Object>> chkPassword(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			System.out.println(userService.getUserPw(new User(userId, null)));
			
			if(!userService.getUserPw(new User(userId, null)).equals(user.getUserPw())) {
				resultMap.put("message", "비밀번호가 틀렸습니다");
			}else{
				resultMap.put("message", userService.signin(userId, user.getUserPw()).getPhone());
			}

			
			status = HttpStatus.OK;

			System.out.println("SUCS chkPassword");
		} catch (RuntimeException e) {
			// log.error("로그인 실패", e);
			resultMap.put("message", "password check fail");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/check")
	public ResponseEntity<Map<String, Object>> userCheck(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (userService.checkId(user.getUserId()) == 1) {
				resultMap.put("message", "사용 가능한 아이디 입니다");
				System.out.println("REGISTER SUCS");
			} else {
				resultMap.put("message", "이미 등록된 아이디 입니다.");
				System.out.println("REGISTER SUCS");
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/check/studentId")
	public ResponseEntity<Map<String, Object>> studentIdCheck(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (userService.checkStudentId(user.getStudentId()) == 1) {
				resultMap.put("message", "신규 등록이 가능합니다.");
				System.out.println("REGISTER SUCS");
			} else {
				resultMap.put("message", "계정이 이미 있습니다.");
				System.out.println("REGISTER SUCS");
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/user/update/password")
	public ResponseEntity<Map<String, Object>> udtUserPw(@RequestBody ChangePassWord changePassWord, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			String userPw = changePassWord.getUserPw();
			String userNewPw = changePassWord.getUserNewPw();
			System.out.println(userId + " " + userPw);
			if (userService.setUserPw(new ChangePassWord(userPw, userNewPw, userId)) == 1) {
				resultMap.put("message", "비밀번호가 변경되었습니다");
				System.out.println("UDATE SUCS");
			} else {
				resultMap.put("message", "비밀번호가 틀립니다.");
				System.out.println("PassWord Incorrect");
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/notice/register")
	public ResponseEntity<Map<String, Object>> regNotice(@RequestBody Notice notice, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			// jwtService.checkValid(req.getHeader("jwt-auth-token"));
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Notice addNotice = new Notice(-1, notice.getNoticeTitle(), notice.getNoticeContent(), "" + sdf.format(new Date()), 1, "" + tmpMap.get("userId"));
			noticeService.intsertNotice(addNotice);

			System.out.println(addNotice);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS addNotice");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/notice/update/{noticeId}")
	public ResponseEntity<Map<String, Object>> udtNotice(@PathVariable int noticeId, @RequestBody Notice notice, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = noticeService.updateNotice(new Notice(noticeId, notice.getNoticeTitle(), notice.getNoticeContent(), null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS udtNotice");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/notice/delete/{noticeId}")
	public ResponseEntity<Map<String, Object>> delNotice(@PathVariable int noticeId, @RequestBody Notice notice, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("delNotice");
			Notice delNotice = new Notice(noticeId, null, null, null, 0, null);
			noticeService.deleteNotice(delNotice);

			System.out.println(delNotice);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS delNotice");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/notice/read/{noticeId}")
	public ResponseEntity<Map<String, Object>> readNotice(@PathVariable int noticeId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("notice read");

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS noticeRead");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/notice/list")
	public ResponseEntity<Map<String, Object>> listNotice(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Notice> noticeList = noticeService.getAllNoticeList();
			Collections.reverse(noticeList);
			resultMap.put("noticeList", noticeList);

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS noticeList");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/notice/count/{noticeId}")
	public ResponseEntity<Map<String, Object>> cntNotice(@PathVariable int noticeId, @RequestBody Notice notice, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {

			int result = noticeService.cntUpNoticeView(new Notice(noticeId, null, null, null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS cntUpNoticeView");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	//
	@SuppressWarnings("unchecked")
	@PostMapping("/faq/register")
	public ResponseEntity<Map<String, Object>> regFaq(@RequestBody Faq faq, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			// jwtService.checkValid(req.getHeader("jwt-auth-token"));
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Faq addFaq = new Faq(-1, faq.getFaqTitle(), faq.getFaqContent(), "" + sdf.format(new Date()), 1, "" + tmpMap.get("userId"));
			faqService.intsertFaq(addFaq);

			System.out.println(addFaq);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS addFaq");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/faq/update/{faqId}")
	public ResponseEntity<Map<String, Object>> udtFaq(@PathVariable int faqId, @RequestBody Faq faq, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = faqService.updateFaq(new Faq(faqId, faq.getFaqTitle(), faq.getFaqContent(), null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS udtFaq");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/faq/delete/{faqId}")
	public ResponseEntity<Map<String, Object>> delFaq(@PathVariable int faqId, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("delFaq");
			Faq delFaq = new Faq(faqId, null, null, null, 0, null);
			faqService.deleteFaq(delFaq);

			System.out.println(delFaq);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS delFaq");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/faq/read/{faqId}")
	public ResponseEntity<Map<String, Object>> readFaq(@PathVariable int faqId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("faq read");

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS faqRead");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/faq/list")
	public ResponseEntity<Map<String, Object>> listFaq(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Faq> faqList = faqService.getAllFaqList();
			Collections.reverse(faqList);
			resultMap.put("faqList", faqList);

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS faqList");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/faq/count/{faqId}")
	public ResponseEntity<Map<String, Object>> cntFaq(@PathVariable int faqId, @RequestBody Faq faq, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {

			int result = faqService.cntUpFaqView(new Faq(faqId, null, null, null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS cntUpFaqView");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	//
	@SuppressWarnings("unchecked")
	@PostMapping("/qna/register")
	public ResponseEntity<Map<String, Object>> regQna(@RequestBody Qna qna, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Qna addQna = new Qna(-1, qna.getQnaTitle(), qna.getQnaContent(), "" + sdf.format(new Date()), 1, "" + tmpMap.get("userId"));
			qnaService.intsertQna(addQna);

			System.out.println(addQna);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS addQna");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/qna/update/{qnaId}")
	public ResponseEntity<Map<String, Object>> udtQna(@PathVariable int qnaId, @RequestBody Qna qna, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = qnaService.updateQna(new Qna(qnaId, qna.getQnaTitle(), qna.getQnaContent(), null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS udtdQna");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	//

	@PostMapping("/qna/delete/{qnaId}")
	public ResponseEntity<Map<String, Object>> delQna(@PathVariable int qnaId, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("delQna");
			Qna delQna = new Qna(qnaId, null, null, null, 0, null);
			qnaService.deleteQna(delQna);

			System.out.println(delQna);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS delQna");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/qna/read/{qnaId}")
	public ResponseEntity<Map<String, Object>> readQna(@PathVariable int qnaId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("qna read");

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS qnaRead");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/qna/list")
	public ResponseEntity<Map<String, Object>> listQna(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Qna> qnaList = qnaService.getAllQnaList();
			Collections.reverse(qnaList);
			resultMap.put("qnaList", qnaList);

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS qnaList");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/qna/count/{qnaId}")
	public ResponseEntity<Map<String, Object>> cntQna(@PathVariable int qnaId, @RequestBody Qna qna, HttpServletRequest req, HttpServletResponse res) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = qnaService.cntUpQnaView(new Qna(qnaId, null, null, null, -1, null));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS cntUpQnaView");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/qna/{qnaId}/register")
	public ResponseEntity<Map<String, Object>> regQnaComment(@PathVariable int qnaId, @RequestBody QnaComment qnaComment, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			System.out.println(-1 + "," + qnaComment.getQnaCommentContent() + "," + "" + tmpMap.get("userId") + "," + "" + sdf.format(new Date()) + "," + qnaId);
			int result = qnaCommentService.insertQnaComment(new QnaComment(-1, qnaComment.getQnaCommentContent(), "" + tmpMap.get("userId"), "" + sdf.format(new Date()), qnaId));

			System.out.println(result);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS add QnaComment");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/qna/{qnaId}/list")
	public ResponseEntity<Map<String, Object>> listQnaComment(@PathVariable int qnaId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<QnaComment> qnaCommentList = qnaCommentService.gwtAllQnaComment(new QnaComment(-1, null, null, null, qnaId));
			resultMap.put("qnaCommentList", qnaCommentList);

			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS qnaCommentList");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/qna/{qnaId}/update/{qnaCommentId}")
	public ResponseEntity<Map<String, Object>> QnaCommentUpdate(@PathVariable int qnaId, @PathVariable int qnaCommentId, @RequestBody QnaComment qnaCmnt, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("댓글 입력");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			qnaCmnt.setQnaCommentDate(sdf.format(today));
			qnaCmnt.setQnaId(qnaId);
			qnaCmnt.setQnaCommentId(qnaCommentId);
			System.out.println(qnaCmnt.toString());

			qnaCommentService.updateQnaComment(qnaCmnt);

			status = HttpStatus.OK;
			System.out.println("SUCS qnaCommentUpdate");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/qna/{qnaId}/delete/{qnaCommentId}")
	public ResponseEntity<Map<String, Object>> QnaCommentDelete(@PathVariable int qnaId, @PathVariable int qnaCommentId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("댓글 삭제");
			qnaCommentService.deleteQnaComment(qnaCommentId);

			status = HttpStatus.OK;
			System.out.println("SUCS qnaCommentUpdate");

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/book/list")
	public ResponseEntity<Map<String, Object>> getBookList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("북 리스트");
			List<Book> bookList = bookService.getAllBookList();
			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("books", bookList);
			System.out.println("SUCS getBookList");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/book/detail/{bookISBN}")
	public ResponseEntity<Map<String, Object>> getBookDetail(@PathVariable String bookISBN, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("북 디테일 시작");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			Book book = bookService.getBookByISBN(bookISBN);
			JSONObject item = naverBookAPIService.getBookByISBN(bookISBN);
			String description = (String) item.get("description");
			String link = (String) item.get("link");
			String userId = (String) tmpMap.get("userId");

			List<BookCommentUser> bookCmntList = bookCmntService.getBookComment(bookISBN, userId);

			if (book == null || book.getAuthor() == "" || book.getAuthor() == null) {
				resultMap.put("message", "그런 책은 없습니다");
				status = HttpStatus.OK;
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("book", book);
			resultMap.put("description", description);
			resultMap.put("link", link);
			resultMap.put("bookCmntList", bookCmntList);
			System.out.println("SUCS getBookDetail");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

//	@GetMapping("/book/detail/camera/{bookId}")
//	public ResponseEntity<Map<String, Object>> getBookDetailCamera(@PathVariable String bookId, HttpServletRequest req, HttpServletResponse res) {
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = null;
//
//		try {
//			System.out.println("북 디테일 테스트 시작");
//			System.out.println("책 Id : " + new String(bookId.getBytes("8859_1"), "UTF-8"));
//
//			String tmpBookId = new String(bookId.getBytes("8859_1"), "UTF-8");
//			Book book = bookService.getBookById(tmpBookId);
//			System.out.println(book.toString());
//
//			List<BookComment> bookCmntList = bookCmntService.getBookComment(bookId);
//			System.out.println(bookCmntList);
//			status = HttpStatus.OK;
//			resultMap.put("status", true);
//			resultMap.put("book", book);
//			resultMap.put("bookCmntList", bookCmntList);
//			System.out.println("SUCS getBookDetail");
//		} catch (Exception e) {
//			resultMap.put("message", e.getMessage());
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<Map<String, Object>>(resultMap, status);
//	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/register")
	public ResponseEntity<Map<String, Object>> addBook(@RequestBody Book book, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));

			String auth = (String) tmpMap.get("userRoleId");

			if (auth.equals("0")) {
				bookService.addBook(book);
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);

			System.out.println("SUCS addBook");
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/registerFile")
	public ResponseEntity<Map<String, Object>> addBookFile(@RequestBody Book book, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader br = new BufferedReader(null);

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));

			String auth = (String) tmpMap.get("userRoleId");

			if (auth.equals("0")) {
				bookService.addBook(book);
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);

			System.out.println("SUCS addBook");
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/book/regcomment")
	public ResponseEntity<Map<String, Object>> regBookComment(@RequestBody BookComment bc, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("북 댓글 테스트 시작");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			bc.setBookCommentDate(sdf.format(today));

			bookCmntService.insertBookComment(bc);
			status = HttpStatus.OK;
			resultMap.put("status", true);

			System.out.println("SUCS reqBookComment");
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/update/{bookCommentId}")
	public ResponseEntity<Map<String, Object>> updateBookComment(@PathVariable String bookCommentId, @RequestBody BookComment bc, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");

			if (bc.getBookCommentWriter().equals(userId)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
				Date today = new Date();
				bc.setBookCommentDate(sdf.format(today));
				System.out.println("bc : " + bc.toString());
				bc.setBookCommentId(Integer.parseInt(bookCommentId));
				bookCmntService.updateBookComment(bc);
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);

			System.out.println("SUCS updateBookComment");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/comment/delete/{bookCommentId}")
	public ResponseEntity<Map<String, Object>> deleteBookComment(@PathVariable String bookCommentId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");

			System.out.println("bookCommentId : " + bookCommentId);

			String cmntUserId = bookCmntService.getCmntUser(bookCommentId);
			System.out.println("cmntUserId : " + cmntUserId);
			System.out.println("userId : " + userId);

			if (cmntUserId.equals(userId)) {
				bookCmntService.deleteBookComment(bookCommentId);
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);
			System.out.println("SUCS deleteBookComment");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/updateInfo")
	public ResponseEntity<Map<String, Object>> getInfo(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			userService.updateInfo(user);
			User loginUser = userService.signin(user.getUserId(), user.getUserPw());
			
			if(user.getCategoryId() == null || user.getCategoryId().equals("")) {
				System.out.println("del interest Category");
				userService.deleteIC(user.getUserId());
			}else {
				System.out.println("udt interest Category");
				userService.deleteIC(user.getUserId());
				int size = user.getCategoryId().size();

				for (int i = 0; i < size; i++) {
					InterestCategory ic = new InterestCategory(user.getUserId(), user.getCategoryId().get(i));
					userService.insertIC(ic);
				} // 업데이트 끝
				loginUser.setCategoryId(user.getCategoryId());
			}
			
			String token = jwtService.create(loginUser);
			res.setHeader("token", token);
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("data", loginUser);

			System.out.println("SUCS UPDATE USER");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/rent/{bookId}")
	public ResponseEntity<Map<String, Object>> rentBook(@PathVariable String bookId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("rent book");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date fromDate = new Date();
			System.out.println("now Date : "+fromDate);
			Date toDate = new Date(fromDate.getTime() + 3 * (24 * 60 * 60 * 1000));
			BookRent bookRent = new BookRent(null, "" + sdf.format(fromDate), "" + sdf.format(toDate), 0, userId, new String(bookId.getBytes("8859_1"), "UTF-8"));
			int result;

			if ((result = bookRentService.insertBookRent(bookRent)) == -1) {
				resultMap.put("message", "이미 대여된 책입니다");
				System.out.println("FAIL rent book");
			} else if (result == -2) {
				resultMap.put("message", "한 권만 대여할 수 있습니다.");
				System.out.println("FAIL rent book");
			} else {
				Book book = bookService.getBookById(new String(bookId.getBytes("8859_1"), "UTF-8"));
				resultMap.put("book", book);
				resultMap.put("message", sdf.format(fromDate) + " ~ " + sdf.format(toDate) + " 까지 대여합니다");
				System.out.println("SUCS rent book");
			}
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/return/{bookId}")
	public ResponseEntity<Map<String, Object>> returnBook(@PathVariable String bookId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("return book");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			System.out.println(sdf.format(today));
			BookRent bookRent = new BookRent(sdf.format(today), 0, userId, new String(bookId.getBytes("8859_1"), "UTF-8"));

			int result;

			if ((result = bookRentService.deleteBookRent(bookRent)) == -2) {
				resultMap.put("message", "대여한 책이 없습니다");
				System.out.println("FAIL return book");
			} else if (result == -1) {
				resultMap.put("message", "이미 반납된 책입니다");
				System.out.println("FAIL return book");
			} else if(result == -3){
				resultMap.put("message", "대여한 책과 다릅니다.");
				System.out.println("FAIL return book");
			} else {
				resultMap.put("message", "반납 완료");
				System.out.println("SUCS return book");
			}
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/directRent/{bookId}")
	public ResponseEntity<Map<String, Object>> directRentBook(@PathVariable String bookId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("rent book");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date fromDate = new Date();
			Date toDate = new Date(fromDate.getTime() + 3 * (24 * 60 * 60 * 1000));
			BookRent bookRent = new BookRent(null, "" + sdf.format(fromDate), "" + sdf.format(toDate), 0, userId, new String(bookId));
			if(bookService.getBookById(bookId) == null) {
				resultMap.put("message", "등록되지 않은 도서번호 입니다.");
				System.out.println("FAIL rent book");
			}
			else {
				int result;
				if ((result = bookRentService.insertBookRent(bookRent)) == -1) {
					resultMap.put("message", "이미 대여된 책입니다");
					System.out.println("FAIL rent book");
				} else if (result == -2) {
					resultMap.put("message", "한 권만 대여할 수 있습니다.");
					System.out.println("FAIL rent book");
				} else {
					Book book = bookService.getBookById(new String(bookId));
					resultMap.put("book", book);
					resultMap.put("message", sdf.format(fromDate) + " ~ " + sdf.format(toDate) + " 까지 대여합니다");
					System.out.println("SUCS rent book");
				}
			}
			
			
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", "runtime error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/directReturn/{bookId}")
	public ResponseEntity<Map<String, Object>> directReturnBook(@PathVariable String bookId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("return book");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			BookRent bookRent = new BookRent(sdf.format(today), 0, userId, new String(bookId));
			
			if(bookService.getBookById(bookId) == null) {
				resultMap.put("message", "등록되지 않은 도서번호 입니다.");
				System.out.println("FAIL rent book");
			}
			else {
				int result;
				if ((result = bookRentService.deleteBookRent(bookRent)) == -2) {
					resultMap.put("message", "대여한 책이 없습니다");
					System.out.println("FAIL return book");
				} else if (result == -3) {
					resultMap.put("message", "대여한 책이 아닙니다");
					System.out.println("FAIL return book");
				} else if (result == -1) {
					resultMap.put("message", "이미 반납된 책입니다");
					System.out.println("FAIL return book");
				} else {
					resultMap.put("message", "반납 완료");
					System.out.println("SUCS return book");
				}
			}
			
			
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", "runtime error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/book/rentedbooks")
	public ResponseEntity<Map<String, Object>> getRentedBooks(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			BookRent bookRent = new BookRent(null, 0, userId, null);
			List<BookRent> rentedBooks = bookRentService.getBookRentByUserIdAll(bookRent);
			List<Book> rentedBookInfo = new ArrayList<>();
			for (int i = 0; i < rentedBooks.size(); i++) {
				rentedBookInfo.add(bookService.getBookById(rentedBooks.get(i).getBookId()));
			}
			resultMap.put("rentedBooks", rentedBooks);
			resultMap.put("rentedBookInfo", rentedBookInfo);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/main")
	public ResponseEntity<Map<String, Object>> main(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("main");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			BookRent brent = bookRentService.getBookIdByUserId(userId);

			if (brent != null) {
				Book book = bookService.getBookById(brent.getBookId());
				resultMap.put("rentFrom", brent.getRentFrom());
				resultMap.put("rentTo", brent.getRentTo());
				resultMap.put("book", book);
			} else {
				resultMap.put("message", "빌린 책이 없습니다");
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req, @RequestBody User user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			// 사용자에게 전달할 정보이다.
			// 보너스로 토큰에 담긴 정보도 전달해보자. 서버에서 토큰을 사용하는 방법임을 알 수 있다.
			resultMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			System.out.println(resultMap.get("userId"));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/chatbot")
	public ResponseEntity<Map<String, Object>> msg(@RequestBody Message message, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("chatbot test cont");
			IamOptions iamOptions = new IamOptions.Builder().apiKey("sGWrUWp6MpdjiEtpA2lwuCTlv77w5bcSKtJmfYELNM7r").build();
			Assistant service = new Assistant("2020-02-06", iamOptions);
			service.setEndPoint("https://api.kr-seo.assistant.watson.cloud.ibm.com/instances/595ef15f-d42a-4afe-82f8-b2bef104bd4e");
			String workspaceId = "570a110b-cf17-4ce0-8c0e-07c259317cb7";
			InputData input = new InputData.Builder(message.getDesc()).build();
			MessageOptions options = new MessageOptions.Builder(workspaceId).input(input).build();
			MessageResponse response = service.message(options).execute();
			String desc = response.getOutput().getText().get(0);
			StringTokenizer line = new StringTokenizer(response.getOutput().getText().get(1), ":");
			List<String> buttonName = new ArrayList<>();
			List<String> routeURL = new ArrayList<>();
			for (; line.hasMoreTokens();) {
				buttonName.add(line.nextToken());
			}
			line = new StringTokenizer(response.getOutput().getText().get(2), ":");
			for (; line.hasMoreTokens();) {
				routeURL.add(line.nextToken());
			}
			System.out.println(desc + "," + buttonName + "," + routeURL);
			Message message2 = new Message(desc, buttonName, routeURL);
			resultMap.put("message", message2);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/notwindow/list")
	public ResponseEntity<Map<String, Object>> notWindowList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			List<Notwindow> notWindowList = notwindowService.getNotwindowList(new Notwindow(-1, userId, null, null, null, null));
			resultMap.put("notWindowList", notWindowList);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS notWindowList");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/notwindow/register")
	public ResponseEntity<Map<String, Object>> insertNotWindow(@RequestBody Notwindow notwindow, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			String userId = (String) tmpMap.get("userId");
			notwindowService.intsertNotwindow(new Notwindow(-1, notwindow.getUserId(), notwindow.getContent(), userId, notwindow.getTitle(), sdf.format(new Date())));
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS insertNotWindow");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/notwindow/delete/{notwindowId}")
	public ResponseEntity<Map<String, Object>> deleteNotWindow(@PathVariable int notwindowId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			notwindowService.deleteNotwindow(new Notwindow(notwindowId, null, null, null, null, null));
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS deleteNotWindow");
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/recommend")
	public ResponseEntity<Map<String, Object>> bookRecommend(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			System.out.println("bookRecommend");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));

			String userId = (String) tmpMap.get("userId");
			List<Book> recommendBooks = userService.getBookListByCategoryId(new User(userId, null, null, null, null, null, 0, 0, null, null));
			resultMap.put("recommendBooks", recommendBooks);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS recommendBooks");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/book/request")
	public ResponseEntity<Map<String, Object>> requestBook(@RequestBody ReqBook reqBook, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("request book");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			String reqCity = (String) tmpMap.get("cityName");
			String userName = (String) tmpMap.get("userName");
			String userClass = (String) tmpMap.get("className");
			String userTeam = reqBook.getUserTeam();
			String bookName = reqBook.getBookName();
			String bookISBN = reqBook.getBookISBN();
			String publisher = reqBook.getPublisher();
			String bookPrice = "" + reqBook.getBookPrice();
			String reqReason = reqBook.getReqReason();
			int have = bookService.getBookByISBN(bookISBN) != null ? 1 : 0;
			String bookImg = reqBook.getBookImg();
			boolean delFlag = false;
			System.out.println("cont : "+userName);
			reqBookService.insertReqBook(new ReqBook(-1, reqCity, userClass, null, null, null, null, userName, userTeam, bookName, bookISBN, publisher, "" + bookPrice, reqReason, have, bookImg, delFlag, userId));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/book/request/list")
	public ResponseEntity<Map<String, Object>> getRequestBookList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("request book List");
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			String reqCity = (String) tmpMap.get("cityName");
			System.out.println(reqCity);
			String userName = (String) tmpMap.get("userName");
			System.out.println(userName);
			String userClass = (String) tmpMap.get("className");
			List<ReqBook> reqBooks = reqBookService.getAllReqBook(new ReqBook(-1, reqCity, userClass, null, null, null, null, userName, null, null, null, null, null, null, 0, null, false, userId));
			resultMap.put("reqBooks", reqBooks);
			System.out.println(reqBooks.get(0) + " size: " + reqBooks.size());
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/book/request/info/{bookName}")
	public ResponseEntity<Map<String, Object>> getRequestBookInof(@PathVariable String bookName, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<Book> books = naverBookAPIService.getBookByBookName(bookName);
			resultMap.put("bookInfoList", books);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/book/best")
	public ResponseEntity<Map<String, Object>> bestBook(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("best Book");
			List<Book> bestBook = bookService.getBestBookList();
			resultMap.put("bestBook", bestBook);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS get bestBook");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/book/rentable/{cityId}")
	public ResponseEntity<Map<String, Object>> rentableBook(@PathVariable String cityId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("rentable book");
			List<Book> rentableBook = bookService.getAllBookRentOkByCityId(cityId);
			resultMap.put("rentableBook", rentableBook);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS get rentable");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/book/category/{categoryId}")
	public ResponseEntity<Map<String, Object>> bookByCategory(@PathVariable String categoryId, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("category book");
			List<Book> categoryBook = bookService.getAllBookByCategoryId(categoryId);
			resultMap.put("categoryBook", categoryBook);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS get categoryBook");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/book/today")
	public ResponseEntity<Map<String, Object>> getTodayBook(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			System.out.println("today book");
			Book todayBook = bookService.getTodayBook();
			resultMap.put("todayBook", todayBook);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS get todayBook");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/book/rentable/count/{bookISBN}")
	public ResponseEntity<Map<String, Object>> getRentableBook(@PathVariable String bookISBN, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			
			System.out.println("count rentable book ISBN: "+bookISBN+" city: "+cityId);
			int cnt = bookService.cntRentableBookByISBNandCityId(new Book(null, bookISBN, null, -1, null, null, null, null, -1, null, cityId, null));
			int total = bookService.cntAllBookByISBNandCityId(new Book(null, bookISBN, null, -1, null, null, null, null, -1, null, cityId, null));
			System.out.println(total);
			String markable = total > 0 && cnt == 0 ? "TRUE" : "FALSE";
			String quantity = "";
			if(markable.equals("TRUE")) quantity = "0권";
			else {
				if(cnt != 0) {
					quantity = cnt+"권";
				}
				else {
					quantity = "미보유";
				}
			}
			resultMap.put("cntRentableBook", quantity);
			resultMap.put("markable", markable);
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS count rentable book");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/book/rent/mark/{bookISBN}")
	public ResponseEntity<Map<String, Object>> insertRentMark(@PathVariable String bookISBN, HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userId = (String) tmpMap.get("userId");
			
			RentMarkService.insertRentMark(new RentMark(bookISBN, cityId, userId));
			status = HttpStatus.ACCEPTED;
			System.out.println("SUCS add rentMark");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	

}