Feature: Create a channel
  Administrators can create channels

  Background:
    Given there are these users:
      | userId                               | username     | email             | role  |
      | eef222ac-066e-4fbe-9821-7f84c5ce1b40 | nicolas.cage | admin@example.com | ADMIN |
      | 0cca2672-54e6-4f20-afa0-aa75b33e316a | john.doe     | user@example.com  | USER  |

  Scenario: Successfully create a channel
    Given I have a valid authentication token for the user with ID "eef222ac-066e-4fbe-9821-7f84c5ce1b40"
    When I request to create a channel with the following characteristics:
      | title       | Hacker News                              |
      | rssUrl      | https://hnrss.org/frontpage              |
      | description | Hacker News RSS                          |
      | imageUrl    | https://news.ycombinator.com/favicon.ico |
    Then the response status code should be 201

  Scenario: I fail to create a channel because I'm not an admin
    Given I have a valid authentication token for the user with ID "0cca2672-54e6-4f20-afa0-aa75b33e316a"
    When I request to create a channel with the following characteristics:
      | title       | Hacker News                              |
      | rssUrl      | https://hnrss.org/frontpage              |
      | description | Hacker News RSS                          |
      | imageUrl    | https://news.ycombinator.com/favicon.ico |
    Then the response status code should be 403

  Scenario: I fail to create a channel because I don't have a valid authentication token
    Given I have an invalid authentication token
    When I request to create a channel with the following characteristics:
      | title       | Hacker News                              |
      | rssUrl      | https://hnrss.org/frontpage              |
      | description | Hacker News RSS                          |
      | imageUrl    | https://news.ycombinator.com/favicon.ico |
    Then the response status code should be 401

  Scenario: I fail to create a channel because of an invalid RSS URL
    Given I have a valid authentication token for the user with ID "eef222ac-066e-4fbe-9821-7f84c5ce1b40"
    When I request to create a channel with the following characteristics:
      | title       | Hacker News                              |
      | rssUrl      | https://www.google.com                   |
      | description | Hacker News RSS                          |
      | imageUrl    | https://news.ycombinator.com/favicon.ico |
    Then the response status code should be 400
