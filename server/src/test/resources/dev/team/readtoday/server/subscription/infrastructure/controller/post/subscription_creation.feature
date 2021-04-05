Feature: Subscribe to a channel
  A user subscribes to a channel

  Background:
    Given there is a user:
      | userId   | a4915226-99af-42a6-bebf-294d9980e588 |
      | username | john.doe                             |
      | email    | user@example.com                     |
    And there is a channel:
      | channelId   | 505cbdd9-5c66-465b-9814-cfc52a8ac293     |
      | title       | Hacker News                              |
      | rssUrl      | https://hnrss.org/frontpage              |
      | description | Hacker News RSS                          |
      | imageUrl    | https://news.ycombinator.com/favicon.ico |

  Scenario: I successfully subscribe to a channel
    Given I have a valid authentication token for the user with ID "a4915226-99af-42a6-bebf-294d9980e588"
    When I request to subscribe to the channel with ID "505cbdd9-5c66-465b-9814-cfc52a8ac293"
    Then the response status code should be 201
