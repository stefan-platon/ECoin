package service;

import exceptions.UniqueDatabaseConstraintException;
import model.Person;
import model.User;
import repository.PersonRepository;

public class PersonService {

	private PersonRepository PERSON_REPOSITORY = new PersonRepository();

	public Person create(String sddress, String email, String firstName, String lastName, User user) {
		long id;

		try {
			id = PERSON_REPOSITORY.create(sddress, email, firstName, lastName, user);
		} catch (UniqueDatabaseConstraintException e) {
			return null;
		}

		return PERSON_REPOSITORY.getById(id);
	}

}
