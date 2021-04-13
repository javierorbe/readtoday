package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Optional;

public interface UserRepository {

  void save(User user);

  Optional<User> getByEmailAddress(EmailAddress email);

  Optional<User> getById(UserId id);
}
