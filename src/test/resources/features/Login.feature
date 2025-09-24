Feature: User Login
#Scenario: User Login with valid credentials
#When I enter valid credentials standard_user and secret_sauce
#Then I should be redirected to the dashboard

Scenario: User Login with valid credentials with paramaters
When I enter valid credentials "standard_user" and "secret_sauce"
#Then I should see an error message