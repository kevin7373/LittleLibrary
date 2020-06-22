package com.ssafy.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {

	@ExceptionHandler(value = { RuntimeException.class })
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> internalServerError(Exception e) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("msg", e.getMessage());
		return resultMap;
	}
}