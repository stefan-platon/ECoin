package ecoin.request_body.account;

import java.math.BigDecimal;

public class AccountTransferRequestBody {

	String accountFrom;

	String accountTo;

	BigDecimal amount;

	String details;

	/**
	 * @return the accountFrom
	 */
	public String getAccountFrom() {
		return accountFrom;
	}

	/**
	 * @param accountFrom the accountFrom to set
	 */
	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	/**
	 * @return the accountTo
	 */
	public String getAccountTo() {
		return accountTo;
	}

	/**
	 * @param accountTo the accountTo to set
	 */
	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

}
