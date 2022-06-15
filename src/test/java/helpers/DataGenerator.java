package helpers;

import com.github.javafaker.Faker;
import java.util.Random;

public class DataGenerator {
    Faker faker = new Faker();
    Random random = new Random();

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getEmail() {
        return faker.internet().emailAddress();
    }

    public String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public String getPassword() {
        return faker.internet().password();
    }

    public int getId() {
        return random.nextInt(100000);
    }

    public String getUsernameById(int id) {
        return "autotests_username_" + id;
    }

    public int getUserStatus() {
        return random.nextInt(9);
    }
}
