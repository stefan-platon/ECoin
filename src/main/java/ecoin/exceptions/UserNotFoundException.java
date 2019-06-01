package ecoin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user not found")
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5124200239009089574L;

	public UserNotFoundException() {
		super("User not found!");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
