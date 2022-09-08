package ru.netology.test;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;
import ru.netology.pages.VerificationPage;
import ru.netology.sql.SqlHelper;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clean() {
        SqlHelper.cleanDefaultData();
    }

    @Test
    void shouldBeValidAuthorization() {
        DataHelper.UserDto user = new DataHelper().getUserFirstPassword();
        SqlHelper.createUser(user);

        new LoginPage() .sigIn(user.getLogin(), user.getPassword()).inputCode(SqlHelper.getVerificationCode(user.getId()));
    }

    @Test
    void shouldBlockUserAfterInvalidPassword() {
        DataHelper.UserDto user = new DataHelper().getUserFirstPassword();
        SqlHelper.createUser(user);

        new LoginPage().invalidSigIn(user.getLogin());
        closeWindow();
        setUp();
        new LoginPage().invalidSigIn(user.getLogin());
        closeWindow();
        setUp();
        new LoginPage().invalidSigIn(user.getLogin());

        String status = SqlHelper.getUserStatus(user.getId());

        assertNotEquals("active", status);
    }
}


