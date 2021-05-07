package dev.team.readtoday.server.user.application.sendemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.EmailAddressMother;
import dev.team.readtoday.server.user.domain.UserMother;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;

@TestMethodOrder(MethodOrderer.Random.class)
final class SendEmailCommandHandlerTest {

  @Test
  void shouldSendEmailWithGivenParameters() {
    // Given
    var sendEmail = mock(SendEmail.class);
    var searchUser = mock(SearchUserById.class);

    var userId = UserId.random();
    var email = EmailAddressMother.random();
    when(searchUser.search(eq(userId)))
        .thenReturn(UserMother.withIdAndEmail(userId, email));

    var subject = EmailSubjectMother.random();
    var content = EmailContentMother.random();
    var cmd = new SendEmailCommand(userId.toString(), subject.toString(), content.toString());

    var handler = new SendEmailCommandHandler(sendEmail, searchUser);

    // When
    handler.handle(cmd);

    // Then
    var emailCaptor = ArgumentCaptor.forClass(EmailAddress.class);
    var subjectCaptor = ArgumentCaptor.forClass(EmailSubject.class);
    var contentCaptor = ArgumentCaptor.forClass(EmailContent.class);
    verify(sendEmail).send(emailCaptor.capture(), subjectCaptor.capture(), contentCaptor.capture());

    assertEquals(email, emailCaptor.getValue());
    assertEquals(subject, subjectCaptor.getValue());
    assertEquals(content, contentCaptor.getValue());
  }
}
