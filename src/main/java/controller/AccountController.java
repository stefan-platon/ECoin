package controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import model.Account;
import service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService ACCOUNT_SERVICE;

	@RequestMapping(value = "/account", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Account create(@RequestBody String accountNumber, @RequestBody BigDecimal balance,
			@RequestBody String accountType, @RequestBody long userId) throws HttpClientErrorException {
		return ACCOUNT_SERVICE.create(accountNumber, balance, accountType, userId);
	}

	@RequestMapping(value = "/account/transfer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void transfer(@RequestBody long accountFromId, @RequestBody long accountToId, @RequestBody BigDecimal amount,
			@RequestBody String details) throws HttpClientErrorException {
		ACCOUNT_SERVICE.transfer(accountFromId, accountToId, amount, details);
	}

	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, produces = "application/json")
	public List<Account> findByUser(@PathVariable long userId) throws HttpClientErrorException {
		return ACCOUNT_SERVICE.findByUser(userId);
	}

	@RequestMapping(value = "/account/findFirstByUserAndAccountNumber", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Account findFirstByUserAndAccountNumber(@RequestBody long userId, @RequestBody String accountNumber)
			throws HttpClientErrorException {
		return ACCOUNT_SERVICE.findFirstByUserAndAccountNumber(userId, accountNumber);
	}

	@RequestMapping(value = "/account/findByUserAndTypeExceptAccountNumber", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Account> findByUserAndTypeExceptAccountNumber(@RequestBody long userId, @RequestBody String accountType,
			@RequestBody String accountNumber) throws HttpClientErrorException {
		return ACCOUNT_SERVICE.findByUserAndTypeExceptAccountNumber(userId, accountType, accountNumber);
	}

}
