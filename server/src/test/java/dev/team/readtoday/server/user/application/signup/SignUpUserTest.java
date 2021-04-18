package dev.team.readtoday.server.user.application.signup;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.application.profile.AccessTokenMother;
import dev.team.readtoday.server.user.application.profile.ProfileFetcher;
import dev.team.readtoday.server.user.application.profile.ProfileFetchingFailed;
import dev.team.readtoday.server.user.domain.AlreadyExistingUser;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.EmailAddressMother;
import dev.team.readtoday.server.user.domain.UserMother;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.UsernameMother;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
final class SignUpUserTest {

  @Test
  void shouldThrowExceptionIfUserAlreadyExists() throws ProfileFetchingFailed {
    AccessToken token = AccessTokenMother.random();
    EmailAddress email = EmailAddressMother.random();
    ProfileFetcher profileFetcher = mock(ProfileFetcher.class);
    when(profileFetcher.fetchEmailAddress(eq(token))).thenReturn(email);
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.getByEmailAddress(eq(email)))
        .thenReturn(Optional.of(UserMother.withEmail(email)));

    SignUpUser signUpUser = new SignUpUser(profileFetcher, userRepository);

    assertThrows(AlreadyExistingUser.class,
        () -> signUpUser.signUp(token, UserId.random(), UsernameMother.random()));
  }

  @Test
  void shouldNotThrowExceptionIfUserDoesNotExist() throws ProfileFetchingFailed {
    AccessToken token = AccessTokenMother.random();
    EmailAddress email = EmailAddressMother.random();
    ProfileFetcher profileFetcher = mock(ProfileFetcher.class);
    when(profileFetcher.fetchEmailAddress(eq(token))).thenReturn(email);
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.getByEmailAddress(eq(email))).thenReturn(Optional.empty());

    SignUpUser signUpUser = new SignUpUser(profileFetcher, userRepository);

    assertDoesNotThrow(() -> signUpUser.signUp(token, UserId.random(), UsernameMother.random()));
  }

  @Test
  void shouldSaveUserInRepositoryIfUserDoesNotExist() throws Exception {
    AccessToken token = AccessTokenMother.random();
    EmailAddress email = EmailAddressMother.random();
    ProfileFetcher profileFetcher = mock(ProfileFetcher.class);
    when(profileFetcher.fetchEmailAddress(eq(token))).thenReturn(email);
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.getByEmailAddress(eq(email))).thenReturn(Optional.empty());

    SignUpUser signUpUser = new SignUpUser(profileFetcher, userRepository);

    signUpUser.signUp(token, UserId.random(), UsernameMother.random());

    verify(userRepository, times(1)).save(any());
  }

  @Test
  void shouldThrowExceptionIfFailsGettingTheAccessToken() throws ProfileFetchingFailed {
    AccessToken token = AccessTokenMother.random();
    EmailAddress email = EmailAddressMother.random();
    ProfileFetcher profileFetcher = mock(ProfileFetcher.class);
    when(profileFetcher.fetchEmailAddress(eq(token)))
        .thenThrow(new ProfileFetchingFailed("Some error.", new Exception("Error cause.")));
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.getByEmailAddress(eq(email)))
        .thenReturn(Optional.of(UserMother.withEmail(email)));

    SignUpUser signUpUser = new SignUpUser(profileFetcher, userRepository);

    assertThrows(ProfileFetchingFailed.class,
        () -> signUpUser.signUp(token, UserId.random(), UsernameMother.random()));
  }
}
