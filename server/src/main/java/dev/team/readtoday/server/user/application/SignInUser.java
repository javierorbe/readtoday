package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;

public class SignInUser {
	private final ProfileFetcher profileFetcher;
	private final UserRepository repository;
	
	public SignInUser(ProfileFetcher profileFetcher, UserRepository repository) {
		this.profileFetcher = profileFetcher;
		this.repository = repository;
	}
	
	public Username SignIn(AuthToken token, Username username) throws AuthProcessFailed{
		EmailAddress email = profileFetcher.fetchEmailAddress(token);
		
		
		
	    return username;
	}
}

