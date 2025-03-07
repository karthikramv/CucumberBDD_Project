Feature: Flipkart Application

  Background: 
    Given User should enter the url
    When User should click input box

@reg
  Scenario: Flipkart application to search the product
    And User should enter the search products
    And user should click enter
    Then User get the result
@reg
  Scenario Outline: : Flipkart application to search the product
    And User should enter the search "<products>"
    And user should click enter
    Then User get the result

    Examples: 
      | products |
      | mobile   |
      | laptop   |
