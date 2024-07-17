package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderTest {

    // Объекты тестовых элементов с css-селекторами
    SelenideElement name = $("[data-test-id=name] .input__control");
    SelenideElement phone = $("[data-test-id=phone] .input__control");
    SelenideElement agreement = $("[data-test-id=agreement] .checkbox__box");
    SelenideElement submit = $(".button.button_view_extra.button_size_m.button_theme_alfa-on-white");
    SelenideElement successMessage = $("[data-test-id=order-success]");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitIfCorrect() {

        // Ввод минимального валидного значения имени и фамилии, и его проверка
        name.setValue("а а");
        name.shouldHave(value("а а"));

        // Ввод минимального валидного значения номера телефона, и его проверка
        phone.setValue("+00000000000");
        phone.shouldHave(value("+00000000000"));

        // Клик на чек-бокс с "соглашением" и проверка статуса чек-бокса
        agreement.click();
        agreement.$(".checkbox__control").shouldBe(selected);

        // Клик на кнопку "Продолжить"
        submit.click();

        // Проверка сообщения после отправки формы
        successMessage.shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

}
