package exceptions;

public class DataValidationException extends Exception {

	private static final long serialVersionUID = 443451622456455536L;

	public DataValidationException(String message) {
        super(message);
    }
	
}
