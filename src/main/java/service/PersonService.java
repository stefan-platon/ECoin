package service;

import model.User;
import repository.PersonRepository;

public class PersonService {

	private PersonRepository PERSON_REPOSITORY = new PersonRepository();

	public long create(String sddress, String email, String firstName, String lastName, User user) {
		return PERSON_REPOSITORY.create(sddress, email, firstName, lastName, user);
	}

}
