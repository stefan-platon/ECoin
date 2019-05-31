package ecoin.exceptions;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -8705813984065897931L;

	public CustomException(String message) {
		super(message);
	}

}
