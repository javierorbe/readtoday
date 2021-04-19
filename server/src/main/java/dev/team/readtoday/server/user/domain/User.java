package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.UserId;
import java.util.Objects;

public final class User {

  private final UserId id;
  private final Username username;
  private final EmailAddress emailAddress;
  private Role role;

  public User(UserId id,
              Username username,
              EmailAddress emailAddress,
              Role role) {
    this.id = Objects.requireNonNull(id);
    this.username = Objects.requireNonNull(username);
    this.emailAddress = Objects.requireNonNull(emailAddress);
    this.role = Objects.requireNonNull(role);
  }

  public UserId getId() {
    return id;
  }

  public Username getUsername() {
    return username;
  }

  public EmailAddress getEmailAddress() {
    return emailAddress;
  }

  public Role getRole() {
    return role;
  }

  public boolean isAdmin() {
    return role == Role.ADMIN;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
