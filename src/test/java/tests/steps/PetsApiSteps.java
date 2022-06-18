package tests.steps;

import models.ApiResponse;
import models.pet.PetStatus;
import io.qameta.allure.Step;
import io.restassured.specification.ResponseSpecification;
import models.pet.Pet;
import models.pet.Tag;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;

public class PetsApiSteps {
    @Step("POST /pet")
    public Pet createPet(Pet pet){
        Pet createdPet = given()
                .spec(requestSpec)
                .when()
                .body(pet)
                .post("/pet")
                .then()
                .spec(responseSpec)
                .extract().as(Pet.class);
        return createdPet;
    }

    @Step("GET /pet/findByStatus?status={status}")
    public Pet[] getPetsByStatus(PetStatus status){
        Pet[] pets = given()
                .spec(requestSpec)
                .param("status", status.toString())
                .when()
                .get("/pet/findByStatus")
                .then()
                .spec(responseSpec)
                .extract().as(Pet[].class);

        return pets;
    }

    @Step("GET /pet/{id}")
    public Pet getPet(long id, boolean exist){
        ResponseSpecification responseSpecification;
        if (exist){
            responseSpecification = responseSpec;
        }
        else{
            responseSpecification = responseNotFoundSpec;
        }
        Pet pet = given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + id)
                .then()
                .spec(responseSpecification)
                .extract().as(Pet.class);

        return pet;
    }

    @Step("DELETE /pet/{id}")
    public ApiResponse deletePet(long id, boolean exist){
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
                .delete("/pet/" + id)
                .then()
                .spec(responseSpecification)
                .extract().as(ApiResponse.class);
        return apiResponse;
    }

    @Step("Check json data: {description}")
    public void compareData(Pet first, Pet second, String description){
        step("id", () -> {
            assertEquals(first.getId(), second.getId());
        });
        step("category id", () -> {
            assertEquals(first.getCategory().getId(), second.getCategory().getId());
        });
        step("category name", () -> {
            assertEquals(first.getCategory().getName(), second.getCategory().getName());
        });
        step("name", () -> {
            assertEquals(first.getName(), second.getName());
        });
        step("status", () -> {
            assertEquals(first.getStatus(), second.getStatus());
        });
        step("tags", () -> {
            compareTags(first.getTags(), second.getTags());
        });
    }

    private void compareTags(Tag[] first, Tag[] second){
        for (int i = 0; i < first.length; i++){
            assertEquals(first[i], second[i]);
        }
    }
}
