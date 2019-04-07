package collections;

public enum AccountType {

	RON("RON"), EUR("EUR");

	private String type;

	public String getType() {
		return this.type;
	}

	public static boolean isType(String type) {
		for (AccountType accountType : AccountType.values()) {
			if (type.equals(accountType.name())) {
				return true;
			}
		}
		return false;
	}

	private AccountType(String type) {
		this.type = type;
	}

}
