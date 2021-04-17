package dev.team.readtoday.server.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserNotFound;
import dev.team.readtoday.server.user.domain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SearchUserByIdTest {

  @Test
  void shouldReturnUserIfFound() {
    UserRepository repository = mock(UserRepository.class);
    SearchUserById searchUserById = new SearchUserById(repository);
    UserId userId = UserId.random();
    User expectedUser = UserMother.withId(userId);
    when(repository.getById(eq(userId))).thenReturn(Optional.of(expectedUser));

    User actualUser = searchUserById.search(userId);

    assertEquals(expectedUser, actualUser);
  }

  @Test
  void shouldThrowExceptionIfNotFound() {
    UserRepository repository = mock(UserRepository.class);
    SearchUserById searchUserById = new SearchUserById(repository);
    when(repository.getById(any())).thenReturn(Optional.empty());

    assertThrows(UserNotFound.class, () -> searchUserById.search(UserId.random()));
  }
}
