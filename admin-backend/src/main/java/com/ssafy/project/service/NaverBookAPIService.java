package com.ssafy.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.Book;

@Service
public class NaverBookAPIService {
	public List<Book> getBookByBookName(String bookName) {
		String clientId = "lcG3c739Y9f0zHeuNxju";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "rurecfKvbx";// 애플리케이션 클라이언트 시크릿값";
		List<Book> books = new ArrayList<Book>();

		try {
			String text = URLEncoder.encode(bookName, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/book_adv.json" + "?d_isbn=" + text
					+ "&start=1&display=10";// +"&display=10&start=1";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			// string -> Object
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(response.toString());
			JSONArray items = (JSONArray) jsonObj.get("items");

			for (int i = 0; i < items.size(); i++) {
				JSONObject item = (JSONObject) items.get(i);
				Book book = new Book(null, item.get("isbn").toString(),
						item.get("title").toString().replace("<b>", "").replace("</b>", ""),
						Integer.parseInt(item.get("price").toString()), item.get("publisher").toString(),
						item.get("pubdate").toString(), item.get("author").toString(), item.get("image").toString(), 0,
						null, null, null);
				books.add(book);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return books;
	}

	public synchronized JSONObject getBookByISBN(String bookISBN) {

		String clientId = "lcG3c739Y9f0zHeuNxju";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "rurecfKvbx";// 애플리케이션 클라이언트 시크릿값";
//		List<Book> books = new ArrayList<Book>();
		JSONObject item = new JSONObject();

		try {
			synchronized (this) {
				String text = URLEncoder.encode(bookISBN, "UTF-8");
				String apiURL = "https://openapi.naver.com/v1/search/book_adv.json" + "?d_isbn=" + text
						+ "&start=1&display=10";// +"&display=10&start=1";
				URL url = new URL(apiURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("X-Naver-Client-Id", clientId);
				con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
				int responseCode = con.getResponseCode();
				BufferedReader br;
				if (responseCode == 200) { // 정상 호출
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				} else { // 에러 발생
					br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				// string -> Object
				JSONParser jsonParse = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParse.parse(response.toString());
				JSONArray items = (JSONArray) jsonObj.get("items");
				item = (JSONObject) items.get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
		return item;
	}
}