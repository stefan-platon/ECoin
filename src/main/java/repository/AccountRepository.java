package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import model.Account;

@Repository
public interface AccountRepository extends CustomRepository, CrudRepository<Account, Long> {

	@Nullable
	Account findById(long id);

	@Nullable
	Account findFirstByUserAndAccountNumber(long userId, String accountNumber);

	List<Account> findByUser(long userId);

	@Query("from Account where user_id = :user_id and account_type = :account_type and account_number != :account_number")
	List<Account> findByUserAndTypeExceptAccountNumber(@Param("user_id") long userId,
			@Param("account_type") String accountType, @Param("account_number") String accountNumber);

}
