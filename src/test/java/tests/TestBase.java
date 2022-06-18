package tests;

import helpers.DataGenerator;
import io.qameta.allure.Step;
import models.order.Order;
import models.pet.Pet;
import models.pet.PetStatus;
import models.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import tests.steps.PetsApiSteps;
import tests.steps.StoreApiSteps;
import tests.steps.UserApiSteps;

public class TestBase {

    protected final String serviceName = "Petstore";
    protected final String serviceLink = "https://petstore.swagger.io/";
    protected DataGenerator generator = new DataGenerator();

    protected UserApiSteps userSteps = new UserApiSteps();

    protected PetsApiSteps petSteps = new PetsApiSteps();

    protected StoreApiSteps storeSteps = new StoreApiSteps();

    @BeforeAll
    static void setUp() {
    }

    @Step("Prepare test data: create user")
    User createUser(){
        User user = generator.getRandomUser();
        userSteps.createSingleUser(user);
        return user;
    }

    @Step("Prepare test data: create pet")
    Pet createPet(){
        Pet pet = generator.getRandomPet();
        petSteps.createPet(pet);
        return pet;
    }

    @Step("Prepare test data: create pet with status {0}")
    Pet createPetWithStatus(PetStatus value){
        Pet pet = generator.getPetWithStatus(value);
        petSteps.createPet(pet);
        return pet;
    }

    @Step("Prepare test data: create order")
    Order createOrder(){
        Pet pet = createPet();
        Order order = generator.getRandomOrder(pet.getId());
        storeSteps.createOrder(order);
        return order;
    }
}
