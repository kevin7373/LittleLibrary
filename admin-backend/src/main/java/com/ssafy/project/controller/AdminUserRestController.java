package com.ssafy.project.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.project.model.InterestCategory;
import com.ssafy.project.model.Notwindow;
import com.ssafy.project.model.RentCnt;
import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.User;
import com.ssafy.project.model.UserjRentjBook;
import com.ssafy.project.service.BookRentService;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.NotwindowService;
import com.ssafy.project.service.ReqBookService;
import com.ssafy.project.service.UserService;
import com.ssafy.project.service.UserjRentjBookService;

@RestController
@RequestMapping("/admin/api")
public class AdminUserRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReqBookService reqBookService;

	@Autowired
	private BookRentService bookRentService;

	@Autowired
	private UserjRentjBookService userjRentjBookService;

	@Autowired
	private NotwindowService notwindowService;

	@Value("${resources.user_location}")
	private String resourcesLocation;

	@Value("${resources.user_uri_path}")
	private String resourcesUriPath;

	@Value("${server.port}")
	private String serverPort;

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/user/list")
	public ResponseEntity<Map<String, Object>> getUerList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");

			List<User> userList = userService.getAllUserList(cityId, userRoleId);

			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUserImg() != null && !userList.get(i).getUserImg().equals("")) {
					userList.get(i).setUserImg("https://i02b206.p.ssafy.io/image" + userList.get(i).getUserImg());
				}
			}

			status = HttpStatus.OK;

			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject jo = new JSONObject();

			for (int i = 0; i < userList.size(); i++) {
				jo.put("ID", userList.get(i).getUserId());
				jo.put("이름", userList.get(i).getUserName());
				jo.put("학번", userList.get(i).getStudentId());
				jo.put("전화번호", userList.get(i).getPhone());
				jo.put("연체기간", userList.get(i).getOverduePeriod());
				jo.put("권한", userList.get(i).getUserRoleId());
				jo.put("관심분야", userList.get(i).getCategoryId());
				jo.put("지역", userList.get(i).getCityId());
				jsonArray.add(jo);
			}
			jsonObject.put("전체 회원 목록", jsonArray);
			String userListjson = jsonObject.toJSONString();

			resultMap.put("status", true);
			resultMap.put("userList", userList);
			resultMap.put("userListjson", userListjson);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/user/rent/list")
	public ResponseEntity<Map<String, Object>> getRentUerList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		Date today = new Date();

		try {
			List<UserjRentjBook> rentUserList = userjRentjBookService.getRentUserList();

			for (int i = 0; i < rentUserList.size(); i++) {
				if (rentUserList.get(i).getUserImg() != null && !rentUserList.get(i).getUserImg().equals("")) {
					rentUserList.get(i)
							.setUserImg("https://i02b206.p.ssafy.io/image" + rentUserList.get(i).getUserImg());
				}
			}

			for (int i = 0; i < rentUserList.size(); i++) {
				String bookId = rentUserList.get(i).getBookId();
				RentCnt rentCnt = new RentCnt(bookId, sdf.format(today));

				if (bookRentService.getRentBookCntByBookId(rentCnt) > 0) {
					rentUserList.get(i).setState("대여");
				} else if (bookRentService.getExpireBookCntByBookId(rentCnt) > 0) {
					rentUserList.get(i).setState("연체");
				}
			}

			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject jo = new JSONObject();
			for (int i = 0; i < rentUserList.size(); i++) {
				jo.put("ID", rentUserList.get(i).getUserId());
				jo.put("이름", rentUserList.get(i).getUserName());
				jo.put("학번", rentUserList.get(i).getStudentId());
				jo.put("전화번호", rentUserList.get(i).getPhone());
				jo.put("대여도서", rentUserList.get(i).getBookName());
				jo.put("대여 일", rentUserList.get(i).getRentFrom());
				jo.put("반납예정일", rentUserList.get(i).getRentTo());
				jo.put("상태", rentUserList.get(i).getState());
				jsonArray.add(jo);
			}
			jsonObject.put("대여 회원 목록", jsonArray);
			String rentUserListjson = jsonObject.toJSONString();

			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("rentUserList", rentUserList);
			resultMap.put("rentUserListjson", rentUserListjson);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/user/profile/{userId}")
	public ResponseEntity<Map<String, Object>> getProfile(@PathVariable String userId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();

		HttpStatus status = null;

		try {
			User profile = new User();

			profile = userService.getUserInfoByUserId(userId);

			if (profile.getUserImg() != null && !profile.getUserImg().equals("")) {
				profile.setUserImg("https://i02b206.p.ssafy.io/image" + profile.getUserImg());
			}
			List<String> categoryId = userService.getCateList(userId);

			if (!categoryId.isEmpty() && categoryId.size() > 0) {
				profile.setCategoryId(categoryId);
			}
			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("profile", profile);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/user/checkstudentid/{studentId}")
	public ResponseEntity<Map<String, Object>> checkStudentId(@PathVariable String studentId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");

			if (userService.checkStudentId(studentId, userId)) {
				status = HttpStatus.OK;
				resultMap.put("status", true);
				resultMap.put("message", "정상");
			} else {
				resultMap.put("message", "중복");
				status = HttpStatus.OK;
			}
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/user/updatepassword")
	public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody JSONObject object, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			JsonParser parser = new JsonParser();
			JsonObject objectJson = (JsonObject) parser.parse(object.get("object").toString());
			Gson gson = new Gson();
			User user = gson.fromJson(objectJson.get("user"), User.class);
			String newUserPw = gson.fromJson(objectJson.get("newUserPw"), String.class);

			if (userService.updatePassword(user, newUserPw)) {
				resultMap.put("message", "정상");
				User loginUser = userService.signin(user.getUserId(), newUserPw);
				String token = jwtService.create(loginUser);
				res.setHeader("token", token);
			} else {
				resultMap.put("message", "비밀번호 오류");
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/user/update")
	public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody User user, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		String[] extensionArr = { ".jpg", ".jpeg", ".png", ".gif", ".JPG", ".JPEG", ".PNG", ".GIF" };

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			List<String> tmpUserImg = new ArrayList<>();
			User loginUser = new User();

			if (!userId.equals(user.getUserId())) {
				if (user.getUserImg() == null) {
					user.setUserImg(null);
				} else {
					tmpUserImg = Arrays.asList(user.getUserImg().split("/"));
					user.setUserImg("/" + tmpUserImg.get(tmpUserImg.size() - 1));
				}
				userService.updateInfo(user);
				res.setHeader("token", req.getHeader("token"));
			} else {
				if (!userService.getUserPw(user)) {
					status = HttpStatus.OK;
					resultMap.put("message", "비밀번호 오류");
					return new ResponseEntity<Map<String, Object>>(resultMap, status);
				}

				for (int i = 0; i < extensionArr.length; i++) {
					File oldFile = new File("C:" + "/" + user.getStudentId() + extensionArr[i]);

					if (oldFile.exists()) {
						oldFile.delete();
						break;
					}
				}

				if (user.getUserImg() == null) {
					user.setUserImg("");
				} else {
					tmpUserImg = Arrays.asList(user.getUserImg().split("/"));
					user.setUserImg("/" + tmpUserImg.get(tmpUserImg.size() - 1));
				}
				userService.updateInfo(user);
				loginUser = userService.signin(user.getUserId(), user.getUserPw());

				if (user.getCategoryId() != null && !user.getCategoryId().isEmpty()) {
					userService.deleteIC(user.getUserId());
					int size = user.getCategoryId().size();

					for (int i = 0; i < size; i++) {
						InterestCategory ic = new InterestCategory(user.getUserId(), user.getCategoryId().get(i));
						userService.updateIC(ic);
					} // 업데이트 끝
					loginUser.setCategoryId(user.getCategoryId());
				}
				String token = jwtService.create(loginUser);
				res.setHeader("token", token);
			}

			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/user/updatewithfile")
	public ResponseEntity<Map<String, Object>> updateProfileWithFile(@RequestBody MultipartFile file,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String[] extensionArr = { ".jpg", ".jpeg", ".png", ".gif", ".JPG", ".JPEG", ".PNG", ".GIF" };

		try {
			User tmpUser = new User();

			tmpUser.setUserId(req.getParameter("userId").toString());
			tmpUser.setUserPw(req.getParameter("userPw").toString());

			if (!userService.getUserPw(tmpUser)) {
				status = HttpStatus.OK;
				resultMap.put("message", "비밀번호 오류");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			
			for (int i = 0; i < extensionArr.length; i++) {
				File oldFile = new File(resourcesLocation + "/" + req.getParameter("studentId") + extensionArr[i]);

				if (oldFile.exists()) {
					oldFile.delete();
					break;
				}
			}
			String fileType = file.getContentType();
			StringTokenizer st = new StringTokenizer(fileType, "/");
			st.nextToken();
			String fileExtension = st.nextToken();
			file.transferTo(new File(resourcesLocation + "/" + req.getParameter("studentId") + "." + fileExtension));
			User user = new User();
			user.setUserImg("/" + req.getParameter("studentId") + "." + fileExtension);
			user.setUserId(req.getParameter("userId"));
			user.setUserPw(req.getParameter("userPw"));
			user.setUserName(req.getParameter("userName"));
			user.setStudentId(req.getParameter("studentId"));
			user.setEmail(req.getParameter("email"));
			user.setPhone(req.getParameter("phone"));
			user.setCityId(req.getParameter("cityId"));
			user.setClassId(req.getParameter("classId"));
			user.setOverduePeriod(Integer.parseInt(req.getParameter("overduePeriod")));
			user.setUserRoleId(req.getParameter("userRoleId"));
			
			if (req.getParameter("categoryId") != null && !req.getParameter("categoryId").equals("null") &&!req.getParameter("categoryId").equals("")) {
				List<String> cateList = Arrays.asList(req.getParameter("categoryId").split(","));

				Collections.sort(cateList);
				user.setCategoryId(cateList);
			}
			userService.updateInfo(user);
			User loginUser = userService.signin(user.getUserId(), user.getUserPw());

			if (user.getCategoryId() != null && !user.getCategoryId().isEmpty()) {
				userService.deleteIC(user.getUserId());
				int size = user.getCategoryId().size();

				for (int i = 0; i < size; i++) {
					InterestCategory ic = new InterestCategory(user.getUserId(), user.getCategoryId().get(i));
					userService.updateIC(ic);
				} // 업데이트 끝
				loginUser.setCategoryId(user.getCategoryId());
			}
			String token = jwtService.create(loginUser);
			res.setHeader("token", token);
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (IOException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/user/require/list")
	public ResponseEntity<Map<String, Object>> getRequireUserList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");
			String reqCity = "서울";
			if (cityId.equals("1")) {
				reqCity = "대전";
			} else if (cityId.equals("2")) {
				reqCity = "구미";
			} else if (cityId.equals("3")) {
				reqCity = "광주";
			}
			List<ReqBook> reqUserList = reqBookService.getAllReqBookList(reqCity, userRoleId);
			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("reqUserList", reqUserList);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/notwindow/register")
	public ResponseEntity<Map<String, Object>> insertNotWindow(@RequestBody JSONObject object, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			JsonParser parser = new JsonParser();
			JsonObject objectJson = (JsonObject) parser.parse(object.get("object").toString());
			Gson gson = new Gson();
			Notwindow notwindow = gson.fromJson(objectJson.get("notwindow"), Notwindow.class);
			List<String> receivers = gson.fromJson(objectJson.get("receiver"), List.class);

			notwindow.setNotwindowId(-1);
			notwindow.setWriteDate(sdf.format(today));

			for (int i = 0; i < receivers.size(); i++) {
				notwindow.setUserId(receivers.get(i));
				notwindowService.intsertNotwindow(notwindow);
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/notwindow/register/accept")
	public ResponseEntity<Map<String, Object>> insertNotWindowAccept(@RequestBody JSONObject object,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			JsonParser parser = new JsonParser();
			JsonObject objectJson = (JsonObject) parser.parse(object.get("object").toString());
			Gson gson = new Gson();
			Notwindow notwindow = gson.fromJson(objectJson.get("notwindow"), Notwindow.class);
			ReqBook[] array = gson.fromJson(objectJson.get("receiver"), ReqBook[].class);
			List<ReqBook> receivers = Arrays.asList(array);

			notwindow.setNotwindowId(-1);
			notwindow.setWriteDate(sdf.format(today));

			for (int i = 0; i < receivers.size(); i++) {
				notwindow.setUserId(receivers.get(i).getUserId());
				String tmpTitle = "[" + notwindow.getTitle() + "] " + receivers.get(i).getBookName();
				notwindow.setTitle(tmpTitle);

				notwindowService.intsertNotwindow(notwindow);
				tmpTitle = "";
				notwindow.setTitle("승인");
				String reqId = receivers.get(i).getReqId();
				reqBookService.insertDelFlag(reqId);
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/notwindow/register/reject")
	public ResponseEntity<Map<String, Object>> insertNotWindowReject(@RequestBody JSONObject object,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();
			JsonParser parser = new JsonParser();
			JsonObject objectJson = (JsonObject) parser.parse(object.get("object").toString());
			Gson gson = new Gson();
			Notwindow notwindow = gson.fromJson(objectJson.get("notwindow"), Notwindow.class);
			ReqBook[] array = gson.fromJson(objectJson.get("receiver"), ReqBook[].class);
			List<ReqBook> receivers = Arrays.asList(array);

			notwindow.setNotwindowId(-1);
			notwindow.setWriteDate(sdf.format(today));

			for (int i = 0; i < receivers.size(); i++) {
				notwindow.setUserId(receivers.get(i).getUserId());
				String tmpTitle = "[" + notwindow.getTitle() + "] " + receivers.get(i).getBookName();
				notwindow.setTitle(tmpTitle);

				notwindowService.intsertNotwindow(notwindow);
				tmpTitle = "";
				notwindow.setTitle("반려");
				String reqId = receivers.get(i).getReqId();
				reqBookService.insertDelFlag(reqId);
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/notwindow/delete")
	public ResponseEntity<Map<String, Object>> deleteNotwindow(@RequestBody List<Notwindow> notwindowList,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			for (int i = 0; i < notwindowList.size(); i++) {
				notwindowService.deleteNotwindow(notwindowList.get(i));
			}
			status = HttpStatus.OK;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/notwindow/list")
	public ResponseEntity<Map<String, Object>> notWindowList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String userId = (String) tmpMap.get("userId");
			List<Notwindow> notWindowList = notwindowService
					.getNotwindowList(new Notwindow(-1, userId, null, null, null, null));
			resultMap.put("notWindowList", notWindowList);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}