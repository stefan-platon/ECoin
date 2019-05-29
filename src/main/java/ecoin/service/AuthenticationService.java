package ecoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.model.Authentication;
import ecoin.repository.AuthenticationRepository;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationRepository AUTHENTICATION_REPOSITORY;

	public boolean checkToken(String token) {
		Authentication authentication = AUTHENTICATION_REPOSITORY.findFirstByToken(token);

		if (authentication == null) {
			return false;
		} else {
			return true;
		}
	}

}
