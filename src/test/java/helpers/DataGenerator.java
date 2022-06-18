package helpers;

import com.github.javafaker.Faker;
import models.*;

import java.util.Random;

public class DataGenerator {
    Faker faker = new Faker();
    Random random = new Random();

    private final String[] categories = {"Cats", "Dogs", "Hamsters", "Parrots", "Turtles", "Fish", "Others"},
                            tags = {"vip", "delivery", "vet passport", "discount"};

    private final PetStatus[] statuses = PetStatus.values();

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

    public User getRandomUser(){
        String firstName = getFirstName();
        String lastName = getLastName();
        String email = getEmail();
        String password = getPassword();
        String phone = getPhoneNumber();
        int userStatus = getUserStatus();
        int id = getId();
        String username = getUsernameById(id);
        return new User(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    public Tag[] getTagsArray(){
        int numberOfTags = random.nextInt(4);
        Tag[] tags_array = new Tag[numberOfTags];
        for(int i = 0; i < numberOfTags; i++){
            int id = random.nextInt(numberOfTags);
            String name = tags[id];
            tags_array[i] = new Tag(id, name);
        }
        return tags_array;

    }

    public Category getCategory(){
        int id = random.nextInt(7);
        String name = categories[id];
        return new Category(id, name);
    }

    public PetStatus getStatus(){
        int id = random.nextInt(3);
        PetStatus name = statuses[id];
        return name;
    }

    public Pet getRandomPet(){
        long id = getId();
        Category category = getCategory();
        String name = faker.funnyName().name();
        String[] photoUrls = {};
        Tag[] tags = getTagsArray();
        PetStatus status = getStatus();
        return new Pet(id, category, name, photoUrls, tags, status);
    }

    public Pet getPetWithStatus(PetStatus status){
        long id = getId();
        Category category = getCategory();
        String name = faker.funnyName().name();
        String[] photoUrls = {};
        Tag[] tags = getTagsArray();
        return new Pet(id, category, name, photoUrls, tags, status);
    }
}
