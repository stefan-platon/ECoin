package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.UniqueDatabaseConstraintException;
import model.Person;
import model.User;
import repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository PERSON_REPOSITORY;

	public Person create(String address, String email, String firstName, String lastName, User user) {
		Person person = new Person();

		try {
			person.setAddress(address);
			person.setEmail(email);
			person.setFirstName(firstName);
			person.setLastName(lastName);
			person.setUserObj(user);
		} catch (UniqueDatabaseConstraintException e) {
			throw new UniqueDatabaseConstraintException("Email already exists!");
		}

		person = PERSON_REPOSITORY.save(person);

		return person;
	}

}
