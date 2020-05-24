package resources;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;


import static io.restassured.RestAssured.given;

public class RestAssuredMethods extends Utility {

    public RequestSpecification requestSpecWithQueryParameter(String key, String value) throws IOException {
       return given().spec(requestSpecification()).queryParam(key,value);
    }

    public Response getRequest(RequestSpecification res,String path){
        return res.when().get(path);
    }

    public Response postRequest(RequestSpecification res,String path){
        return res.when().post(path);
    }

}
