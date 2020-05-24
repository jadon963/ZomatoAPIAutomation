Feature: Find restaurant by search API's using common API's

Scenario Outline: Verify restaurant have particular cuisine
  Given UserId,"<city>" and list of cuisine
  And  Fetch the "<cuisine>" from cuisine list
  When Users calls "search" with "Get" http request
  Then API call got success with status code as 200
  And  Verify search result contains "<cuisine>"
Examples:
  |city|cuisine|
  |Indore|Afghan|
  |Indore|xyz|

