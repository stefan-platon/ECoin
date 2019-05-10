package exceptions;

public class HTTPClientCustomException extends RuntimeException {

	private static final long serialVersionUID = 2240773616268742379L;

	public HTTPClientCustomException(String message) {
		super(message);
	}

}
