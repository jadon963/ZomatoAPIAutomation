package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import resources.RestAssuredMethods;
import resources.Utility;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.List;


public class RestaurantStepDefinitions extends Utility {

    static int restaurantId;

    RestAssuredMethods restMethods = new RestAssuredMethods();
    LocationstepDefinition ls = new LocationstepDefinition();

    @Given("User Id,{string},{string} and parameters {string},{string},{string},{string}")
    public void userIdAndParametersReview(String city, String sortValue, String entity_idKey, String entity_typeKey, String q_key, String sortKey) throws IOException {
        //Getting entity_id and entity_type from loactionPath request
        LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("query",city);
        ls.usersCallsHttpRequest("locationPath","Get");
        String entity_id = getJsonPath(LocationstepDefinition.response,"location_suggestions[0].entity_id");
        String entity_type = getJsonPath(LocationstepDefinition.response,"location_suggestions[0].entity_type");
        //Preparing Res object for search API
        LocationstepDefinition.res= restMethods.requestSpecWithQueryParameter(entity_idKey,entity_id).queryParam(entity_typeKey,entity_type).queryParam(q_key,city).queryParam(sortKey,sortValue);
    }

    @And("Verify restaurant is present below given {string} and rating above {string}")
    public void countRestaurantInGivenCityPriceAndRating(String price,String rating) {
        //Getting List of average_cost_for_two and Rating of restaurant from response
        List<Integer> actualPrice = getJsonPathListInteger(LocationstepDefinition.response,"restaurants.restaurant.average_cost_for_two");
        List<String> actualrating = getJsonPathList(LocationstepDefinition.response,"restaurants.restaurant.user_rating.aggregate_rating");
        List<Integer> restaurantListId = getJsonPathListInteger(LocationstepDefinition.response,"restaurants.restaurant.R.res_id");
        boolean found = false;
       for(int i=0;i<actualPrice.size();i++){
           if(actualPrice.get(i)<=Integer.parseInt(price)&&Float.parseFloat(actualrating.get(i))>=Float.parseFloat(rating)){
               assertTrue(true);
               //Extracted restaurant Id
               restaurantId= restaurantListId.get(i);
               found=true;
               break;
           }
       }
        if(!found)
            fail();
    }


    @Given("User Id and parameter {string}")
    public void userIdAndParameter(String res_id) throws IOException {

      LocationstepDefinition.res=restMethods.requestSpecWithQueryParameter(res_id,String.valueOf(restaurantId));

    }



    @And("Verify restaurant should have specified highlight {string}")
    public void verifyRestaurantShouldHaveSpecifiedHighlight(String expectedHighlight) {
        List<String> highlights = getJsonPathList(LocationstepDefinition.response,"highlights");
        if(highlights.contains(expectedHighlight))
            assertTrue(true);
        else
            fail();
    }


    @And("Verify if {string} is present in particular restaurant")
    public void verifyIfIsPresentInParticularRestaurant(String expectedCuisine) {
        String cuisine = restMethods.getJsonPath(LocationstepDefinition.response,"cuisines");
        if(cuisine.contains(expectedCuisine))
            assertTrue(true);
        else
            fail();

    }

    @And("Verify {string} is present in particular restaurant")
    public void verifyIsPresentInParticularRestaurant(String expectedName) {
        List<String> reviewerNameList = restMethods.getJsonPathList(LocationstepDefinition.response,"user_reviews.review.user.name");
        if(reviewerNameList.contains(expectedName))
            assertTrue(true);
        else
            fail();
    }
}

