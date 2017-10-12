Feature: Order Service fail-free functionality

  A fail-safe in engineering is a design feature or practice that
  in the event of a specific type of failure, inherently responds
  in a way that will cause no or minimal harm to other equipment,
  the environment or to people.

  Scenario: Checking Order Service error free behaviour

    You can have as many steps as you like, but we recommend you keep
    the number at 3-5 per scenario. If they become longer than that they
    lose their expressive power as specification and documentation.

    Given DClou Stack is up and running
    When  Auth Service container is ready for the user "user1" and password "password" by credentials "order-service" and "123456"
    Then  Send checking requests to Order Service