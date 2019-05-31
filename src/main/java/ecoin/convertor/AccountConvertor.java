package ecoin.convertor;

import ecoin.dto.AccountDTO;
import ecoin.model.Account;

public class AccountConvertor {

	public static AccountDTO convert(Account account) {
		return new AccountDTO(account.getId(), account.getAccountNumber(), account.getBalance(),
				account.getAccountType(), account.getCreatedTime(), account.getUpdatedTime());
	}

}
