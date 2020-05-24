package resources;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class Utility {

   static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {
        if(req==null){
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        req = new RequestSpecBuilder().setBaseUri(getConfigValue("baseUrl")).addHeader("user-key",getConfigValue("userKey")).addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .setContentType(ContentType.JSON).build();
        return req;}

        return req;
    }

    public static String getConfigValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resources/config.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response,String key){
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        return jsonPath.get(key).toString();
    }


    public List<String> getJsonPathList(Response response, String key){
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        return jsonPath.get(key);
    }

    public List<Integer> getJsonPathListInteger(Response response, String key){
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        return jsonPath.get(key);
    }
    public List<String> getJsonPathListWithParam(Response response, String key,String paramKey,String paramValue){
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        return jsonPath.param(paramKey,paramValue).get(key);
    }
}
