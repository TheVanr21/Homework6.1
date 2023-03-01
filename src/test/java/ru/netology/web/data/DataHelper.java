package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @Value
    public static class Card {
        private String id;
        private String number;
        private int balance;
    }

    public static int getRandomValue(int min, int max){
        Faker faker = new Faker();
        return faker.number().numberBetween(min, max);
    }
}
