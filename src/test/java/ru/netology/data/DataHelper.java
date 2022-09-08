package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;

import java.util.Locale;

public class DataHelper {

    public AuthInfo getUserFirstPassword() {
        Faker faker = new Faker(new Locale("en"));
        String id = faker.idNumber().valid();
        String login = faker.name().firstName();
        String password = "qwerty123";
        String encryptedPassword = "$2a$10$MBH6363cULaEA999pr9tHOc7Mv8.HnVvbnA.sMOYoatqiSEkdzsfq";
        return new AuthInfo(id, login, password, encryptedPassword);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AuthInfo {
        private String id;
        private String login;
        private String password;
        private String encryptedPassword;

    }
}
