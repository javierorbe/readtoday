package dev.team.readtoday.server.user.application.signup;

import dev.team.readtoday.server.shared.domain.Service;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.CommandHandler;
import dev.team.readtoday.server.user.application.profile.AccessToken;
import dev.team.readtoday.server.user.domain.Username;

@Service
public final class SignUpUserCommandHandler implements CommandHandler<SignUpUserCommand> {

  private final SignUpUser signUpUser;

  public SignUpUserCommandHandler(SignUpUser signUpUser) {
    this.signUpUser = signUpUser;
  }

  @Override
  public void handle(SignUpUserCommand command) {
    AccessToken accessToken = new AccessToken(command.getAccessToken());
    UserId userId = UserId.fromString(command.getUserId());
    Username username = new Username(command.getUsername());
    signUpUser.signUp(accessToken, userId, username);
  }
}
