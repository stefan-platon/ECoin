package ecoin.exceptions;

public class AccountDataValidationException extends RuntimeException {

	private static final long serialVersionUID = -6956333636744717589L;

	public AccountDataValidationException(String message) {
		super(message);
	}

}
