package tests.steps;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import io.restassured.specification.ResponseSpecification;
import models.ApiResponse;
import models.order.Order;
import models.pet.Pet;

import java.sql.Date;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;
import static specs.Specs.responseNotFoundSpec;

public class StoreApiSteps {
    @Step("POST /store/order")
    public Order createOrder(Order order) {
        Order createdOrder = given()
                .spec(requestSpec)
                .when()
                .body(order)
                .post("/store/order")
                .then()
                .log().all()
                .spec(responseSpec)
                .extract().as(Order.class);
        return createdOrder;
    }

    @Step("GET /store/order/{orderId}")
    public Order getOrder(long orderId, boolean exist){
        ResponseSpecification responseSpecification;
        if (exist){
            responseSpecification = responseSpec;
        }
        else{
            responseSpecification = responseNotFoundSpec;
        }
        Order order = given()
                .spec(requestSpec)
                .when()
                .get("/store/order/" + orderId)
                .then()
                .spec(responseSpecification)
                .extract().as(Order.class);

        return order;
    }

    @Step("DELETE /store/order/{orderId}")
    public ApiResponse deleteOrder(long orderId, boolean exist){
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
                .delete("/store/order/" + orderId)
                .then()
                .spec(responseSpecification)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }

    @Step("GET /store/inventory")
    public JsonPath inventory(){
        JsonPath responseBody = given()
                .spec(requestSpec)
                .when()
                .get("/store/inventory")
                .then()
                .log().all()
                .spec(responseSpec)
                .extract().body().jsonPath();
        return responseBody;
    }

    @Step("Check json data: {description}")
    public void compareData(Order first, Order second, String description){
        step("id", () -> {
            assertEquals(first.getId(), second.getId());
        });
        step("petId", () -> {
            assertEquals(first.getPetId(), second.getPetId());
        });
        step("quantity", () -> {
            assertEquals(first.getQuantity(), second.getQuantity());
        });
        step("shipDate", () -> {
            assertEquals(first.getShipDate(), second.getShipDate());
        });
        step("status", () -> {
            assertEquals(first.getStatus(), second.getStatus());
        });
        step("complete", () -> {
            assertEquals(first.getComplete(), second.getComplete());
        });
    }
}
