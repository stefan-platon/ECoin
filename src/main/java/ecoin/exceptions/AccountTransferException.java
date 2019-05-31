package ecoin.exceptions;

public class AccountTransferException extends RuntimeException {

	private static final long serialVersionUID = -8705813984065897931L;

	public AccountTransferException(String message) {
		super(message);
	}

}
