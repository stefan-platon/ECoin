package repository;

import model.Person;
import model.User;

public class PersonRepository extends Repository {

	public long create(String sddress, String email, String firstName, String lastName, User user) {
		long returnId;
		Person person = new Person();

		person.setAddress(sddress);
		person.setEmail(email);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setUserObj(user);

		SESSION.beginTransaction();

		returnId = (long) SESSION.save(person);
		SESSION.getTransaction().commit();

		return returnId;
	}

}
