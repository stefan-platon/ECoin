package exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5124200239009089574L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
