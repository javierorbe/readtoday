Feature: Get publications of a channel

  Background:
    Given there is a user with ID "3fb33460-7adf-4c9f-aa18-2df5fa1ac9ff"
    And there are is a channel:
      | id    | c8ba974c-fa85-44f9-b8b1-e2671f73952f |
      | title | The New York Times                   |

  Scenario: I successfully receive the publications of a channel
    Given I have a valid authentication token for the user with ID "3fb33460-7adf-4c9f-aa18-2df5fa1ac9ff"
    When I request to the publications of the channel with ID "c8ba974c-fa85-44f9-b8b1-e2671f73952f"
    Then the response status code should be 200
    And the response content should be:
    """
    [
      {
        "id": "https://www.nytimes.com/2021/04/16/world/europe/russia-ukraine-troops.html",
        "title": "In Russia, a Military Buildup That Can’t Be Missed",
        "link": "https://www.nytimes.com/2021/04/16/world/europe/russia-ukraine-troops.html",
        "description": "Russia’s massing of tanks and infantry along its southwestern border with Ukraine was meant to send a message, analysts say.",
        "categories": []
      },
      {
        "id": "https://www.nytimes.com/live/2021/04/17/world/prince-philip-funeral/",
        "title": "Prince Philip’s Funeral: Latest Updates",
        "link": "https://www.nytimes.com/live/2021/04/17/world/prince-philip-funeral/",
        "description": "The Duke of Edinburgh, the husband of Queen Elizabeth II and patriarch of the House of Windsor, died last week at age 99.",
        "categories": []
      }
    ]
    """
