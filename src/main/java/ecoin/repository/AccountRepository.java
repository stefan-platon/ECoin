package ecoin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import ecoin.model.Account;
import ecoin.model.User;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

	@Nullable
	Account findFirstById(long id);

	@Nullable
	Account findFirstByAccountNumber(String accountNumber);

	@Nullable
	Account findFirstByUserObjAndAccountNumber(User user, String accountNumber);

	List<Account> findByUserObj(User user);

	@Query("from account where user_id = :user_id and account_type = :account_type and account_number != :account_number")
	List<Account> findByUserObjAndTypeExceptAccountNumber(@Param("user_id") long userId,
			@Param("account_type") String accountType, @Param("account_number") String accountNumber);

}
