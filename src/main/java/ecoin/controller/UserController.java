package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import model.User;
import service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService USER_SERVICE;

	@RequestMapping(value = "/user/getByCredentials", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public User getByCredentials(@RequestBody String username, @RequestBody String password)
			throws HttpClientErrorException {
		return USER_SERVICE.getByCredentials(username, password);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
	public User getById(@PathVariable long id) throws HttpClientErrorException {
		return USER_SERVICE.getById(id);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public User create(@RequestBody String username, @RequestBody String password, @RequestBody String address,
			@RequestBody String email, @RequestBody String firstName, @RequestBody String lastName)
			throws HttpClientErrorException {
		return USER_SERVICE.create(username, password, address, email, firstName, lastName);
	}

}
