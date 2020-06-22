package com.ssafy.project.controller;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.project.model.Book;
import com.ssafy.project.model.BookDetail;
import com.ssafy.project.model.BookjRent;
import com.ssafy.project.model.RentCnt;
import com.ssafy.project.model.ReqBook;
import com.ssafy.project.model.ReqBookDetail;
import com.ssafy.project.model.User;
import com.ssafy.project.service.BookRentService;
import com.ssafy.project.service.BookService;
import com.ssafy.project.service.BookjRentService;
import com.ssafy.project.service.CategoryService;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.NaverBookAPIService;
import com.ssafy.project.service.ReqBookService;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("/admin/api")
public class AdminBookRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRentService bookRentService;

	@Autowired
	private BookjRentService bookjRentService;

	@Autowired
	private ReqBookService reqBookService;

	@Autowired
	private NaverBookAPIService naverBookAPIService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Value("${resources.user_location}")
	private String resourcesLocation;

	@Value("${resources.user_uri_path}")
	private String resourcesUriPath;

	@Value("${server.port}")
	private String serverPort;

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/list")
	public ResponseEntity<Map<String, Object>> getBookList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();

		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");
			List<Book> bookList = bookService.getAllBookList(cityId, userRoleId);
			status = HttpStatus.OK;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date today = new Date();

			for (int i = 0; i < bookList.size(); i++) {
				String bookId = bookList.get(i).getBookId();
				RentCnt rentCnt = new RentCnt(bookId, sdf.format(today));

				if (bookRentService.getRentBookCntByBookId(rentCnt) > 0) {// 남은 날짜 추가해서 보내기
					String brId = bookRentService.getBookRentId(bookId);
					String td = "" + sdf.format(today);
					String dday = "" + bookRentService.getRemainDay(brId, td);
					bookList.get(i).setState("대여 D-" + dday);
				} else if (bookRentService.getExpireBookCntByBookId(rentCnt) > 0) { // 연체날짜 추가해서 보내기
					String brId = bookRentService.getBookRentId(bookId);
					String td = "" + sdf.format(today);
					String dday = "" + bookRentService.getExpiredDay(brId, td);
					String eday = dday.substring(1);
					bookList.get(i).setState("연체 D+" + eday);
				} else {
					bookList.get(i).setState("보유");
				}
			}
			resultMap.put("status", true);
			resultMap.put("books", bookList);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/rent/list")
	public ResponseEntity<Map<String, Object>> getRentBookList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		Date today = new Date();

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");
			List<BookjRent> rentBookList = bookjRentService.getAllRentBookList(cityId, userRoleId);
			List<BookjRent> returnBookList = bookjRentService.getAllReturnBookList(cityId, userRoleId);
			List<BookjRent> expiredBookList = bookjRentService.getAllExpiredBookList(cityId, userRoleId);

			for (int i = 0; i < rentBookList.size(); i++) {
				String bookId = rentBookList.get(i).getBookId();
				RentCnt rentCnt = new RentCnt(bookId, sdf.format(today));

				if (bookRentService.getRentBookCntByBookId(rentCnt) > 0) {
					String brId = bookRentService.getBookRentId(bookId);
					String td = "" + sdf.format(today);
					String dday = "" + bookRentService.getRemainDay(brId, td);
					rentBookList.get(i).setState("대여 D-" + dday);
				} else if (bookRentService.getExpireBookCntByBookId(rentCnt) > 0) {
					String brId = bookRentService.getBookRentId(bookId);
					String td = "" + sdf.format(today);
					String dday = "" + bookRentService.getExpiredDay(brId, td);
					String eday = dday.substring(1);
					rentBookList.get(i).setState("연체 D+" + eday);
				}
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("rentBookList", rentBookList);
			resultMap.put("returnBookList", returnBookList);
			resultMap.put("expiredBookList", expiredBookList);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/return/list")
	public ResponseEntity<Map<String, Object>> getReturnBookList(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");
			String userRoleId = (String) tmpMap.get("userRoleId");
			List<BookjRent> returnBookList = bookjRentService.getAllReturnBookList(cityId, userRoleId);

			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("returnBookList", returnBookList);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/book/detail/{bookId}")
	public ResponseEntity<Map<String, Object>> getBookDetailByBookId(@PathVariable String bookId,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		Date today = new Date();

		try {
			BookDetail bookDetail = bookService.getBookDetailById(bookId);

			if (bookDetail == null || bookDetail.getBookId().equals("") || bookDetail.getBookId() == null) {
				Book bookInfo = bookService.getBookById(bookId);

				bookInfo.setState("보유");
				List<Book> bookInfos = new ArrayList<>();
				bookInfos.add(bookInfo);
				resultMap.put("bookDetail", bookInfos);
				status = HttpStatus.OK;
				resultMap.put("status", status);
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}

			if (bookDetail.getRealReturn() != null && !bookDetail.getRealReturn().equals("")) {
				bookDetail.setState("보유");
			} else {
				if (bookDetail.getRentTo().substring(0, 10).compareTo(sdf.format(today)) >= 0) {
					bookDetail.setState("대여");
				} else if (bookDetail.getRentTo().substring(0, 10).compareTo(sdf.format(today)) < 0) {
					bookDetail.setState("연체");
				}
			}
			List<BookDetail> bookDetails = new ArrayList<>();
			bookDetails.add(bookDetail);

			String userId = bookRentService.getUserIdByBookId(bookId);
			User user = userService.getUserInfoByUserId(userId);

			InetAddress local;
			local = InetAddress.getLocalHost();
			String serverAddress = local.getHostAddress() + ":" + serverPort;
			String imgUrl = "http://" + serverAddress + resourcesUriPath;

			if (user.getUserImg() != null && !user.getUserImg().equals("")) {
				user.setUserImg("https://i02b206.p.ssafy.io/image" + user.getUserImg());
			}

			List<String> cateList = new ArrayList<>();
			cateList.addAll(userService.getCateListByUserId(userId));

			StringBuilder userCategory = new StringBuilder();

			for (int i = 0; i < cateList.size(); i++) {
				if (i == cateList.size() - 1) {
					userCategory.append(cateList.get(i));
				} else {
					userCategory.append(cateList.get(i)).append(", ");
				}
			}
			user.setCategoryName(userCategory.toString());

			List<User> users = new ArrayList<>();
			users.add(user);

			status = HttpStatus.OK;
			resultMap.put("userInfo", users);
			resultMap.put("bookDetail", bookDetails);
			resultMap.put("status", status);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/admin/book/info/{bookId}")
	public ResponseEntity<Map<String, Object>> getBookInfoByBookId(@PathVariable String bookId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Book bookDetail = bookService.getBookById(bookId);

			status = HttpStatus.OK;
			resultMap.put("bookDetail", bookDetail);
			resultMap.put("status", status);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/checkbookid")
	public ResponseEntity<Map<String, Object>> checkBookId(@RequestBody JSONObject object, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			JsonParser parser = new JsonParser();
			JsonObject objectJson = (JsonObject) parser.parse(object.get("object").toString());
			Gson gson = new Gson();
			String editBookId = gson.fromJson(objectJson.get("editBookId"), String.class);
			String cityId = gson.fromJson(objectJson.get("cityId"), String.class);

			if (bookService.checkBookId(editBookId, cityId)) {
				status = HttpStatus.OK;
				resultMap.put("status", true);
				resultMap.put("message", "정상");
			} else {
				resultMap.put("message", "중복");
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/update")
	public ResponseEntity<Map<String, Object>> updateBookByBookId(@RequestBody Book book, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			status = HttpStatus.OK;
			resultMap.put("status", status);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/delete")
	public ResponseEntity<Map<String, Object>> deleteBookByBookId(@RequestBody List<Book> books, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			List<String> deleteRejectList = new ArrayList<>();
			List<String> deleteRejectBookId = new ArrayList<>();

			for (int i = 0; i < books.size(); i++) {
				String bookId = books.get(i).getBookId();

				if (bookRentService.isNowRent(bookId) > 0) {
					deleteRejectBookId.add(bookId);
					deleteRejectList.add(bookId + "은/는 현재 대여 중입니다.");
					continue;
				} else if (bookRentService.isNowExpire(bookId) > 0) {
					deleteRejectBookId.add(bookId);
					deleteRejectList.add(bookId + "은/는 현재 연체 중입니다.");
					continue;
				} else {
					bookRentService.deleteBookRentHistory(bookId);
					bookService.deleteBook(bookId);
				}
			}
			status = HttpStatus.OK;
			resultMap.put("status", status);
			resultMap.put("deleteRejectList", deleteRejectList);
			resultMap.put("deleteRejectBookId", deleteRejectBookId);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/return/detail/{bookRentId}")
	public ResponseEntity<Map<String, Object>> getReturnBookDetailByBookRentId(@PathVariable String bookRentId,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String cityId = (String) tmpMap.get("cityId");

			BookDetail bookDetail = bookService.getReturnBookDetailByBookRentId(bookRentId, cityId);

			if (bookDetail.getRentTo().substring(0, 10).compareTo(bookDetail.getRealReturn().substring(0, 10)) >= 0) {
				bookDetail.setState("정상 반납");
			} else {
				bookDetail.setState("연체 반납");
			}
			List<BookDetail> bookDetails = new ArrayList<>();
			bookDetails.add(bookDetail);
			String userId = bookRentService.getUserIdByBookId(bookDetail.getBookId());
			User user = userService.getUserInfoByUserId(userId);

			InetAddress local;
			local = InetAddress.getLocalHost();
			String serverAddress = local.getHostAddress() + ":" + serverPort;
			String imgUrl = "http://" + serverAddress + resourcesUriPath;

			if (user.getUserImg() != null && !user.getUserImg().equals("")) {
				user.setUserImg("https://i02b206.p.ssafy.io/image" + user.getUserImg());
			}

			List<String> cateList = new ArrayList<>();
			cateList.addAll(userService.getCateListByUserId(userId));

			StringBuilder userCategory = new StringBuilder();

			for (int i = 0; i < cateList.size(); i++) {
				if (i == cateList.size() - 1) {
					userCategory.append(cateList.get(i));
				} else {
					userCategory.append(cateList.get(i)).append(", ");
				}
			}
			user.setCategoryName(userCategory.toString());

			List<User> users = new ArrayList<>();
			users.add(user);

			status = HttpStatus.OK;
			resultMap.put("userInfo", users);
			resultMap.put("bookDetail", bookDetails);
			resultMap.put("status", status);
		} catch (Exception e) {

			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/delete/{bookId}")
	public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable String bookId, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		bookService.deleteBook(bookId);
		bookRentService.deleteBookRentHistory(bookId);

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/deleteBooks")
	public ResponseEntity<Map<String, Object>> deleteBook(@RequestBody List<Book> books, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String bookId = "";

		for (int i = 0; i < books.size(); i++) {
			bookId = books.get(i).getBookId();
			bookService.deleteBook(bookId);
			bookRentService.deleteBookRentHistory(bookId);
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/book/register")
	public ResponseEntity<Map<String, Object>> addBook(@RequestBody Book book, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;

		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			String auth = (String) tmpMap.get("userRoleId");
			JSONObject newBook = new JSONObject();
			String errorMsg = "";
			
			if (bookService.getBookById(book.getBookId()) != null) {
				errorMsg = book.getBookId() + "는 중복된 도서 코드입니다.";
				resultMap.put("errorMsg", errorMsg);
				status = HttpStatus.OK;
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			newBook = naverBookAPIService.getBookByISBN(book.getBookISBN());
			
			boolean isbnFlag = false;

			if (newBook == null || newBook.size() == 0) {
				String tmpIsbn = book.getBookISBN();
				errorMsg = tmpIsbn + " ISBN을 확인해 주세요";
				isbnFlag = true;
				resultMap.put("errorMsg", errorMsg);
				status = HttpStatus.OK;
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			String bookImg = ((String) newBook.get("image")).replace("\\/", "/");
			book.setBookImg(bookImg);
			book.setAuthor((String) newBook.get("author"));
			book.setBookPrice(Integer.parseInt((String) newBook.get("price")));
			book.setBookName((String) newBook.get("title"));
			StringBuffer sb = new StringBuffer((String) newBook.get("pubdate"));
			sb.insert(4, "-");
			sb.insert(7, "-");
			book.setPublisher((String) newBook.get("publisher"));
			book.setBookSummary((String) newBook.get("description"));
			book.setPublishDate(sb.toString());
			book.setDelFlag(0);

//			String inputBid = book.getBookId();
//			String inputCn = book.getCityId();
//			boolean bookIdFlag = false;
//			List<String> existBookCityId = bookService.getexistBookCityIdList(inputBid);
//
//			if (existBookCityId.size() > 0) {
//				bookIdFlag = true;
//			}
//
//			if (bookIdFlag) {
//				boolean sameCityBookId = false;
//				for (int j = 0; j < existBookCityId.size(); j++) {
//					if (inputCn.equals(existBookCityId.get(j))) {
//						sameCityBookId = true;
//						break;
//					}
//				}
//
//				if (sameCityBookId) {
//					String tmpCityName = "";
//					if (inputCn.equals("0")) {
//						tmpCityName = "서울";
//					} else if (inputCn.equals("1")) {
//						tmpCityName = "대전";
//					} else if (inputCn.equals("2")) {
//						tmpCityName = "구미";
//					} else if (inputCn.equals("3")) {
//						tmpCityName = "광주";
//					}
//					errorMsg = tmpCityName + "지역에 " + inputBid + "가 이미 사용중입니다.";
//				}
//			}

//			if (!bookIdFlag && !isbnFlag) {
//				bookService.addBook(book);
//			}
			bookService.addBook(book);
			String registerMsg = "정상적으로 등록이 완료되었습니다";
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("registerMsg", registerMsg);
			resultMap.put("errorMsg", errorMsg);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/admin/book/registerFile")
	public ResponseEntity<Map<String, Object>> addBookFile(@RequestBody JSONObject object, HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String[] cateArr = { "웹 / 모바일", "머신러닝", "DS / 빅데이터", "블록체인", "IoT", "프로그래밍 언어", "알고리즘", "애자일", "면접", "CI / CD",
				"VueJS", "ReactJS", "데이터베이스", "기타" };
		String[] cityArr = { "서울", "대전", "구미", "광주" };
		String[] classArr = { "1반", "2반", "3반", "4반", "5반", "6반", "7반", "8반", "9반", "10반" };

		try {
			String tmpBook = object.toString().replaceAll("도서 아이디", "bookId").replaceAll("ISBN 코드", "bookISBN")
					.replaceAll("카테고리", "categoryName").replaceAll("지역", "cityName").replaceAll("\"반", "\"className");
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(tmpBook);
			JSONArray books = (JSONArray) jsonObj.get("book");
			ArrayList<Integer> successList = new ArrayList<>();
			ArrayList<String> sBidList = new ArrayList<>();
			ArrayList<Integer> remainList = new ArrayList<>();
			ArrayList<String> reasonList = new ArrayList<>();

			for (int i = 0; i < books.size(); i++) {
				boolean cateFlag = false;
				boolean cityFlag = false;
				boolean classFlag = false;
				boolean bookIdFlag = false;
				JSONObject item = new JSONObject();
				item = (JSONObject) books.get(i);
				String bookISBN = item.get("bookISBN").toString().trim();
				JSONObject newBook = new JSONObject();

				String inputBookId = item.get("bookId").toString();
				
				if (bookService.getBookById(inputBookId) != null) {
					remainList.add(i + 1);
					reasonList.add(inputBookId + "는 중복된 도서 코드입니다");
					continue;
				}

				Book book = bookService.getBookByISBN(bookISBN);

				if (book == null) {
					book = new Book();
					newBook = naverBookAPIService.getBookByISBN(bookISBN);
					Thread.sleep(110);

					if (newBook == null || newBook.size() == 0) {
						remainList.add(i + 1);
						reasonList.add(bookISBN + "은(는) 잘못된 방식의 입력입니다." + " ISBN을 확인해주세요");
						continue;
					}
					String bookImg = ((String) newBook.get("image")).replace("\\/", "/");
					book.setBookImg(bookImg);
					book.setAuthor((String) newBook.get("author"));
					book.setBookPrice(Integer.parseInt((String) newBook.get("price")));
					book.setBookName((String) newBook.get("title"));
					StringBuffer sb = new StringBuffer((String) newBook.get("pubdate"));
					sb.insert(4, "-");
					sb.insert(7, "-");
					book.setPublisher((String) newBook.get("publisher"));
					book.setBookSummary((String) newBook.get("description"));
					book.setPublishDate(sb.toString());
				}

				book.setBookId(item.get("bookId").toString());
				book.setBookISBN(bookISBN);
				String inputCate = item.get("categoryName").toString();

				for (int j = 0; j < cateArr.length; j++) {
					if (inputCate.equals(cateArr[j])) {
						cateFlag = true;
						break;
					}
				}

				if (!cateFlag) {
					remainList.add(i + 1);
					reasonList.add(inputCate + "은(는) 잘못된 방식의 입력입니다." + " 카테고리 이름을 확인해주세요");
					continue;
				}
				book.setCategoryName(item.get("categoryName").toString());
				String categoryId = categoryService.getCategoryIdByCategoryName(item.get("categoryName").toString());
				book.setCategoryId(categoryId);

				String inputCn = item.get("cityName").toString();

				for (int j = 0; j < cityArr.length; j++) {
					if (inputCn.equals(cityArr[j])) {
						cityFlag = true;
						break;
					}
				}

				if (!cityFlag) {
					remainList.add(i + 1);
					reasonList.add(inputCn + "은(는) 잘못된 방식의 입력입니다." + " 지역 이름을 확인해주세요");
					continue;
				}
				book.setCityName(item.get("cityName").toString());
				String cityId = bookService.getCityIdByCityName(item.get("cityName").toString());
				book.setCityId(cityId);

//				if (bookIdFlag) {
//					boolean sameCityBookId = false;
//					String tempCityId = "";
//					if (inputCn.equals("서울")) {
//						tempCityId = "0";
//					} else if (inputCn.equals("대전")) {
//						tempCityId = "1";
//					} else if (inputCn.equals("구미")) {
//						tempCityId = "2";
//					} else if (inputCn.equals("광주")) {
//						tempCityId = "3";
//					}
//					
//					String inputBid = item.get("bookId").toString();
//					// 북아이디가 이미 존재하면 몇개 존재하는지 파악하기
//					List<String> existBookCityId = bookService.getexistBookCityIdList(inputBid);
//
//					if (existBookCityId.size() > 0) {
//						bookIdFlag = true;
//					}
//
//					for (int j = 0; j < existBookCityId.size(); j++) {
//						if (tempCityId.equals(existBookCityId.get(j))) {
//							sameCityBookId = true;
//							break;
//						}
//					}
//
//					if (sameCityBookId) {
//						remainList.add(i + 1);
//						reasonList.add(inputCn + "지역에 " + inputBid + "가 이미 사용중입니다.");
//						continue;
//					}
//				}
				String inputClass = item.get("className").toString();

				for (int j = 0; j < classArr.length; j++) {
					if (inputClass.equals(classArr[j])) {
						classFlag = true;
						break;
					}
				}

				if (!classFlag) {
					remainList.add(i + 1);
					reasonList.add(inputClass + "은(는) 잘못된 방식의 입력입니다." + " 반 이름을 확인해주세요");
					continue;
				}
				book.setClassName(item.get("className").toString());
				String classId = bookService.getClassIdByClassName(item.get("className").toString());
				book.setClassId(classId);
				book.setDelFlag(0);
				bookService.addBookByFile(book);
				successList.add(i + 1);
				sBidList.add(book.getBookId());
			}
			status = HttpStatus.OK;
			resultMap.put("status", true);
			resultMap.put("remainList", remainList);
			resultMap.put("reasonList", reasonList);
			resultMap.put("successList", successList);
			resultMap.put("sBidList", sBidList);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/require/list")
	public ResponseEntity<Map<String, Object>> getRequireBookList(HttpServletRequest req, HttpServletResponse res) {
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
			List<ReqBook> reqBookList = reqBookService.getAllReqBookList(reqCity, userRoleId);

			status = HttpStatus.OK;

			resultMap.put("status", true);
			resultMap.put("reqBookList", reqBookList);
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/book/require/detail/{reqId}")
	public ResponseEntity<Map<String, Object>> getRequireBookDetailByBookRentId(@PathVariable String reqId,
			HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> tmpMap = new HashMap<>();
		HttpStatus status = null;
		try {
			tmpMap.putAll((Map<? extends String, ? extends Object>) jwtService.get(req.getHeader("token")).get("User"));
			ReqBookDetail reqBookDetail = reqBookService.getReqBookDetailByReqId(reqId);
			User user = userService.getUserInfoByUserId(reqBookDetail.getUserId());
			List<ReqBookDetail> reqBookDetails = new ArrayList<>();
			reqBookDetails.add(reqBookDetail);
			List<String> cateList = new ArrayList<>();
			cateList.addAll(userService.getCateListByUserId(reqBookDetails.get(0).getUserId()));
			StringBuilder userCategory = new StringBuilder();
//
			for (int i = 0; i < cateList.size(); i++) {
				if (i == cateList.size() - 1) {
					userCategory.append(cateList.get(i));
				} else {
					userCategory.append(cateList.get(i)).append(", ");
				}
			}
			user.setCategoryName(userCategory.toString());
//
			List<User> users = new ArrayList<>();
			users.add(user);
			status = HttpStatus.OK;
			resultMap.put("userInfo", users);
			resultMap.put("reqBookDetails", reqBookDetails);
			resultMap.put("status", status);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}