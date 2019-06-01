package ecoin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

	@Query("from authentication where created_time < :date")
	List<Authentication> findAllWhereCreatedTimeOlderThan(@Param("date") String date);

	@Transactional
	void deleteByToken(String token);
}
