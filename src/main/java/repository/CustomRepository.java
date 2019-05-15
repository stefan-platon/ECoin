package repository;

import org.hibernate.Session;

import database.SessionFactoryObject;

interface CustomRepository {

	static final Session SESSION = SessionFactoryObject.getSession();

}
