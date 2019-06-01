package ecoin.cron_job;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ecoin.model.Notification;
import ecoin.repository.NotificationRepository;
import ecoin.utils.email.Email;

@Component
public class NotificationCronJob {

	NotificationRepository NOTIFICATION_REPOSITORY;

	@Autowired
	public NotificationCronJob(NotificationRepository NOTIFICATION_REPOSITORY) {
		super();
		this.NOTIFICATION_REPOSITORY = NOTIFICATION_REPOSITORY;
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	public void sendEmailNotification() {
		List<Notification> notifications = NOTIFICATION_REPOSITORY.findAllBySentTime(null);

		Email email = new Email();

		notifications.forEach((notification) -> {
			String to = notification.getUserObj().getPerson().getEmail();
			email.send(to, "Transaction finished successfully.", "Transaction finished successfully.");

			notification.setSentTime(LocalDateTime.now());
			NOTIFICATION_REPOSITORY.save(notification);
		});
	}

}
