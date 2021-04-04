package dev.team.readtoday.server.user.domain;

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
    Objects.requireNonNull(id);
    Objects.requireNonNull(username);
    Objects.requireNonNull(emailAddress);
    Objects.requireNonNull(role);

    this.id = id;
    this.username = username;
    this.emailAddress = emailAddress;
    this.role = role;
  }

  public static User create(Username username, EmailAddress email, Role role) {
    return new User(UserId.random(), username, email, role);
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

  @Override
  public String toString() {
    return String.format(
        "User (id=%s, username=%s, emailAddress=%s, role=%s)",
        id, username, emailAddress, role);
  }
}
