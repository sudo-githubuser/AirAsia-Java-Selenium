Feature: Search Flight - Return Journey
  Description: Search flight with return journey and specific dates


  Scenario: Search Flight - Return Journey
    Given User visits Air Asia website
    And Selects Flight
    When User enters "From" city
    * Enters "To" city
    * Enters departure date as "Departure date"
    * Enters return date as "Return date"
    * Selects cabin class "Cabin class"
    * Selects number of travellers as "No. of Passengers"
    * Search flights
    Then Flight results are displayed