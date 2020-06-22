package com.ssafy.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookjRent;
import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.User;
import com.ssafy.project.service.BookService;
import com.ssafy.project.service.BookjRentService;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.ReqBookService;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("/admin/api")
public class AdminMainRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ReqBookService reqBookService;

	@Autowired
	private BookjRentService bookjRentService;

	@PostMapping("/admin/user/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = userService.register(user.getUserId(), user.getUserPw(), user.getStudentId(), user.getEmail(),
					user.getUserName(), user.getPhone(), 0, 0, "1", user.getCityId());

			if (result <= 0) {
				status = HttpStatus.OK;
				resultMap.put("msg", "등록 실패");
				new ResponseEntity<Map<String, Object>>(resultMap, status);
			}

			int size = user.getCategoryId().size();

			for (int i = 0; i < size; i++) {
				InterestCategory ic = new InterestCategory(user.getUserId(), user.getCategoryId().get(i));
				userService.insertIC(ic);
			}
			resultMap.put("status", true);
			status = HttpStatus.OK;

			resultMap.put("status", status);

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/user/signin")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String failMsg = "로그인에 실패하였습니다.";
		try {
			User loginUser = userService.signin(user.getUserId(), user.getUserPw());

			if (loginUser.getUserRoleId().equals("0")) {
				status = HttpStatus.OK;
				resultMap.put("status", true);
				resultMap.put("message", "관리자 권한이 아닙니다");
				new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			List<String> cateList = new ArrayList<>();
			cateList.addAll(userService.getCateList(user.getUserId()));

			if (cateList.size() > 0) {
				loginUser.setCategoryId(cateList);
			}
			// 로그인 성공했다면 토큰을 생성한다.
			String token = jwtService.create(loginUser);
			// 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아주자.
			res.setHeader("token", token);
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
			status = HttpStatus.OK;

		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			resultMap.put("failMsg", failMsg);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/user/checkId")
	public ResponseEntity<Map<String, Object>> checkId(@RequestBody String userId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			int result = userService.checkId(userId);

			resultMap.put("result", result);
			resultMap.put("status", true);

			status = HttpStatus.OK;

		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/main")
	public ResponseEntity<Map<String, Object>> bookCnt(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			String tday = sdf.format(today);

			int bookCnt = bookService.getAllBookCnt(cityId, userRoleId);
			int rentCnt = bookjRentService.getRentCnt(cityId, tday, userRoleId);
			int expireCnt = bookjRentService.getExpireBookCnt(cityId, tday, userRoleId);
			int currentBookCnt = bookCnt - rentCnt - expireCnt;

			if (!userRoleId.equals("1")) {
				double realReturn = bookjRentService.getRealReturnCnt(cityId);
				double todayReturn = bookjRentService.getTodayReturnCnt(cityId);
				double returnRate = realReturn / (todayReturn + expireCnt);
				double rentRate = rentCnt / bookCnt;
				double expireRate = rentCnt != 0 ? expireCnt / rentCnt : 0;
				resultMap.put("rentRate", rentRate);
				resultMap.put("returnRate", returnRate);
				resultMap.put("expireRate", expireRate);
				resultMap.put("returnStatus",
						(Integer.toString((int) realReturn) + "/" + Integer.toString((int) (todayReturn + expireCnt))));
			} else if (userRoleId.equals("1")) {
				ArrayList<Double> rtRateList = new ArrayList<>();

				for (int i = 0; i < 4; i++) {
					String tmpCityId = "" + i;
					double realRt = bookjRentService.getRealReturnCnt(tmpCityId);
					double tdRt = bookjRentService.getTodayReturnCnt(tmpCityId);
					double epCnt = bookjRentService.getEpBookCnt(tmpCityId, tday);
					double rtRate = 0;

					if (tdRt + epCnt == 0) {
						rtRate = 1.0;
					} else {
						rtRate = realRt / (tdRt + epCnt);
					}
					rtRateList.add(rtRate);
				}
				resultMap.put("rtRateList", rtRateList);
			}
			List<Book> bestBookList = bookService.getBestBookList(cityId, userRoleId);
			List<BookjRent> rentCntListByPeriod = bookjRentService.getRentCntListByPeriod(cityId, userRoleId);
			String reqCity = "서울";

			if (cityId.equals("1")) {
				reqCity = "대전";
			} else if (cityId.equals("2")) {
				reqCity = "구미";
			} else if (cityId.equals("3")) {
				reqCity = "광주";
			}
			List<ReqBook> reqBookMain = reqBookService.getReqBookMain(reqCity, userRoleId);

			status = HttpStatus.OK;
			resultMap.put("bookCnt", bookCnt);
			resultMap.put("rentCnt", rentCnt);
			resultMap.put("expireCnt", expireCnt);
			resultMap.put("currentBookCnt", currentBookCnt);
			resultMap.put("status", true);
			resultMap.put("bestBookList", bestBookList);
			resultMap.put("rentCntListByPeriod", rentCntListByPeriod);
			resultMap.put("reqBookMain", reqBookMain);

		} catch (RuntimeException e) {

			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}