package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Nullable
	Transaction findById(long id);

}
