package ecoin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ecoin.model.Authentication;
import ecoin.model.User;

@Repository
public interface AuthenticationRepository extends CrudRepository<Authentication, Long> {

	@Nullable
	Authentication findFirstByToken(String token);

	@Nullable
	Authentication findFirstByUserObj(User user);

	@Transactional
	void deleteByToken(String token);
}
