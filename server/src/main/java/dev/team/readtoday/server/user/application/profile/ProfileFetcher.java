package dev.team.readtoday.server.user.application.profile;

import dev.team.readtoday.server.user.domain.EmailAddress;

public interface ProfileFetcher {

  /**
   * Get a profile's email address.
   *
   * @param token the access token to the profile
   * @return the email address of the profile
   * @throws ProfileFetchingFailed if an exception occurs while fetching the profile
   */
  EmailAddress fetchEmailAddress(AccessToken token);
}
