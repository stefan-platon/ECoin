package ecoin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecoin.model.User;
import ecoin.request_body.user.UserCreateRequestBody;
import ecoin.request_response.user.LoginRequestResponse;
import ecoin.service.UserService;

@RestController
public class UserController extends Controller {

	UserService USER_SERVICE;

	@Autowired
	public UserController(UserService USER_SERVICE) {
		super();
		this.USER_SERVICE = USER_SERVICE;
	}

	@ExceptionHandler({ Exception.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json", produces = "application/json")
	public User create(@RequestBody UserCreateRequestBody body) {
		return USER_SERVICE.create(body.getUsername(), body.getPassword(), body.getAddress(), body.getEmail(),
				body.getFirstName(), body.getLastName());
	}

	@GetMapping("/user/{username}/{password}")
	public LoginRequestResponse login(@PathVariable String username, @PathVariable String password) {
		String token = USER_SERVICE.login(username, password);

		LoginRequestResponse response = new LoginRequestResponse();
		response.setToken(token);

		return response;
	}

}
