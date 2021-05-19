package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Optional;

public interface UserRepository {

  /**
   * Saves the user
   * @param user
   */
  void save(User user);

  /**
   * Returns the user given the email adress
   * @param email
   * @return
   */
  Optional<User> getByEmailAddress(EmailAddress email);

  /**
   * Returns the user given the user id
   * @param id
   * @return
   */
  Optional<User> getById(UserId id);
}
