package dev.team.readtoday.server.user.application.signup;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.domain.AlreadyExistingUser;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;
import java.util.Optional;

@Service
public final class SignUpUser {

  private final ProfileFetcher profileFetcher;
  private final UserRepository repository;

  public SignUpUser(ProfileFetcher profileFetcher,
                    UserRepository repository) {
    this.profileFetcher = profileFetcher;
    this.repository = repository;
  }

  public void signUp(AccessToken token, UserId userId, Username username) {
    EmailAddress email = profileFetcher.fetchEmailAddress(token);

    Optional<User> optUser = repository.getByEmailAddress(email);
    if (optUser.isPresent()) {
      throw new AlreadyExistingUser("There is already a user with that email: " + email);
    }

    User user = new User(userId, username, email, Role.USER);
    repository.save(user);
  }
}
