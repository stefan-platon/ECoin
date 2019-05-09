package repository;

import org.hibernate.Session;

import database.SessionFactoryObject;

abstract class Repository {

	protected static final Session SESSION = SessionFactoryObject.getSession();

}
