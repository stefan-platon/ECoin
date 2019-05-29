package ecoin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import ecoin.model.Authentication;

@Repository
public interface AuthenticationRepository extends CrudRepository<Authentication, Long> {

	@Nullable
	Authentication findFirstByToken(String token);

}
