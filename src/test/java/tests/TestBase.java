package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.DataGenerator;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import models.Pet;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import tests.steps.PetsApiSteps;
import tests.steps.UserApiSteps;

public class TestBase {

    protected final String serviceName = "Petstore";
    protected final String serviceLink = "https://petstore.swagger.io/";
    protected DataGenerator generator = new DataGenerator();

    protected UserApiSteps userSteps = new UserApiSteps();

    protected PetsApiSteps petSteps = new PetsApiSteps();

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
}
