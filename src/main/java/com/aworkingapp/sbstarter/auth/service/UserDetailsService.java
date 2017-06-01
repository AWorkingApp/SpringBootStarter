package com.aworkingapp.sbstarter.auth.service;

import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used for authentication service
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * {@link UserRepository}
	 */
	@Autowired
	private UserRepository userRepo;

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(user);
		return user;
	}
}
