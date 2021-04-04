package dev.team.readtoday.server.user.application;

import java.util.Optional;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.InvalidEmailAddress;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;

public class SignInUser {
  private final ProfileFetcher profileFetcher;
  private final UserRepository repository;

  public SignInUser(ProfileFetcher profileFetcher, UserRepository repository) {
    this.profileFetcher = profileFetcher;
    this.repository = repository;
  }

  public User SignIn(AuthToken token) throws AuthProcessFailed, InvalidEmailAddress {
    EmailAddress email = profileFetcher.fetchEmailAddress(token);
    Optional<User> optUser = repository.getByEmailAddress(email);
    if (!optUser.isPresent()) {
      throw new InvalidEmailAddress("There aren't any users with that email: " + email);
    }
    return optUser.get();
  }
}

