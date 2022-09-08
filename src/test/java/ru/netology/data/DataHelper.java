package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;

import java.util.Locale;


public class DataHelper {
    public UserDto getUserFirstPassword() {
        Faker user = new Faker(new Locale("en"));
        String id = user.idNumber().valid();
        String name = user.name().firstName();
        String password = "qwerty123";
        String encryptedPassword = "$2a$10$MBH6363cULaEA999pr9tHOc7Mv8.HnVvbnA.sMOYoatqiSEkdzsfq";
        return new UserDto(id, name, password, encryptedPassword);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {
        private String id;
        private String login;
        private String password;
        private String encryptedPassword;

    }
}
