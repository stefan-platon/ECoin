package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Nullable
	User findFirstByUsernameAndPassword(String username, String password);

	@Nullable
	User findFirstById(long userId);

}
