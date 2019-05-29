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
import ecoin.request_details.UserCreateDetails;
import ecoin.service.UserService;

@RestController
public class UserController extends Controller {

	@Autowired
	UserService USER_SERVICE;

	@ExceptionHandler({ Exception.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = "application/json", produces = "application/json")
	public User create(@RequestBody UserCreateDetails details) {
		return USER_SERVICE.create(details.getUsername(), details.getPassword(), details.getAddress(),
				details.getEmail(), details.getFirstName(), details.getLastName());
	}

	@GetMapping("/user/{username}/{password}")
	public String login(@PathVariable String username, @PathVariable String password) {
		return USER_SERVICE.login(username, password);
	}

}
