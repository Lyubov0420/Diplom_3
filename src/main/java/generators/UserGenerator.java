package generators;

import com.github.javafaker.Faker;

public class UserGenerator {
    private static final Faker faker = new Faker();

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.internet().password(8, 16);
    }

    public static String getRandomName() {
        return faker.name().firstName();
    }
}