package dev.team.readtoday.server.user.application;

import dev.team.readtoday.server.user.domain.EmailAddress;

public interface ProfileFetcher {

  EmailAddress fetchEmailAddress(AuthToken token) throws AuthProcessFailed;
}
