package ecoin.exceptions;

public class UniqueDatabaseConstraintException extends RuntimeException {

	private static final long serialVersionUID = -8705813984065897931L;

	public UniqueDatabaseConstraintException(String message) {
		super(message);
	}

}
