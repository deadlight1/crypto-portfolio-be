package com.volodymyr.pletnev.portfolio.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class AdviceController {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handle(ResponseStatusException responseStatusException){
		return ResponseEntity.status(responseStatusException.getStatus()).body(responseStatusException.getMessage());
	}
}
