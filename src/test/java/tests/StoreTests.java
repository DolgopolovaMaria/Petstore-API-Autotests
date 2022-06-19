package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import models.ApiResponse;
import models.order.Order;
import models.pet.Pet;
import models.pet.PetStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Store")
@Feature("Store")
@DisplayName("Store API tests")
public class StoreTests extends TestBase {
    @Tag("Smoke")
    @DisplayName("Create order")
    @Story("Create order")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void createOrderTest(){
        Pet pet = createPet();

        Order order = generator.getRandomOrder(pet.getId());
        Order returnedOrder = storeSteps.createOrder(order);

        storeSteps.compareData(order, returnedOrder, "response data is equivalent to request data");

        step("Check that order exists", () -> {
            storeSteps.getOrder(order.getId(), true);
        });
    }

    @Tag("Smoke")
    @DisplayName("Get order by id")
    @Story("Get order")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void getOrderTest(){
        Order order = createOrder();

        Order returnedOrder = storeSteps.getOrder(order.getId(), true);

        storeSteps.compareData(order, returnedOrder, "response data is correct");
    }

    @DisplayName("Delete order by id")
    @Story("Delete order")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void deleteOrderTest(){

        Order order = createOrder();

        ApiResponse apiResponse = storeSteps.deleteOrder(order.getId(), true);

        step("Check response", () -> {
            assertEquals(order.getId().toString(), apiResponse.getMessage());
            assertEquals(200, apiResponse.getCode());
        });

        step("Check that order doesnt exist", () -> {
            storeSteps.getOrder(order.getId(), false);
        });
    }

    @DisplayName("Inventory store")
    @Story("Inventory")
    @Link(value = serviceName, url = serviceLink)
    @Test
    void InventoryTest(){
        JsonPath jsonPath = storeSteps.inventory();

        int soldValue = jsonPath.get(PetStatus.sold.toString());
        int pendingValue = jsonPath.get(PetStatus.pending.toString());
        int availableValue = jsonPath.get(PetStatus.available.toString());

        step("Add pet with status = pending", () -> {
            createPetWithStatus(PetStatus.pending);
        });

        JsonPath jsonPath1 = storeSteps.inventory();

        step("Check that number of pets in pending status has increased by 1", () -> {
            int pendingNewValue = jsonPath1.get(PetStatus.pending.toString());
            assertEquals(pendingValue + 1, Integer.valueOf(pendingNewValue));
        });
        step("Check that number of pets in other statuses remains the same", () -> {
            int soldNewValue = jsonPath1.get(PetStatus.sold.toString());
            assertEquals(soldValue, Integer.valueOf(soldNewValue));

            int availableNewValue = jsonPath1.get(PetStatus.available.toString());
            assertEquals(availableValue, Integer.valueOf(availableNewValue));
        });
    }
}
