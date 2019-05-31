package ecoin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecoin.convertor.AccountConvertor;
import ecoin.dto.AccountDTO;
import ecoin.exceptions.WrongTokenException;
import ecoin.model.Account;
import ecoin.request_body.account.AccountCreateRequestBody;
import ecoin.request_body.account.AccountTransferRequestBody;
import ecoin.service.AccountService;

@RestController
public class AccountController extends Controller {

	AccountService ACCOUNT_SERVICE;

	@Autowired
	public AccountController(AccountService ACCOUNT_SERVICE) {
		super();
		this.ACCOUNT_SERVICE = ACCOUNT_SERVICE;
	}

	@PostMapping("/account/transfer/{token}")
	public void transfer(@PathVariable String token, @RequestBody AccountTransferRequestBody body) {
		if (checkToken(token)) {
			ACCOUNT_SERVICE.transfer(body.getAccountFrom(), body.getAccountTo(), body.getAmount(), body.getDetails());
		} else {
			throw new WrongTokenException();
		}
	}

	@PostMapping("/account/{token}")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody AccountDTO create(@PathVariable String token, @RequestBody AccountCreateRequestBody body) {
		if (checkToken(token)) {
			return AccountConvertor.convert(ACCOUNT_SERVICE.create(body.getAccountNumber(), body.getBalance(),
					body.getAccountType(), body.getUserId()));
		} else {
			throw new WrongTokenException();
		}
	}

	@GetMapping("/account/{token}/{user_id}")
	public List<Account> findByUser(@PathVariable String token, @PathVariable long userId) {
		if (checkToken(token)) {
			return ACCOUNT_SERVICE.findByUser(userId);
		} else {
			throw new WrongTokenException();
		}
	}

}
