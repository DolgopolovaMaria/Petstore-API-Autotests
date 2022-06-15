package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class Specs {
    static String baseUrl = "https://petstore.swagger.io/v2";

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .baseUri(baseUrl)
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
