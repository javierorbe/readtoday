Feature: Search channels by category

  Background:
    Given there is a user with ID "3fb33460-7adf-4c9f-aa18-2df5fa1ac9ff"
    And there are these channels:
      | channelId                            | title       |
      | a700d653-0c8b-4f39-a487-5f4fd48d9edb | Hacker News |
      | f76b187d-8327-48a0-bdff-43704e46918f | TechCrunch  |
    And those channels have these categories:
      | channelId                            | categoryId                           | categoryName |
      | a700d653-0c8b-4f39-a487-5f4fd48d9edb | c593c383-48bb-4f1b-8736-9a81ab91c5bb | technology   |
      | f76b187d-8327-48a0-bdff-43704e46918f | c593c383-48bb-4f1b-8736-9a81ab91c5bb | technology   |
      | f76b187d-8327-48a0-bdff-43704e46918f | 902aa93b-4cee-4889-8264-f3546f91d1ee | news         |

  Scenario: Successfully receive the channels of a given category
    Given I have a valid authentication token
    When I request to search channels with the category "technology"
    Then the response status code should be 200
    And the response content should have:
      | id                                   | title       |
      | a700d653-0c8b-4f39-a487-5f4fd48d9edb | Hacker News |
      | f76b187d-8327-48a0-bdff-43704e46918f | TechCrunch  |
