package dev.team.readtoday.server.user.domain;

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
}
