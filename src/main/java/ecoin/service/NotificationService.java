package ecoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.exceptions.UniqueDatabaseConstraintException;
import ecoin.model.Notification;
import ecoin.model.User;
import ecoin.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository NOTIFICATION_REPOSITORY;

	public Notification create(String details, User user) {
		Notification notification = new Notification();

		try {
			notification.setDetails(details);
			notification.setUserObj(user);
		} catch (UniqueDatabaseConstraintException e) {
			throw new UniqueDatabaseConstraintException("Email already exists!");
		}

		notification = NOTIFICATION_REPOSITORY.save(notification);

		return notification;
	}

}
