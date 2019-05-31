package ecoin.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.model.Account;
import ecoin.model.Transaction;
import ecoin.repository.TransactionRepository;

@Service
public class TransactionService {

	private TransactionRepository TRANSACTION_REPOSITORY;

	@Autowired
	public TransactionService(TransactionRepository TRANSACTION_REPOSITORY) {
		super();
		this.TRANSACTION_REPOSITORY = TRANSACTION_REPOSITORY;
	}

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
