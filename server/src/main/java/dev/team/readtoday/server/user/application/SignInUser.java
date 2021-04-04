package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;

public final class SignInUser {

  private final ProfileFetcher profileFetcher;
  private final UserRepository repository;

  public SignInUser(ProfileFetcher profileFetcher, UserRepository repository) {
    this.profileFetcher = profileFetcher;
    this.repository = repository;
  }

  public User signIn(AuthToken token) throws AuthProcessFailed, NonExistingUser {
    EmailAddress email = profileFetcher.fetchEmailAddress(token);

    Optional<User> optUser = repository.getByEmailAddress(email);
    if (optUser.isPresent()) {
      return optUser.get();
    }
    throw new NonExistingUser("This user has not sign up");
  }
}
