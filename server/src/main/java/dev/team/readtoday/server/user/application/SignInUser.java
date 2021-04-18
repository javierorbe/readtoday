package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.application.profile.ProfileFetchingFailed;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.NonExistingUser;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;

@Service
public final class SignInUser {

  private final ProfileFetcher profileFetcher;
  private final UserRepository repository;

  public SignInUser(ProfileFetcher profileFetcher, UserRepository repository) {
    this.profileFetcher = profileFetcher;
    this.repository = repository;
  }

  public User signIn(AccessToken token) throws ProfileFetchingFailed, NonExistingUser {
    EmailAddress email = profileFetcher.fetchEmailAddress(token);

    Optional<User> optUser = repository.getByEmailAddress(email);
    if (optUser.isPresent()) {
      return optUser.get();
    }
    throw new NonExistingUser("This user has not sign up");
  }
}
