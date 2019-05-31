package ecoin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecoin.exceptions.HTTPCustomClientException;
import ecoin.model.Account;
import ecoin.request_body.account.AccountTransferRequestBody;
import ecoin.service.AccountService;

@RestController
public class AccountController extends Controller {

	@Autowired
	AccountService ACCOUNT_SERVICE;

	@ExceptionHandler({ Exception.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@PostMapping("/account/transfer/{token}")
	public void transfer(@PathVariable String token, @RequestBody AccountTransferRequestBody body) {
		if (checkToken(token)) {
			ACCOUNT_SERVICE.transfer(token, body.getAccountFrom(), body.getAccountTo(), body.getAmount(),
					body.getDetails());
		} else {
			throw new HTTPCustomClientException();
		}
	}

	@PostMapping("/account/{token}")
	@ResponseStatus(HttpStatus.CREATED)
	public Account create(@PathVariable String token, @RequestBody String accountNumber,
			@RequestBody BigDecimal balance, @RequestBody String accountType, @RequestBody long userId) {
		if (checkToken(token)) {
			return ACCOUNT_SERVICE.create(token, accountNumber, balance, accountType, userId);
		} else {
			throw new HTTPCustomClientException();
		}
	}

	@GetMapping("/account/{token}/{user_id}")
	public List<Account> findByUser(@PathVariable String token, @PathVariable long userId) {
		if (checkToken(token)) {
			return ACCOUNT_SERVICE.findByUser(token, userId);
		} else {
			throw new HTTPCustomClientException();
		}
	}

	@PostMapping("/account/findFirstByUserAndAccountNumber/{token}")
	public Account findFirstByUserAndAccountNumber(@PathVariable String token, @RequestBody long userId,
			@RequestBody String accountNumber) {
		if (checkToken(token)) {
			return ACCOUNT_SERVICE.findFirstByUserAndAccountNumber(token, userId, accountNumber);
		} else {
			throw new HTTPCustomClientException();
		}
	}

	@PostMapping("/account/findByUserAndTypeExceptAccountNumber/{token}")
	public List<Account> findByUserAndTypeExceptAccountNumber(@PathVariable String token, @RequestBody long userId,
			@RequestBody String accountType, @RequestBody String accountNumber) {
		if (checkToken(token)) {
			return ACCOUNT_SERVICE.findByUserAndTypeExceptAccountNumber(token, userId, accountType, accountNumber);
		} else {
			throw new HTTPCustomClientException();
		}
	}

}
