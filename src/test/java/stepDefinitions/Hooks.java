package stepDefinitions;

import io.cucumber.java.Before;
import resources.RestAssuredMethods;
import resources.Utility;

import java.io.IOException;

public class Hooks {

    String city="Indore";
    RestAssuredMethods restMethods = new RestAssuredMethods();
    LocationstepDefinition ls = new LocationstepDefinition();
    Utility util = new Utility();

@Before("@restaurantTest")
public void fetchRestaurantId() throws IOException {
    if(RestaurantStepDefinitions.restaurantId==0) {
        LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("query", city);
        ls.usersCallsHttpRequest("locationPath", "Get");
        String entity_id = util.getJsonPath(LocationstepDefinition.response, "location_suggestions[0].entity_id");
        String entity_type = util.getJsonPath(LocationstepDefinition.response, "location_suggestions[0].entity_type");
        //Preparing Res object for search API
        LocationstepDefinition.res = restMethods.requestSpecWithQueryParameter("entity_id", entity_id).queryParam("entity_type", entity_type).queryParam("q", city).queryParam("sort", "cost");
        ls.usersCallsHttpRequest("search", "Get");
        RestaurantStepDefinitions rsd = new RestaurantStepDefinitions();
        rsd.countRestaurantInGivenCityPriceAndRating("2000", "4");
    }
}


}
