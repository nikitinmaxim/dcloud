Feature: Auth Server fail-free functionality

  A fail-safe in engineering is a design feature or practice that
  in the event of a specific type of failure, inherently responds
  in a way that will cause no or minimal harm to other equipment,
  the environment or to people.

  Scenario: Checking Auth Server error free behaviour

    Authentication is a crucial part of any DClou application.
    Its correct work a fundamental requirement to the service.

    Given DClou Stack is up and running
    When  Auth Service container is ready for the user "user1" and password "password" by credentials "order-service" and "123456"
    Then  Send checking requests to Auth Server