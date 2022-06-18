package tests.steps;

import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.ApiResponse;
import models.user.User;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;

public class UserApiSteps {

    @Step("POST /user")
    public ApiResponse createSingleUser(User user){
        ApiResponse apiResponse = given()
                .spec(requestSpec)
                .when()
                .body(user)
                .post("/user")
                .then()
                .spec(responseSpec)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }

    @Step("POST /user/createWithArray")
    public ApiResponse createUsersWithArray(User[] users){
        ApiResponse apiResponse = given()
                .spec(requestSpec)
                .when()
                .body(users)
                .post("/user/createWithArray")
                .then()
                .spec(responseSpec)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }

    @Step("GET /user/{username}")
    public User getUser(String username, boolean exist){
        ResponseSpecification responseSpecification;
        if (exist){
            responseSpecification = responseSpec;
        }
        else{
            responseSpecification = responseNotFoundSpec;
        }
        User user = given()
                .spec(requestSpec)
                .when()
                .get("/user/" + username)
                .then()
                .spec(responseSpecification)
                .extract().as(User.class);

        return user;
    }

    @Step("DELETE /user/{username}")
    public ApiResponse deleteUser(String username, boolean exist){
        ResponseSpecification responseSpecification;
        if (exist){
            responseSpecification = responseSpec;
        }
        else{
            responseSpecification = responseNotFoundSpec;
        }
        ApiResponse apiResponse = given()
                .spec(requestSpec)
                .when()
                .delete("/user/" + username)
                .then()
                .spec(responseSpecification)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }

    @Step("PUT /user/{username}")
    public ApiResponse updateUser(String username, User newData){
        ApiResponse apiResponse = given()
                .spec(requestSpec)
                .body(newData)
                .when()
                .put("/user/" + username)
                .then()
                .spec(responseSpec)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }



    @Step("Check json data: {description}")
    public void compareData(User first, User second, String description){
        step("id", () -> {
            assertEquals(first.getId(), second.getId());
        });
        step("username", () -> {
            assertEquals(first.getUsername(), second.getUsername());
        });
        step("firstName", () -> {
            assertEquals(first.getFirstName(), second.getFirstName());
        });
        step("lastName", () -> {
            assertEquals(first.getLastName(), second.getLastName());
        });
        step("email", () -> {
            assertEquals(first.getEmail(), second.getEmail());
        });
        step("password", () -> {
            assertEquals(first.getPassword(), second.getPassword());
        });
        step("phone", () -> {
            assertEquals(first.getPhone(), second.getPhone());
        });
        step("userStatus", () -> {
            assertEquals(first.getUserStatus(), second.getUserStatus());
        });
    }
}
