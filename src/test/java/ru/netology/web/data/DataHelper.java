package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getValidUser(){
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(){
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        private String id;
        private String number;
        private int balance;
    }

    public static Card getFirstCard(){
        return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001", 10000);
    }

    public static Card getSecondCard(){
        return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002", 10000);
    }

    public static int getRandomValue(int min, int max){
        Faker faker = new Faker();
        return faker.number().numberBetween(min, max);
    }
}
