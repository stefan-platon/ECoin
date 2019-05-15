package service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Account;
import model.Transaction;
import repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository TRANSACTION_REPOSITORY;

	public Transaction create(Account accountObj, String acountNumber, BigDecimal amount, String details, String type) {
		Transaction transaction = new Transaction();

		transaction.setAccountObj(accountObj);
		transaction.setAccount(acountNumber);
		transaction.setAmount(amount);
		transaction.setDetails(details);
		transaction.setType(type);

		transaction = TRANSACTION_REPOSITORY.save(transaction);

		return transaction;
	}

}
