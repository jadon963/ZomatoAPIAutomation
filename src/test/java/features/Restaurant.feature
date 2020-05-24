Feature: Validating restaurant specific scenario

@searchTestOnPriceRating
Scenario Outline: Verify no of restaurant in given city with price less than 2000 and aggregate_rating more than 4
    Given User Id,"<city>","<sort>" and parameters "entity_id","entity_type","q","sort"
    When  Users calls "search" with "Get" http request
    Then  API call got success with status code as 200
    And   Verify restaurant is present below given "<price>" and rating above "<rating>"
Examples:
    |city|sort|price|rating|
    |Indore|cost|500|4    |
    |Indore|cost|2000|4    |

@restaurantTest
Scenario Outline: Verify selected cuisine present in particular restaurant
    Given User Id and parameter "res_id"
    When Users calls "restaurant" with "Get" http request
    Then API call got success with status code as 200
    And  Verify if "<cuisine>" is present in particular restaurant
Examples:
    |cuisine|
    |Chinese|
    |xyz    |

@restaurantTest
Scenario Outline: Verify given restaurant have specified highlight
    Given User Id and parameter "res_id"
    When Users calls "restaurant" with "Get" http request
    Then API call got success with status code as 200
    And  Verify restaurant should have specified highlight "<highlight>"
Examples:
    |highlight|
    |Fullbar|

@reviewTest
Scenario Outline: Verify given user review present in particular restaurant
    Given User Id and parameter "res_id"
    When Users calls "reviews" with "Get" http request
    Then API call got success with status code as 200
    And  Verify "<reviewerName>" is present in particular restaurant
Examples:
    |reviewerName|
    |Pavneet Saluja|
