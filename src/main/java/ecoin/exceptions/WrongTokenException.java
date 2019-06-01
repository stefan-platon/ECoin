package ecoin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong token!")
public class WrongTokenException extends RuntimeException {

	private static final long serialVersionUID = -5124200239009089574L;

	public WrongTokenException() {
		super("Wrong token!");
	}

	public WrongTokenException(String message) {
		super(message);
	}

}
