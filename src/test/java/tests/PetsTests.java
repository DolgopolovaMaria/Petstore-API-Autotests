package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import models.ApiResponse;
import models.Pet;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Test
    void getPetsByStatusTest(){

        Pet[] returnedPets = petSteps.getPetsByStatus("sold");

        for (int i = 0; i < returnedPets.length; i++){
            assertEquals("sold", returnedPets[i].getStatus());
        }
    }
}
