package ecoin.utils.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Email {

	private final Logger LOGGER = LogManager.getLogger(Email.class);

	@Autowired
	public JavaMailSender emailSender;

	@Value("${company.email}")
	private String from;

	public void send(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			LOGGER.error("Could not send email : " + to + " : " + subject + " : " + text);
		}
	}

}
