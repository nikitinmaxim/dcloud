Feature: Admin Server fail-free functionality

  A fail-safe in engineering is a design feature or practice that
  in the event of a specific type of failure, inherently responds
  in a way that will cause no or minimal harm to other equipment,
  the environment or to people.

  Scenario: Checking Admin Server error free behaviour

    You can have as many steps as you like, but we recommend you keep
    the number at 3-5 per scenario. If they become longer than that they
    lose their expressive power as specification and documentation.

    Given DClou Stack is up and running
    When  Admin server container is ready
    Then  Send checking requests to Admin Server