package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import collections.AccountType;
import exceptions.AccountDataValidationException;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "user_id", nullable = false)
	private User userObj;

	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@Column(name = "account_type", nullable = false)
	private String accountType;

	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	@OneToMany(mappedBy = "accountObj", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<Transaction> transactions;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userObj
	 */
	public User getUserObj() {
		return userObj;
	}

	/**
	 * @param userObj the userObj to set
	 */
	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		if (accountNumber.length() != 24) {
			throw new AccountDataValidationException("Account number has wrong length.");
		}

		if (!accountNumber.startsWith("RO")) {
			throw new AccountDataValidationException("Account number should start with RO.");
		}

		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		if (!AccountType.isType(accountType)) {
			throw new AccountDataValidationException("Account type not supported.");
		}

		this.accountType = accountType;
	}

	/**
	 * @return the createdTime
	 */
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the updatedTime
	 */
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return the transactions
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@PrePersist
	private void initializeCreatedTime() {
		this.createdTime = LocalDateTime.now();
	}

	@PreUpdate
	private void initializeUpdatedTime() {
		this.updatedTime = LocalDateTime.now();
	}

}
