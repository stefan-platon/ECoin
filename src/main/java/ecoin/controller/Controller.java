package ecoin.controller;

import org.springframework.beans.factory.annotation.Autowired;

import ecoin.service.AuthenticationService;

public class Controller {

	@Autowired
	public AuthenticationService AUTHENTICATION_SERVICE;

	boolean checkToken(String token) {
		return AUTHENTICATION_SERVICE.checkToken(token);
	}

}
