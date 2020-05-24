Feature: Validating Location API's

@LocationAPI
Scenario Outline: Verify co-ordinates by city
    Given  User id ,"<city>" and parameter "query"
    When Users calls "locationPath" with "Get" http request
    Then API call got success with status code as 200
    And Verify "title" equals to "<city>"
    Examples:
        |city     |
        |Indore|

@BestRestaurantTest
Scenario Outline: Verify Best rated restaurants
    Given User id ,"<city>" and parameter "entity_id","entity_type"
    When Users calls "locationDetailsPath" with "Get" http request
    Then API call got success with status code as 200
    And  Verify "<restaurant>"  in best rated restaurant

    Examples:
      | city   | restaurant |
      | Indore |  Shree Gurukripa|
      | BENGALURU |  Meghana Foods|
      | BENGALURU |  Shree Gurukripa|


