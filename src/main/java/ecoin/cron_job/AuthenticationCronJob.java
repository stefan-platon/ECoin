package ecoin.cron_job;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ecoin.model.Authentication;
import ecoin.repository.AuthenticationRepository;

@Component
public class AuthenticationCronJob {

	@Autowired
	private Environment env;

	AuthenticationRepository AUTHENTICATION_REPOSITORY;

	@Autowired
	public AuthenticationCronJob(AuthenticationRepository AUTHENTICATION_REPOSITORY) {
		super();
		this.AUTHENTICATION_REPOSITORY = AUTHENTICATION_REPOSITORY;
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	public void deleteTokens() {
		Date date = new Date(System.currentTimeMillis()
				- TimeUnit.MINUTES.toMillis(Integer.parseInt(env.getProperty("token.livespan"))));
		List<Authentication> authentications = AUTHENTICATION_REPOSITORY
				.findAllWhereCreatedTimeOlderThan(date.toString());

		authentications.forEach((authentication) -> {
			AUTHENTICATION_REPOSITORY.deleteByToken(authentication.getToken());
		});
	}

}
