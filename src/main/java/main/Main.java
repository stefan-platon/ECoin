package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import menus.LoginMenu;
import models.Account;
import models.Notification;
import models.Person;
import models.Transaction;
import models.User;

/**
 * Starting point of the program.
 */
public class Main {

	public static void main(String[] args) {	
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Person.class)
				.addAnnotatedClass(Notification.class)
				.addAnnotatedClass(Account.class)
				.addAnnotatedClass(Transaction.class)
				.buildSessionFactory();
		
//		LoginMenu loginMenu = new LoginMenu();
//		loginMenu.show();
		
		factory.close();
	}

}
