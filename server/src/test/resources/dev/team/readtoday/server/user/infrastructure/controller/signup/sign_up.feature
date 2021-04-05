Feature: User sign up

  Background:
    Given there is a user:
      | userId   | 1e2533ab-17d4-46fd-b914-e8a682ac1e85 |
      | username | john.doe                             |
      | email    | user@example.com                     |

  Scenario: Successfully sign up
    Given my email address is "jamesbrown@example.com"
    And I have a valid access token
    When I request to sign up as "james.brown"
    Then the response status code should be 201

  Scenario: I fail to sign up because of an invalid access token
    Given my email address is "jamesbrown@example.com"
    And I have an invalid access token
    When I request to sign up as "james.brown"
    Then the response status code should be 401

  Scenario: I fail to sign up because I'm already signed up
    Given my email address is "user@example.com"
    And I have a valid access token
    When I request to sign up as "james.brown"
    Then the response status code should be 409
