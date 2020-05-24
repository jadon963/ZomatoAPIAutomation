package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.sl.In;
import resources.RestAssuredMethods;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.List;

public class RestaurantFinderStepDefinitions {
    int cuisineId;
    String entity_id;
    String entity_type;

    RestAssuredMethods restMethods = new RestAssuredMethods();
    LocationstepDefinition ls = new LocationstepDefinition();

    @Given("UserId,{string} and list of cuisine")
    public void userid(String city) throws IOException {
        LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("query",city);
        ls.usersCallsHttpRequest("locationPath","Get");
       String city_id = restMethods.getJsonPath(LocationstepDefinition.response,"location_suggestions.city_id");
       city_id = city_id.replaceAll("\\[", "").replaceAll("\\]","");
       entity_id = restMethods.getJsonPath(LocationstepDefinition.response,"location_suggestions[0].entity_id");
       entity_type = restMethods.getJsonPath(LocationstepDefinition.response,"location_suggestions[0].entity_type");
       LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("city_id",city_id);
       ls.usersCallsHttpRequest("cuisines","Get");

    }

    @And("Fetch the {string} from cuisine list")
    public void fetchTheFromCuisineList(String expectedCuisine) {
        List<String> cuisinesList = restMethods.getJsonPathList(LocationstepDefinition.response,"cuisines.cuisine.cuisine_name");
        List<Integer> cuisinesId = restMethods.getJsonPathListInteger(LocationstepDefinition.response,"cuisines.cuisine.cuisine_id");
        for(int i =0;i<cuisinesList.size();i++){
            if(cuisinesList.get(i).equals(expectedCuisine))
                cuisineId = cuisinesId.get(i);
        }

    }

    @And("Verify search result contains {string}")
    public void verifySearchResultContainsCuisine(String expectedCuisine) throws IOException {
        LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("entity_id",entity_id).queryParam("entity_type",entity_type).queryParam("cuisines",cuisineId);
        ls.usersCallsHttpRequest("search","Get");
        String cuisine = restMethods.getJsonPath(LocationstepDefinition.response,"restaurants[0].restaurant.cuisines");
        if(cuisine.contains(expectedCuisine))
            assertTrue(true);
        else
            fail();

    }



}
