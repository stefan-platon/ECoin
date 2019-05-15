package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	@Nullable
	Person findById(long id);

}
