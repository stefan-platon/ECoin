package collections;

public enum AccountType {

	RON, EUR;

	public String getType() {
		return this.toString();
	}

	public static boolean isType(String type) {
		for (AccountType accountType : AccountType.values()) {
			if (type.equals(accountType.toString())) {
				return true;
			}
		}
		return false;
	}

}
