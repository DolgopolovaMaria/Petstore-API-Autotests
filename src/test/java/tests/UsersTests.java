package tests;

import helpers.DataGenerator;
import models.ApiResponse;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.requestSpec;
import static specs.Specs.responseSpec;
import com.github.javafaker.Faker;

import java.util.Random;

@DisplayName("Petstore API tests")
public class UsersTests {
    private DataGenerator generator = new DataGenerator();

    private String firstName = generator.getFirstName();
    private String lastName = generator.getLastName();
    private String email = generator.getEmail();
    private String password = generator.getPassword();
    private String phone = generator.getPhoneNumber();
    private int userStatus = generator.getUserStatus();
    private int id = generator.getId();
    private String username = generator.getUsernameById(id);


    @Test
    @DisplayName("Create single user")
    void createUserTest(){
        User user = new User(id, username, firstName, lastName, email, password, phone, userStatus);
        ApiResponse apiResponse = given()
                .spec(requestSpec)
                .when()
                .body(user)
                .post("/user")
                .then()
                .spec(responseSpec)
                .log().all()
                .extract().as(ApiResponse.class);

        assertEquals(Integer.toString(id), apiResponse.getMessage());
        assertEquals(200, apiResponse.getCode());
    }
}
