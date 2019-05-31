package ecoin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ecoin.exceptions.AccountDataValidationException;
import ecoin.exceptions.AccountTransferException;
import ecoin.exceptions.CustomException;
import ecoin.exceptions.UserNotFoundException;
import ecoin.exceptions.WrongTokenException;
import ecoin.service.AuthenticationService;

public class Controller {

	@Autowired
	public AuthenticationService AUTHENTICATION_SERVICE;

	boolean checkToken(String token) {
		return AUTHENTICATION_SERVICE.checkToken(token);
	}

	@ExceptionHandler({ WrongTokenException.class })
	void handleWrongTokenException(HttpServletResponse response, WrongTokenException e) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler({ UserNotFoundException.class })
	void handleUserNotFoundException(HttpServletResponse response, UserNotFoundException e) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler({ AccountDataValidationException.class })
	void handleAccountDataValidationException(HttpServletResponse response, AccountDataValidationException e)
			throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler({ AccountTransferException.class })
	void handleAccountTransferException(HttpServletResponse response, AccountTransferException e) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler({ CustomException.class })
	void handleCustomException(HttpServletResponse response, CustomException e) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
