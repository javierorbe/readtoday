package dev.team.readtoday.server.user.domain;

import dev.team.readtoday.server.shared.domain.UserId;

public enum UserMother {
  ;

  public static User random() {
    return new User(
        UserId.random(),
        UsernameMother.random(),
        EmailAddressMother.random(),
        Role.USER
    );
  }

  public static User withId(UserId id) {
    return new User(
        id,
        UsernameMother.random(),
        EmailAddressMother.random(),
        Role.USER
    );
  }

  public static User withEmail(EmailAddress email) {
    return new User(
        UserId.random(),
        UsernameMother.random(),
        email,
        Role.USER
    );
  }

  public static User withIdAndEmail(UserId id, EmailAddress email) {
    return new User(
        id,
        UsernameMother.random(),
        email,
        Role.USER
    );
  }

  public static User randomAdmin() {
    return new User(
        UserId.random(),
        UsernameMother.random(),
        EmailAddressMother.random(),
        Role.ADMIN
    );
  }
}
