package ru.netology.sql;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static javax.management.remote.JMXConnectorFactory.connect;

public class SqlHelper {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    public static void createUser(DataHelper.AuthInfo user) {

        val dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";

        try (
                val conn = connect();
                val dataStmt = conn.prepareStatement(dataSQL);
        ) {
            dataStmt.setString(1, user.getId());
            dataStmt.setString(2, user.getLogin());
            dataStmt.setString(3, user.getEncryptedPassword());
            dataStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Cant create user");
        }
    }

    public static void cleanDefaultData() {
        val deleteCards = "DELETE FROM cards WHERE number = '5559 0000 0000 0002' OR number = '5559 0000 0000 0001';";
        val deleteUsers = "DELETE FROM users WHERE login = 'petya' OR login = 'vasya';";
        try (
                val conn = connect();
                val dataStmt = conn.createStatement();
        ) {
            dataStmt.executeUpdate(deleteCards);
            dataStmt.executeUpdate(deleteUsers);
        } catch (SQLException e) {
            System.out.println("Cant clean default data");
        }
    }

    public static String getVerificationCode(String id) {
        val selectCode = "SELECT code FROM auth_codes WHERE user_id = '" + id + "';";
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("error 42");
        }

        try (
                val conn = connect();
                val dataStmt = conn.createStatement()
        ) {
            try (val rs = dataStmt.executeQuery(selectCode)) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.out.println("field does not exist");
            }
        } catch (SQLException e) {
            System.out.println("Cant find verification code");
        }
        return "Error";
    }

    public static String getUserStatus(String id) {
        val selectCode = "SELECT status FROM users WHERE id = '" + id + "';";
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("error 42");
        }


        try (
                val conn = connect();
                val dataStmt = conn.createStatement()
        ) {
            try (val rs = dataStmt.executeQuery(selectCode)) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.out.println("field does not exist");
            }
        } catch (SQLException e) {
            System.out.println("Cant get user status");
        }
        return "Error";
    }
}