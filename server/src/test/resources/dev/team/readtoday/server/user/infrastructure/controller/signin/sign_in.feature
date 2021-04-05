Feature: User sign in

  Background:
    Given there is a user:
      | userId   | 1e2533ab-17d4-46fd-b914-e8a682ac1e85 |
      | username | john.doe                             |
      | email    | user@example.com                     |

  Scenario: Successfully sign in
    Given my email address is "user@example.com"
    And I have a valid access token
    When I request to sign in
    Then the response status code should be 200

  Scenario: I fail to sign in because of an invalid access token
    Given my email address is "user@example.com"
    And I have an invalid access token
    When I request to sign in
    Then the response status code should be 401

  Scenario: I fail to sign in because I'm not signed up
    Given my email address is "jamesbrown@example.com"
    And I have a valid access token
    When I request to sign in
    Then the response status code should be 404
