package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;

public final class SearchUserById {

  private final UserRepository userRepository;


  public SearchUserById(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> search(UserId id) {
    return userRepository.getById(id);
  }
}
