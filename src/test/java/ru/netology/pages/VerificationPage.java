package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement heading = $(".heading");
    private SelenideElement codeField = $("[data-test-id=\"code\"] input");
    private SelenideElement verify = $("[data-test-id=\"action-verify\"]");

    public VerificationPage() {
        heading.shouldBe(Condition.visible).shouldHave(Condition.text("Интернет Банк"));
    }

    @SneakyThrows
    public DashboardPage inputCode(String code) {
        codeField.setValue(code);
        verify.click();

        return new DashboardPage();
    }
}
