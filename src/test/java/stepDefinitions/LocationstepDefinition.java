package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.Constants;
import resources.RestAssuredMethods;
import resources.Utility;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LocationstepDefinition extends Utility {

    static Response response;
    static RequestSpecification res;
    RestAssuredMethods restMethods = new RestAssuredMethods();



    @Given("User id ,{string} and parameter {string}")
    public void user_id_and_parameter(String city, String keyParam) throws IOException {
        //Created res object for Locations API
        res = restMethods.requestSpecWithQueryParameter(keyParam,city);
    }

    @Given("User id ,{string} and parameter {string},{string}")
    public void userIdAndParameter(String city, String keyParam1, String keyParam2) throws IOException {
        //Calling location API to get entity Id and entity type by specific location
        res = restMethods.requestSpecWithQueryParameter("query",city);
        usersCallsHttpRequest("locationPath","Get");
        String entity_id = getJsonPath(response,"location_suggestions[0].entity_id");
        String entity_type = getJsonPath(response,"location_suggestions[0].entity_type");
        //And preparing res object for location detail API with entity Id and entity type extracted by location API
        res= restMethods.requestSpecWithQueryParameter(keyParam1,entity_id).queryParam(keyParam2,entity_type);
    }

    @When("Users calls {string} with {string} http request")
    public void usersCallsHttpRequest(String resource,String method) {
        //Generic method to call Get and Post request with specified enum
        Constants path = Constants.valueOf(resource);
        if(method.equals("Get"))
        response = restMethods.getRequest(this.res,path.getResource());
        else if(method.equals("Post"))
        response = restMethods.postRequest(this.res,path.getResource());
    }

    @Then("API call got success with status code as {int}")
    public void apiCallGotSuccessWithStatusCodeAs(int arg0) {
        //Verifying status code of response
        assertEquals(arg0,response.getStatusCode());
    }

    @And("Verify {string} equals to {string}")
    public void equalsTo(String arg0, String city) {
        //Verifying Title of response
        assertEquals(city,getJsonPath(response,"location_suggestions[0]."+arg0));

    }

    @And("Verify {string}  in best rated restaurant")
    public void verifyInBestRatedRestaurant(String restaurant) {
        //Verifying restaurant is present in response body
        List<String> restaurantName = getJsonPathList(response,"best_rated_restaurant.restaurant.name");
        boolean found = false;
        for(String name:restaurantName){
            if(name.equals(restaurant)){
                assertEquals(restaurant,name);
            found= true;
            break;}
        }
        if(!found)
            fail();

    }

}
