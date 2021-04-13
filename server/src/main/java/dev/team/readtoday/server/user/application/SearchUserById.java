package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserNotFound;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;

@Service
public final class SearchUserById {

  private final UserRepository userRepository;

  public SearchUserById(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Search a user for its ID.
   *
   * @param userId the user ID
   * @throws UserNotFound if there is no user with the specified ID
   */
  public User search(UserId userId) {
    Optional<User> optUser = userRepository.getById(userId);
    return optUser.orElseThrow(UserNotFound::new);
  }
}
