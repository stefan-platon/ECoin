package ecoin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "custom client exception")
public class HTTPCustomClientException extends RuntimeException {

	private static final long serialVersionUID = -5124200239009089574L;

	public HTTPCustomClientException() {
		super();
	}

	public HTTPCustomClientException(String message) {
		super(message);
	}

}
