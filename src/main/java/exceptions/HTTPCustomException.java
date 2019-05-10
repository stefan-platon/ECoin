package exceptions;

public class HTTPCustomException extends RuntimeException {

	private static final long serialVersionUID = 2240773616268742379L;

	public HTTPCustomException(String message) {
		super(message);
	}

}
