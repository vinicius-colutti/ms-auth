package com.colutti.msauth.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.colutti.msauth.exceptions.ExceptionRespose;
import com.colutti.msauth.exceptions.InvalidJwtAuthenticationException;

@ControllerAdvice
@RestController
public class CustomizedReponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionRespose> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
		ExceptionRespose exceptionRespose = new ExceptionRespose(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionRespose, HttpStatus.BAD_REQUEST);
	}

}
