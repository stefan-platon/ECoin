package repository;

import javax.persistence.NoResultException;

import model.Person;
import model.User;

public class PersonRepository extends Repository {

	public long create(String address, String email, String firstName, String lastName, User user) {
		long returnId;
		Person person = new Person();

		person.setAddress(address);
		person.setEmail(email);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setUserObj(user);

		SESSION.beginTransaction();

		returnId = (long) SESSION.save(person);
		SESSION.getTransaction().commit();

		return returnId;
	}
	
	public Person getById(long id) {
		Person person = null;

		SESSION.beginTransaction();

		try {
			person = (Person) SESSION.createQuery("from Person where id = :id").setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			SESSION.getTransaction().commit();
		}

		return person;
	}

}
