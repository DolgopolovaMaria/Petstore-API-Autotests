package tests;

import io.qameta.allure.*;
import models.ApiResponse;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Users API tests")
public class UsersTests extends TestBase {

    @DisplayName("Get user by username")
    @Feature("User")
    @Story("Get user")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void getUserTest(){

        User user = createUser();

        User returnedUser = userSteps.getUser(user.getUsername(), true);

        userSteps.compareData(user, returnedUser, "response data is correct");
    }

    @DisplayName("Create single user")
    @Feature("User")
    @Story("Create users")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void createUserTest(){

        User user = generator.getRandomUser();
        ApiResponse apiResponse = userSteps.createSingleUser(user);

        step("Check response", () -> {
            assertEquals(Integer.toString(user.getId()), apiResponse.getMessage());
            assertEquals(200, apiResponse.getCode());
        });

        step("Check that user exists", () -> {
            userSteps.getUser(user.getUsername(), true);
        });
    }

    @DisplayName("Create users with array")
    @Feature("User")
    @Story("Create users")
    @Link(value = serviceName, url = serviceLink)
    @ParameterizedTest(name = "({0} items)")
    @ValueSource(ints = {
            1,
            3,
            7
    })
    void createUsersArrayTest(int value){
        User[] users = new User[value];
        for (int i = 0; i<value; i++){
            users[i] = generator.getRandomUser();
        }

        ApiResponse apiResponse = userSteps.createUsersWithArray(users);

        step("Check response", () -> {
            assertEquals("ok", apiResponse.getMessage());
            assertEquals(200, apiResponse.getCode());
        });

        step("Check that users exist", () -> {
            for (int i = 0; i < value; i++){
                userSteps.getUser(users[i].getUsername(), true);
            }
        });
    }

    @DisplayName("Delete user by username")
    @Feature("User")
    @Story("Delete user")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void deleteUserTest(){

        User user = createUser();

        ApiResponse apiResponse = userSteps.deleteUser(user.getUsername(), true);

        step("Check response", () -> {
            assertEquals(user.getUsername(), apiResponse.getMessage());
            assertEquals(200, apiResponse.getCode());
        });

        step("Check that user doesnt exist", () -> {
            userSteps.getUser(user.getUsername(), false);
        });
    }

    @DisplayName("Update user by username")
    @Feature("User")
    @Story("Update user")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void updateUserTest(){

        User oldUser = createUser();

        User user = generator.getRandomUser();
        ApiResponse apiResponse = userSteps.updateUser(oldUser.getUsername(), user);

        step("Check response", () -> {
            assertEquals(200, apiResponse.getCode());
            assertEquals(Integer.toString(user.getId()), apiResponse.getMessage());
        });

        User returnedUser = userSteps.getUser(user.getUsername(), true);

        userSteps.compareData(user, returnedUser, "user data is updated");
    }
}
