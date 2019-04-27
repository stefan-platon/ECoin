package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Account;
import model.Notification;
import model.Person;
import model.Transaction;
import model.User;

public class SessionFactoryObject {

	private static SessionFactory sessionFactory = null;

	private static Session session = null;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
					.addAnnotatedClass(Person.class).addAnnotatedClass(Notification.class)
					.addAnnotatedClass(Account.class).addAnnotatedClass(Transaction.class).buildSessionFactory();

		return sessionFactory;
	}

	public static Session getSession() {
		if (session == null)
			session = getSessionFactory().openSession();

		return session;
	}

	public static void closeSession() {
		if (session != null)
			session.close();
	}

	public static void closeSessionFactory() {
		if (session != null)
			session.close();
		if (sessionFactory != null)
			sessionFactory.close();
	}

}
