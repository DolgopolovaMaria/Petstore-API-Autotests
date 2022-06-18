package tests;

import models.ApiResponse;
import models.PetStatus;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Pets API tests")
public class PetsTests extends TestBase {
    @DisplayName("Create pet")
    @Feature("Pet")
    @Story("Create pet")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void createPetTest(){
        Pet pet = generator.getRandomPet();
        Pet returnedPet = petSteps.createPet(pet);

        petSteps.compareData(pet, returnedPet, "response data is equivalent to request data");

        step("Check that pet exists", () -> {
            petSteps.getPet(pet.getId(), true);
        });
    }

    @DisplayName("Get pet by id")
    @Feature("Pet")
    @Story("Get pets")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void getPetTest(){
        Pet pet = createPet();

        Pet returnedPet = petSteps.getPet(pet.getId(), true);

        petSteps.compareData(pet, returnedPet, "response data is correct");
    }

    @DisplayName("Get pets by status")
    @Feature("Pet")
    @Story("Get pets")
    @Link(value = serviceName, url = serviceLink)
    @ParameterizedTest(name = "(status = {0})")
    @EnumSource(PetStatus.class)
    void getPetsByStatusTest(PetStatus value){
        Pet pet = createPetWithStatus(value);

        Pet[] returnedPets = petSteps.getPetsByStatus(value);

        step("Check that response contains pet from test data", () -> {
            assertTrue(Arrays.stream(returnedPets).anyMatch((p) -> p.getId().equals(pet.getId())));
        });

        step("Check that each returned pet has status = " + value, () -> {
            for (int i = 0; i < returnedPets.length; i++){
                assertEquals(value, returnedPets[i].getStatus());
            }
        });
    }

    @DisplayName("Delete user by username")
    @Feature("Pet")
    @Story("Delete pet")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void deletePetTest(){

        Pet pet = createPet();

        ApiResponse apiResponse = petSteps.deletePet(pet.getId(), true);

        step("Check response", () -> {
            assertEquals(pet.getId().toString(), apiResponse.getMessage());
            assertEquals(200, apiResponse.getCode());
        });

        step("Check that pet doesnt exist", () -> {
            petSteps.getPet(pet.getId(), false);
        });
    }
}
